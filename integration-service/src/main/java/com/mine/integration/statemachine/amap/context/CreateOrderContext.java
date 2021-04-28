package com.mine.integration.statemachine.amap.context;

import com.mine.integration.statemachine.amap.pojo.OrderInfo;
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
public class CreateOrderContext {

    private OrderInfo orderInfo;

}
