package com.mine.integration.dutychain;

import lombok.Getter;
import lombok.Setter;

/**
 * ServiceResult
 *
 * @author zhangsl
 * @date 2021/4/28 22:47
 */
@Getter
@Setter
public class ServiceResult<T> {

    private Boolean success;

    private T result;

    private String msg;

    public Boolean isSuccess() {
        return success == null ? Boolean.FALSE : success;
    }

    public Boolean isFail() {
        return !isSuccess();
    }

    public ServiceResult() {
        this.success = Boolean.TRUE;
    }
}
