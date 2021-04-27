package com.mine.integration.statemachine.amap;

import org.springframework.stereotype.Component;

/**
 * 状态机处理器模板类
 *
 * @author zhangsl
 * @date 2021/4/27 21:52
 */
@Component
public abstract class AbstractStateProcessor<T, C> implements StateProcessor<T, C>, StateActionStep<T, C> {

    @Override
    public final ServiceResult<T> action(StateContext<C> context) throws Exception {
        ServiceResult<T> result = null;
        try {
            // 数据准备
            this.prepare(context);
            // 串行校验器
            result = this.check(context);
            if (!result.isSuccess()) {
                return result;
            }
            // getNextState不能在prepare前，因为有的nextState是根据prepare中的数据转换而来
            String nextState = this.getNextState(context);
            // 业务处理
            result = this.action(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // 持久化
            result = this.save(nextState, context);
            if (!result.isSuccess()) {
                return result;
            }
            // after
            this.after(context);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}
