package com.hyetec.moa.model.entity;

import java.io.Serializable;

public class ActivityLotteryEntity implements Serializable {

    /**
     * times : 1
     * userno : H062
     * photo : upload/2019/05/16/db3d91c2-88a8-46db-af31-ba1497739e04.jpg
     * userName : 李承泽
     * sumAmount : 8.44
     */

    private int times;
    private String userno;
    private String photo;
    private String userName;
    private double sumAmount;

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(double sumAmount) {
        this.sumAmount = sumAmount;
    }
}
