package com.hyetec.moa.model.entity;

import java.io.Serializable;
import java.util.List;

public class UploadEntity implements Serializable {

    /**
     * message : 保存成功
     * result : [{"del_flag":"0","id":22,"rec_id":62,"url":"/upload/office/1562722832917.jpg"},{"del_flag":"0","id":23,"rec_id":62,"url":"/upload/office/1562726701854.jpg"}]
     * success : true
     */

    private String message;
    private boolean success;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * del_flag : 0
         * id : 22
         * rec_id : 62
         * url : /upload/office/1562722832917.jpg
         */

        private String del_flag;
        private int id;
        private int rec_id;
        private String url;

        public String getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(String del_flag) {
            this.del_flag = del_flag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRec_id() {
            return rec_id;
        }

        public void setRec_id(int rec_id) {
            this.rec_id = rec_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
