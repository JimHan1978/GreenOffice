package com.hyetec.moa.model.entity;

import java.util.List;

public class ActivityDeleteEntity {

    /**
     * message : 修改成功
     * result : []
     * success : true
     */

    private String message;
    private boolean success;
    private List<?> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }
}
