package com.mine.integration.statemachine.amap.context;

import com.mine.integration.statemachine.amap.FsmOrder;
import com.mine.integration.statemachine.amap.OrderStateEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/27
 */
@Getter
@Setter
public class StateContext<C> {

    private OrderStateEvent orderStateEvent;

    /**
     * 状态机需要的订单基本信息
     */
    private FsmOrder fsmOrder;

    private C context;

    public StateContext() {
    }

    public StateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        this.orderStateEvent = orderStateEvent;
        this.fsmOrder = fsmOrder;
    }
}
