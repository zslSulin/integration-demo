package com.mine.integration.dutychain;

import com.mine.integration.dutychain.context.ActionContext;

import java.util.Comparator;
import java.util.List;

/**
 * AbstractChainHandler
 *
 * @author zhangsl
 * @date 2021/4/29 20:48
 */
public abstract class AbstractChain<T, C> implements Chainable<T, C> {

    @Override
    public ServiceResult<T> action(ActionContext<C> context) throws Exception {
        List<ChainHandler> chainHandlers = acquireChainHandler();
        chainHandlers.sort(Comparator.comparing(ChainHandler::order));
        for (ChainHandler chainHandler : chainHandlers) {
            ServiceResult serviceResult = chainHandler.execute(context);
            if (serviceResult.isFail()) {
                return serviceResult;
            }
        }
        return new ServiceResult<>();
    }

    @Override
    public Chainable addHandler(ChainHandler chainHandler) {


        return null;
    }

    /**
     * 获取链路上的处理器
     *
     * @return 处理器集合
     */
    public abstract List<ChainHandler> acquireChainHandler();
}
