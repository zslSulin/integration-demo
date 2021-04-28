package com.mine.integration.statemachine.amap.plugin;

import com.mine.integration.statemachine.amap.processor.StateProcessor;

/**
 * 插件处理器
 * <p>
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public interface PluginHandler<T, C> extends StateProcessor<T, C> {

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }
}
