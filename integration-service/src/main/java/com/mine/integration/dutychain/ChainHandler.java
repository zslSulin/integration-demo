package com.mine.integration.dutychain;

import com.mine.integration.dutychain.context.ActionContext;

/**
 * Validtor
 *
 * @author zhangsl
 * @date 2021/4/28 22:40
 */
public interface ChainHandler<T, C> {

//    /**
//     * 需要引入责任链的时候，则采用此方法
//     * @param context 上下文
//     * @param chain chain
//     * @return 处理结果
//     * @throws Exception 执行异常
//     */
//    default ServiceResult<T> doValidate(ActionContext<C> context, Chainable chain) throws Exception {
//        ServiceResult<T> result = this.doValidate(context);
//        //校验失败了，就直接返回
//        if(result.isFail()) {
//            return result;
//        }
//        //否则往责任链中下一个校验器进行处理
//        return chain.action(context);
//    }

    /**
     * 不需要责任链的时候，则可以直接调用此方法的实现即可
     */
    default ServiceResult<T> execute(ActionContext<C> context) throws Exception {
        return new ServiceResult<>();
    }

    /**
     * 多执行器下 按顺序处理
     */
    default int order() {
        return 0;
    }
}
