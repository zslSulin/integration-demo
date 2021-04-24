package com.mine.integration.statemachine.spring;

import com.mine.integration.enums.statemachine.EventsEnum;
import com.mine.integration.enums.statemachine.StatesEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfig;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.common.annotation.AnnotationBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * Spring 状态机配置
 * <p>
 * Copyright (c) 2018 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author chisu
 * @since 2021/04/23
 */
public class SpringStatemachineConfig extends EnumStateMachineConfigurerAdapter<StatesEnum, EventsEnum> {


    @Override
    public void configure(StateMachineConfigBuilder<StatesEnum, EventsEnum> config) throws Exception {
        super.configure(config);
    }

    @Override
    public void configure(StateMachineModelConfigurer<StatesEnum, EventsEnum> model) throws Exception {
        super.configure(model);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<StatesEnum, EventsEnum> config) throws Exception {
        config.withConfiguration().listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<StatesEnum, EventsEnum> states) throws Exception {
        states
                .withStates()
                .initial(StatesEnum.NO_PAY)
                .states(EnumSet.allOf(StatesEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<StatesEnum, EventsEnum> transitions) throws Exception {
        transitions
                .withExternal()
                .source(StatesEnum.NO_PAY).target(StatesEnum.PAY_SUCCESS)
                .event(EventsEnum.PAY_SUCCESS)
                .and()
                .withExternal()
                .source(StatesEnum.NO_PAY).target(StatesEnum.CANCEL)
                .event(EventsEnum.CANCEL)
                .and()
                .withExternal()
                .source(StatesEnum.PAY_SUCCESS).target(StatesEnum.REFUND_APPLY)
                .event(EventsEnum.REFUND_APPLY)
                .and();
    }

    @Override
    public boolean isAssignable(AnnotationBuilder<StateMachineConfig<StatesEnum, EventsEnum>> builder) {
        return super.isAssignable(builder);
    }


    @Bean
    private StateMachineListener<StatesEnum, EventsEnum> listener() {
        return new StateMachineListenerAdapter<StatesEnum, EventsEnum>() {
            @Override
            public void transition(Transition<StatesEnum, EventsEnum> transition) {
                super.transition(transition);
            }
        };
    }
}
