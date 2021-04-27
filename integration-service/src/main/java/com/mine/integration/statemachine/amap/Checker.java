package com.mine.integration.statemachine.amap;

/**
 * Checker 状态机校验器
 *
 * @author zhangsl
 * @date 2021/4/27 22:06
 */
public interface Checker<T, C> {

    ServiceResult<T> check(StateContext<C> context);

    /**
     * 多个checker时的执行顺序
     */
    default int order() {
        return 0;
    }

}
