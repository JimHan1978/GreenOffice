/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.hyetec.moa.model.entity;


import java.io.Serializable;
import java.util.List;


public class ActivityEventEntity implements Serializable {


    /**
     * del_flag : 1
     * venue : 游泳池
     * logoImgUrl : /upload/ic_swimming.png
     * type_name : 游泳活动
     * target_name : 清华园
     * bgImgUrl : /upload/ic_qhy.jpg
     * type : 8
     * target : 12
     * organiser : 218
     * joinDate : 2019-06-03 00:00
     * organiser_name : 向广德
     * id : 29
     * create_date : 2019-06-03 00:00
     * imgList : [{"del_flag":"0","id":14,"rec_id":29,"url":"/upload/office/1559637208053.png"}]
     */

    private String del_flag;
    private String venue;
    private String logoImgUrl;
    private String type_name;
    private String target_name;
    private String bgImgUrl;
    private int type;
    private int target;
    private int organiser;
    private String joinDate;
    private String organiser_name;
    private int id;
    private String create_date;
    private List<ImgListBean> imgList;

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLogoImgUrl() {
        return logoImgUrl;
    }

    public void setLogoImgUrl(String logoImgUrl) {
        this.logoImgUrl = logoImgUrl;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public String getBgImgUrl() {
        return bgImgUrl;
    }

    public void setBgImgUrl(String bgImgUrl) {
        this.bgImgUrl = bgImgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getOrganiser() {
        return organiser;
    }

    public void setOrganiser(int organiser) {
        this.organiser = organiser;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getOrganiser_name() {
        return organiser_name;
    }

    public void setOrganiser_name(String organiser_name) {
        this.organiser_name = organiser_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public List<ImgListBean> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImgListBean> imgList) {
        this.imgList = imgList;
    }

    public static class ImgListBean implements Serializable {
        /**
         * del_flag : 0
         * id : 14
         * rec_id : 29
         * url : /upload/office/1559637208053.png
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
