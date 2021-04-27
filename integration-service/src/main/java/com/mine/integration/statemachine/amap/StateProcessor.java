package com.mine.integration.statemachine.amap;

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
public interface StateProcessor {

    /**
     * 执行状态迁移入口
     *
     * @param context 上下文
     * @throws Exception
     */
    void action(StateContext context) throws Exception;
}
