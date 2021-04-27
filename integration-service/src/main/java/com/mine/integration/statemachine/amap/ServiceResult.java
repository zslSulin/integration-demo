package com.mine.integration.statemachine.amap;

/**
 * ServiceResult
 *
 * @author zhangsl
 * @date 2021/4/27 21:47
 */
public class ServiceResult<T> {

    private Boolean success;

    private T result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Boolean isSuccess() {
        if (success == null) {
            return Boolean.FALSE;
        }
        return success;
    }
}
