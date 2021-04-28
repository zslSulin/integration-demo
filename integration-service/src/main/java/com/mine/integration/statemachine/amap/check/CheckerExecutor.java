package com.mine.integration.statemachine.amap.check;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.context.StateContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 校验器的执行器
 *
 * @author zhangsl
 * @date 2021/4/27 22:19
 */
@Slf4j
@Component
public class CheckerExecutor<T, C> {

    @Resource
    private ExecutorService executor;

    /**
     * 执行并行校验器
     * 按照任务投递的顺序判断返回
     */
    public ServiceResult<T> parallelCheck(List<Checker> checkers, StateContext<C> context) {
        if (CollectionUtils.isNotEmpty(checkers)) {
            if (checkers.size() == 1) {
                return checkers.get(0).check(context);
            }
            List<Future<ServiceResult>> resultList = Collections.synchronizedList(new ArrayList<>(checkers.size()));
            checkers.sort(Comparator.comparing(Checker::order));
            for (Checker c : checkers) {
                Future<ServiceResult> future = executor.submit(() -> c.check(context));
                resultList.add(future);
            }
            for (Future<ServiceResult> future : resultList) {
                try {
                    ServiceResult sr = future.get();
                    if (!sr.isSuccess()) {
                        return sr;
                    }
                } catch (Exception e) {
                    log.error("parallelCheck executor.submit error.", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return new ServiceResult<>();
    }


    public ServiceResult<T> serialCheck(List<Checker> checkers, StateContext<C> context) {
        if (CollectionUtils.isNotEmpty(checkers)) {
            if (checkers.size() == 1) {
                return checkers.get(0).check(context);
            }
            checkers.sort(Comparator.comparing(Checker::order));
            for (Checker checker : checkers) {
                ServiceResult serviceResult = checker.check(context);
                if (!serviceResult.isSuccess()) {
                    return serviceResult;
                }
            }
        }
        return new ServiceResult<>();
    }

    /**
     * 执行checker的释放操作
     * @param checkable 校验器
     * @param context 上下文
     * @param result 处理结果
     * @param <T> 返回参数
     * @param <C> 请求参数
     */
    public <T, C> void releaseCheck(Checkable checkable, StateContext<C> context, ServiceResult<T> result) {
        List<Checker> checkers = new ArrayList<>();
        checkers.addAll(checkable.getParameterCheck());
        checkers.addAll(checkable.getSyncChecker());
        checkers.addAll(checkable.getAsyncChecker());
        checkers.removeIf(Checker::needRelease);
        if (CollectionUtils.isNotEmpty(checkers)) {
            if (checkers.size() == 1) {
                checkers.get(0).release(context, result);
                return;
            }
            CountDownLatch latch = new CountDownLatch(checkers.size());
            for (Checker c : checkers) {
                executor.execute(() -> {
                    try {
                        c.release(context, result);
                    } finally {
                        latch.countDown();
                    }
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
