package com.mine.integration.statemachine.amap;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public interface FsmOrder {

    /**
     * 订单id
     */
    String getOrderId();

    /**
     * 订单状态
     */
    String getOrderState();

    /**
     * 订单的业务属性
     */
    String bizCode();

    /**
     * 订单的场景属性
     */
    String sceneId();

}
