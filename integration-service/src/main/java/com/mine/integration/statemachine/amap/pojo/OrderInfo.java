package com.mine.integration.statemachine.amap.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
@Getter
@Setter
public class OrderInfo {

    private String orderId;

    private String userId;

    private String orderState;

}
