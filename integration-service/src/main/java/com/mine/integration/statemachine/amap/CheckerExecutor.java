package com.mine.integration.statemachine.amap;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 校验器的执行器
 *
 * @author zhangsl
 * @date 2021/4/27 22:19
 */
@Slf4j
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
                    log.error("parallelCheck executor.sumbit error.", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return new ServiceResult<>();
    }
}
