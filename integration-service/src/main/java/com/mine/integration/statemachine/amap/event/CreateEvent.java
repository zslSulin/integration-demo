package com.mine.integration.statemachine.amap.event;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public class CreateEvent implements OrderStateEvent{

    @Override
    public String getEventType() {
        return null;
    }

    @Override
    public String getOrderId() {
        return null;
    }

    @Override
    public boolean newCreate() {
        return false;
    }
}
