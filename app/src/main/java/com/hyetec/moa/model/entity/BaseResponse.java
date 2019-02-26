package com.hyetec.moa.model.entity;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述：
 **/
public class BaseResponse<T>{

    /**
     * message : 个人信息获取成功。
     * result : {}
     * success : true
     */

    private String message;
    private T result;
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class ResultBean {
    }
}
