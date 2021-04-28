package com.mine.integration.dutychain;

import com.mine.integration.dutychain.context.ValidatorContext;

/**
 * ValidatorChain
 *
 * @author zhangsl
 * @date 2021/4/28 22:41
 */
public interface ValidatorChain<T, C> {

    ServiceResult<T> doValidate(ValidatorContext<C> context) throws Exception;

    ValidatorChain addValidator(Validator validator);
}
