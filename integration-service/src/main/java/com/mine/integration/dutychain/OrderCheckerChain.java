package com.mine.integration.dutychain;

import java.util.List;

/**
 * OrderCheckerChain
 *
 * @author zhangsl
 * @date 2021/4/29 22:00
 */
public class OrderCheckerChain<T, C> extends AbstractChain<T, C> {
    @Override
    public List<ChainHandler> acquireChainHandler() {
        return null;
    }
}
