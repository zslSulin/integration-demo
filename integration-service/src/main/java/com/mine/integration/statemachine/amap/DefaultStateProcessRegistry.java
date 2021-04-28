package com.mine.integration.statemachine.amap;

import com.mine.integration.statemachine.amap.anno.OrderProcessor;
import com.mine.integration.statemachine.amap.enums.OrderEventEnum;
import com.mine.integration.statemachine.amap.enums.OrderStateEnum;
import com.mine.integration.statemachine.amap.processor.AbstractStateProcessor;
import com.mine.integration.statemachine.amap.processor.StateProcessor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
public class DefaultStateProcessRegistry implements BeanPostProcessor {

    /**
     * 第一层key是订单状态。 OrderStateEnum
     * 第二层key是订单状态对应的事件，一个状态可以有多个事件。 OrderEventEnum
     * 第三层key是具体场景code，场景下对应的多个处理器，需要后续进行过滤选择出一个具体的执行。 bizCode
     */
    protected static Map<String, Map<String, Map<String, List<AbstractStateProcessor>>>> stateProcessMap = new ConcurrentHashMap<>();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 订单状态执行器 并且 有OrderProcessor注解
        if (bean instanceof AbstractStateProcessor && bean.getClass().isAnnotationPresent(OrderProcessor.class)) {
            // 获取注解信息
            OrderProcessor annotation = bean.getClass().getAnnotation(OrderProcessor.class);
            // 注解上状态枚举
            OrderStateEnum[] states = annotation.state();
            // 注解上事件枚举
            OrderEventEnum event = annotation.event();
            // 业务code
            String[] bizCodes = annotation.bizCode().length == 0 ? new String[]{"#"} : annotation.bizCode();
            // 场景id
            String[] sceneIds = annotation.sceneId().length == 0 ? new String[]{"#"} : annotation.sceneId();
            // 初始化处理器类型映射
            initProcessMap(states, event, bizCodes, sceneIds, stateProcessMap, (AbstractStateProcessor) bean);
        }
        return bean;
    }

    private <E extends StateProcessor> void initProcessMap(OrderStateEnum[] states, OrderEventEnum event, String[] bizCodes, String[] sceneIds,
                                                           Map<String, Map<String, Map<String, List<E>>>> map, E processor) {
        // 按业务code、场景id 分级注册
        for (String bizCode : bizCodes) {
            for (String sceneId : sceneIds) {
                Arrays.asList(states).parallelStream().forEach(orderStateEnum -> {
                    registerStateHandlers(orderStateEnum, event, bizCode, sceneId, map, processor);
                });
            }
        }
    }

    /**
     * 注册处理器
     *
     * @param orderStateEnum 订单状态
     * @param event 事件
     * @param bizCode 业务code
     * @param sceneId 场景id
     * @param map 映射map
     * @param processor 执行器
     * @param <E> 执行器泛型
     */
    public <E extends StateProcessor> void registerStateHandlers(OrderStateEnum orderStateEnum, OrderEventEnum event, String bizCode, String sceneId,
                                                                 Map<String, Map<String, Map<String, List<E>>>> map, E processor) {
        // state维度
        if (!map.containsKey(orderStateEnum)) {
            // state维度初始化
            map.put(orderStateEnum.toString(), new ConcurrentHashMap<>());
        }
        Map<String, Map<String, List<E>>> stateTransformEventEnumMap = map.get(orderStateEnum);
        // event维度
        if (!stateTransformEventEnumMap.containsKey(event)) {
            // event维度初始化
            stateTransformEventEnumMap.put(event.toString(), new ConcurrentHashMap<>());
        }
        // bizCode and sceneId
        Map<String, List<E>> processorMap = stateTransformEventEnumMap.get(event);
        // bizCode + sceneId 拼接 key
        String bizCodeAndSceneId = bizCode + "@" + sceneId;
        if (!processorMap.containsKey(bizCodeAndSceneId)) {
            // 初始化
            processorMap.put(bizCodeAndSceneId, new CopyOnWriteArrayList<>());
        }
        // 执行器添加
        processorMap.get(bizCodeAndSceneId).add(processor);
    }
}
