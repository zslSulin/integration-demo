package com.mine.integration.statemachine.amap.anno;

import com.mine.integration.statemachine.amap.enums.OrderEventEnum;
import com.mine.integration.statemachine.amap.enums.OrderStateEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 状态机引擎的处理器注解标识
 *
 * url_link: https://mp.weixin.qq.com/s/0GfCOUEw4svvSQVoShjJDw##
 *
 * @author chisu
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Component
public @interface OrderProcessor {

    /**
     * 指定状态，state不能同时存在
     */
    OrderStateEnum[] state() default {};

    /**
     * 订单操作事件
     */
    OrderEventEnum event();

    /**
     * 业务
     */
    String[] bizCode() default {};

    /**
     * 场景
     */
    String[] sceneId() default {};
}
