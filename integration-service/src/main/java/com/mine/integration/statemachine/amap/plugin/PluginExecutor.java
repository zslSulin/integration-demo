package com.mine.integration.statemachine.amap.plugin;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.context.StateContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
@Slf4j
@Component
public class PluginExecutor<T, C> {
    @Resource
    private ExecutorService executor;

    /**
     * 执行并行校验器
     * 按照任务投递的顺序判断返回
     */
    public ServiceResult<T> parallelExecutor(StateContext<C> context) throws Exception {
//        if (CollectionUtils.isNotEmpty(pluginHandlers)) {
//            if (pluginHandlers.size() == 1) {
//                return pluginHandlers.get(0).action(context);
//            }
//            List<Future<ServiceResult>> resultList = Collections.synchronizedList(new ArrayList<>(pluginHandlers.size()));
//            pluginHandlers.sort(Comparator.comparing(PluginHandler::order));
//            for (PluginHandler p : pluginHandlers) {
//                Future<ServiceResult> future = executor.submit(() -> p.action(context));
//                resultList.add(future);
//            }
//            for (Future<ServiceResult> future : resultList) {
//                try {
//                    ServiceResult sr = future.get();
//                    if (!sr.isSuccess()) {
//                        return sr;
//                    }
//                } catch (Exception e) {
//                    log.error("parallelCheck executor.submit error.", e);
//                    throw new RuntimeException(e);
//                }
//            }
//        }
        return new ServiceResult<>();
    }
}
