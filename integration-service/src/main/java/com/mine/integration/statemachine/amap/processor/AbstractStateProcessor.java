package com.mine.integration.statemachine.amap.processor;

import com.mine.integration.statemachine.amap.ServiceResult;
import com.mine.integration.statemachine.amap.StateActionStep;
import com.mine.integration.statemachine.amap.check.Checkable;
import com.mine.integration.statemachine.amap.check.CheckerExecutor;
import com.mine.integration.statemachine.amap.context.StateContext;
import com.mine.integration.statemachine.amap.plugin.PluginExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 状态机处理器模板类
 *
 * @author zhangsl
 * @date 2021/4/27 21:52
 */
@Component
public abstract class AbstractStateProcessor<T, C> implements StateProcessor<T, C>, StateActionStep<T, C> {

    @Resource
    private CheckerExecutor checkerExecutor;
    @Resource
    private PluginExecutor pluginExecutor;

    @Override
    public final ServiceResult<T> action(StateContext<C> context) throws Exception {
        ServiceResult<T> result = null;
        Checkable checkable = this.getCheckable(context);
        try {
            // 参数校验
            result = checkerExecutor.serialCheck(checkable.getParameterCheck(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // 数据准备
            this.prepare(context);
            // 串行校验器
//            result = this.check(context);
            result = checkerExecutor.serialCheck(checkable.getSyncChecker(), context);
            if (!result.isSuccess()) {
                return result;
            }
            // 并行校验器
            result = checkerExecutor.parallelCheck(checkable.getAsyncChecker(), context);
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
            // 在action和save直接执行插件逻辑
            this.pluginExecutor.parallelExecutor(context);

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

    public boolean filter(StateContext<C> context) {
        return true;
    };
}
