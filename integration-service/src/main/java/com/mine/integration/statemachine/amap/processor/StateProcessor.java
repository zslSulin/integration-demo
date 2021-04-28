package com.mine.integration.statemachine.amap.processor;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.context.StateContext;

/**
 * 状态机处理器接口
 * <p>
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/27
 */
public interface StateProcessor<T, C> {

    /**
     * 执行状态迁移入口
     *
     * @param context 上下文
     * @throws Exception
     * @return
     */
    ServiceResult<T> action(StateContext<C> context) throws Exception;
}
