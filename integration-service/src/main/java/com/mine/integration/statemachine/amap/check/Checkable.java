package com.mine.integration.statemachine.amap.check;

import java.util.Collections;
import java.util.List;

/**
 * 状态机校验器
 *
 * @author zhangsl
 * @date 2021/4/27 22:16
 */
public interface Checkable {
    /**
     * 参数校验
     */
    default List<Checker> getParameterCheck() {
        return Collections.EMPTY_LIST;
    }

    /**
     * 需要同步执行的状态检查器
     */
    default List<Checker> getSyncChecker() {
        return Collections.EMPTY_LIST;
    }

    /**
     * 可以异步执行的校验器
     */
    default List<Checker> getAsyncChecker() {
        return Collections.EMPTY_LIST;
    }
}
