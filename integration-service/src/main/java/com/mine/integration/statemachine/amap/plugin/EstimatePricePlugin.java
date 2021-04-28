package com.mine.integration.statemachine.amap.plugin;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.anno.ProcessorPlugin;
import com.mine.integration.statemachine.amap.context.CreateOrderContext;
import com.mine.integration.statemachine.amap.context.StateContext;
import com.mine.integration.statemachine.amap.enums.OrderEventEnum;
import com.mine.integration.statemachine.amap.enums.OrderStateEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
@ProcessorPlugin(state = OrderStateEnum.INIT, event = OrderEventEnum.CREATE, bizCode = "BUSINESS")
public class EstimatePricePlugin implements PluginHandler<String, CreateOrderContext> {

    @Override
    public ServiceResult<String> action(StateContext<CreateOrderContext> context) throws Exception {
        String price = StringUtils.EMPTY;
        context.getContext().setEstimatePriceInfo(price);
        return new ServiceResult<>();
    }
}
