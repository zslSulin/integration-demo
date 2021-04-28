package com.mine.integration.statemachine.amap.engine;

import com.google.common.collect.Lists;
import com.mine.integration.statemachine.amap.FsmOrder;
import com.mine.integration.statemachine.amap.FsmOrderService;
import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.StateProcessorRegistry;
import com.mine.integration.statemachine.amap.context.StateContext;
import com.mine.integration.statemachine.amap.event.OrderStateEvent;
import com.mine.integration.statemachine.amap.processor.AbstractStateProcessor;
import com.mine.integration.statemachine.amap.processor.StateProcessor;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
public class DefaultOrderFsmEngine implements OrderFsmEngine{

    @Resource
    private FsmOrderService fsmOrderService;

    @Resource
    private StateProcessorRegistry stateProcessorRegistry;

    @Override
    public <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent) throws Exception {
        FsmOrder fsmOrder = null;
        if (orderStateEvent.newCreate()) {
            fsmOrder = this.fsmOrderService.getFsmOrder(orderStateEvent.getOrderId());
            if (fsmOrder == null) {
//                throw new FsmException(ErrorCodeEnum.ORDER_NOT_FOUND);
            }
        }
        return sendEvent(orderStateEvent, fsmOrder);
    }
    @Override
    public <T> ServiceResult<T> sendEvent(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) throws Exception {
        // 构造当前事件上下文
        StateContext context = this.getStateContext(orderStateEvent, fsmOrder);
        // 获取当前事件处理器
        StateProcessor stateProcessor = this.getStateProcessor(context);
        // 执行处理逻辑
        return stateProcessor.action(context);
    }
    private <T> StateProcessor<T, ?> getStateProcessor(StateContext<?> context) {
        OrderStateEvent stateEvent = context.getOrderStateEvent();
        FsmOrder fsmOrder = context.getFsmOrder();
        // 根据状态+事件对象获取所对应的业务处理器集合
        List<AbstractStateProcessor> processorList = stateProcessorRegistry.acquireStateProcess(fsmOrder.getOrderState(),
                stateEvent.getEventType(), fsmOrder.bizCode(), fsmOrder.sceneId());
        if (processorList == null) {
            // 订单状态发生改变
            if (!Objects.isNull(stateEvent.orderState()) && !stateEvent.orderState().equals(fsmOrder.getOrderState())) {
//                throw new FsmException(ErrorCodeEnum.ORDER_STATE_NOT_MATCH);
            }
//            throw new FsmException(ErrorCodeEnum.NOT_FOUND_PROCESSOR);
        }
        // 匹配上下文业务的唯一的处理器
        List<AbstractStateProcessor> processorResult = Lists.newArrayListWithExpectedSize(processorList.size());
        for (AbstractStateProcessor processor : processorList) {
            if (processor.filter(context)) {
                processorResult.add(processor);
            }
        }


        if (CollectionUtils.isEmpty(processorResult)) {
//            throw new FsmException(ErrorCodeEnum.NOT_FOUND_PROCESSOR);
        }
        if (processorResult.size() > 1) {
//            throw new FsmException(ErrorCodeEnum.FOUND_MORE_PROCESSOR);
        }
        return processorResult.get(0);
    }
    private StateContext<?> getStateContext(OrderStateEvent orderStateEvent, FsmOrder fsmOrder) {
        StateContext<?> context = new StateContext(orderStateEvent, fsmOrder);
        return context;
    }



}
