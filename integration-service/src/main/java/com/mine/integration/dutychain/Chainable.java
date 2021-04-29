package com.mine.integration.dutychain;

import com.mine.integration.dutychain.context.ActionContext;

/**
 * Chainable 执行链接口
 *
 * @author zhangsl
 * @date 2021/4/28 22:41
 */
public interface Chainable<T, C> {

    ServiceResult<T> action(ActionContext<C> context) throws Exception;

    Chainable addHandler(ChainHandler chainHandler);
}
