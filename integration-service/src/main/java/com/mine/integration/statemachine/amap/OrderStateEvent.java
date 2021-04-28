package com.mine.integration.statemachine.amap;

/**
 * 订单状态迁移事件
 *
 * @author zhangsl
 * @date 2021/4/27 21:39
 */
public interface OrderStateEvent {

    /**
     * 订单状态事件
     */
     String  getEventType();

    /**
     * 订单id
     */
    String getOrderId();

    /**
     * 如果orderState不为空，则代表只有当前状态才进行转移
     */
    default String orderState() {
        return null;
    }

    /**
     * 是否要创建订单
     */
    boolean newCreate();

}
