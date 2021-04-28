package com.mine.integration.statemachine.amap.check;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.context.StateContext;

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

    /**
     * 是否需求release
     */
    default boolean needRelease() {
        return false;
    }

    /**
     * 业务执行完成后的释放方法，
     * 比如有些业务会在checker中加一些状态操作，等业务执行完成后根据结果选择处理这些状态操作,
     * 最典型的就是checker中加一把锁，release根据结果释放锁
     */
    default void release(StateContext<C> context, ServiceResult<T> result) {

    }

}
