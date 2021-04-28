package com.mine.integration.statemachine.amap.processor;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.anno.OrderProcessor;
import com.mine.integration.statemachine.amap.check.Checkable;
import com.mine.integration.statemachine.amap.check.Checker;
import com.mine.integration.statemachine.amap.check.CreateParamChecker;
import com.mine.integration.statemachine.amap.check.UnfinshChecker;
import com.mine.integration.statemachine.amap.check.UserChecker;
import com.mine.integration.statemachine.amap.context.CreateOrderContext;
import com.mine.integration.statemachine.amap.context.StateContext;
import com.mine.integration.statemachine.amap.enums.OrderEventEnum;
import com.mine.integration.statemachine.amap.enums.OrderStateEnum;
import com.mine.integration.statemachine.amap.pojo.OrderInfo;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * OrderCreatedProcessor
 * <p>
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/28
 */
@Slf4j
@OrderProcessor(state = OrderStateEnum.INIT, event = OrderEventEnum.CREATE, sceneId = "H5")
public class OrderCreateProcessor extends AbstractStateProcessor<String, CreateOrderContext> {

    @Resource
    private CreateParamChecker createParamChecker;

    @Resource
    private UserChecker userChecker;

    @Resource
    private UnfinshChecker unfinshChecker;

    @Override
    public Checkable getCheckable(StateContext<CreateOrderContext> context) {
        return new Checkable() {
            @Override
            public List<Checker> getParameterCheck() {
                return Arrays.asList(createParamChecker);
            }

            @Override
            public List<Checker> getSyncChecker() {
                return Collections.EMPTY_LIST;
            }

            @Override
            public List<Checker> getAsyncChecker() {
                return Arrays.asList(userChecker, unfinshChecker);
            }
        };
    }

    @Override
    public ServiceResult<String> check(StateContext<CreateOrderContext> context) {
        return null;
    }

    @Override
    public String getNextState(StateContext<CreateOrderContext> context) {
//        if (context.getOrderStateEvent().getEventType().equals("xxx")) {
//            return OrderStateEnum.INIT.name();
//        }
        return OrderStateEnum.INIT.name();
    }

    @Override
    public ServiceResult<String> action(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        return null;
    }

    @Override
    public ServiceResult<String> save(String nextState, StateContext<CreateOrderContext> context) throws Exception {
        OrderInfo orderInfo = context.getContext().getOrderInfo();
        // 更新状态
        orderInfo.setOrderState(nextState);
        // 持久化
//        this.updateOrderInfo(orderInfo)
        // log
        return new ServiceResult<>(orderInfo.getOrderId(), "下单成功");
    }

    @Override
    public void after(StateContext<CreateOrderContext> context) {
        // 事务消息
    }
}
