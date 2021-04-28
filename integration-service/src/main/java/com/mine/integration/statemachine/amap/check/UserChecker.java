package com.mine.integration.statemachine.amap.check;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.context.StateContext;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public class UserChecker<T, C> implements Checker<T, C> {

    @Override
    public ServiceResult<T> check(StateContext<C> context) {
        return null;
    }

    @Override
    public int order() {
        return Checker.super.order();
    }
}
