package com.mine.integration.statemachine.amap.engine;

import com.mine.integration.statemachine.amap.FsmOrder;
import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.event.OrderStateEvent;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public interface OrderFsmEngine {

    /**
     * 执行状态迁移事件，不传FsmOrder默认会根据orderId从FsmOrderService接口获取
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception;
    /**
     * 执行状态迁移事件，可携带FsmOrder参数
     */
    <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) throws Exception;
}
