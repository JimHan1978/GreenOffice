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


public class PunchCardEntity implements Serializable {


    /**
     * attetype : 2
     * code :
     * attedate : 2019-04-17 00:00:00
     * offworktype : 9
     * attemonth : 2019-04
     * atteweekday : 周三
     * toworktime : 2019-04-17 08:47:13
     * userid : 73
     * toworkremark :
     * ischeck : 0
     * offworkremark :
     * toworktype : 0
     * orgname :
     * planofftime : 2019-04-17 17:30:00
     * username :
     * offworktime :
     */

    private String attetype;
    private String code;
    private String attedate;
    private String offworktype;
    private String attemonth;
    private String atteweekday;
    private String toworktime;
    private String userid;
    private String toworkremark;
    private String ischeck;
    private String offworkremark;
    private String toworktype;
    private String orgname;
    private String planofftime;
    private String username;
    private String offworktime;
    private int remainder;
    private double lotteryAmount;
    public double getLotteryAmount() {
        return lotteryAmount;
    }

    public void setLotteryAmount(double lotteryAmount) {
        this.lotteryAmount = lotteryAmount;
    }




    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }



    public String getAttetype() {
        return attetype;
    }

    public void setAttetype(String attetype) {
        this.attetype = attetype;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAttedate() {
        return attedate;
    }

    public void setAttedate(String attedate) {
        this.attedate = attedate;
    }

    public String getOffworktype() {
        return offworktype;
    }

    public void setOffworktype(String offworktype) {
        this.offworktype = offworktype;
    }

    public String getAttemonth() {
        return attemonth;
    }

    public void setAttemonth(String attemonth) {
        this.attemonth = attemonth;
    }

    public String getAtteweekday() {
        return atteweekday;
    }

    public void setAtteweekday(String atteweekday) {
        this.atteweekday = atteweekday;
    }

    public String getToworktime() {
        return toworktime;
    }

    public void setToworktime(String toworktime) {
        this.toworktime = toworktime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getToworkremark() {
        return toworkremark;
    }

    public void setToworkremark(String toworkremark) {
        this.toworkremark = toworkremark;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getOffworkremark() {
        return offworkremark;
    }

    public void setOffworkremark(String offworkremark) {
        this.offworkremark = offworkremark;
    }

    public String getToworktype() {
        return toworktype;
    }

    public void setToworktype(String toworktype) {
        this.toworktype = toworktype;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getPlanofftime() {
        return planofftime;
    }

    public void setPlanofftime(String planofftime) {
        this.planofftime = planofftime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOffworktime() {
        return offworktime;
    }

    public void setOffworktime(String offworktime) {
        this.offworktime = offworktime;
    }
}
