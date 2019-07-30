package com.hyetec.moa.model.entity;

public class LeaveMessageEntity {

    /**
     * code : 200
     * message : 添加成功
     * result :
     * success : true
     */

    private int code;
    private String message;
    private String result;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
