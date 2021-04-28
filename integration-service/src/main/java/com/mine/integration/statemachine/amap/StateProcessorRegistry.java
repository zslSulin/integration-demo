package com.mine.integration.statemachine.amap;

import com.mine.integration.statemachine.amap.processor.AbstractStateProcessor;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public class StateProcessorRegistry extends DefaultStateProcessRegistry {


    public List<AbstractStateProcessor> acquireStateProcess(String orderState, String eventType, String bizCode, String sceneId) {
        return stateProcessMap.getOrDefault(orderState, new ConcurrentHashMap<>())
                .getOrDefault(eventType, new ConcurrentHashMap<>())
                .getOrDefault(bizCode + "@" + sceneId, new CopyOnWriteArrayList<>());
    }
}
