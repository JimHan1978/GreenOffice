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


public class BillEntity implements Serializable {


    /**
     * detail : {"date":"","sjkk":"MA==","bjkk":"MA==","activityYoyo":"OQ==","gjjlmksr":"MTI2OA==","beatQtjlsrPercent":"MCU=","dksbgjj":"MTAxMA==","gdbtksr":"MTUw","id":"MTkxMw==","xjflsr":"NjA1","attendanceYoyoPercent":"NDgl","zhgzksr":"NDc1MA==","email":"emhhb3pob25nbmFuQGh5ZXRlYy5jb20=","fId":"MTAz","txbt":"MTAw","seecount":"MTA=","attendanceYoyo":"Nw==","bysrhj":"Njc4OQ==","activityYoyoPercent":"MjQl","userno":"SDAyNg==","gs":"MA==","jjsr":"MC4w","cdkk":"MA==","beatBonusIncomePercent":"MCU=","dnbt":"NTA=","sqgzhj":"NTc2MA==","beatXjflsrPercent":"NjUl","jxjc":"MA==","name":"6LW16ZKf5qWg"}
     * historyData : [{"bysrhj":"MC4w","ym":"MjAxOS0wMQ=="},{"bysrhj":"MC4w","ym":"MjAxOS0wMg=="},{"bysrhj":"MC4w","ym":"MjAxOS0wMw=="},{"bysrhj":"MC4w","ym":"MjAxOS0wNA=="},{"bysrhj":"MC4w","ym":"MjAxOS0wNQ=="},{"bysrhj":"Njc4OQ==","ym":"MjAxOS0wNg=="}]
     */

    private DetailBean detail;

    @Override
    public String toString() {
        return "BillEntity{" +
                "detail=" + detail +
                ", historyData=" + historyData +
                '}';
    }

    private List<HistoryDataBean> historyData;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public List<HistoryDataBean> getHistoryData() {
        return historyData;
    }

    public void setHistoryData(List<HistoryDataBean> historyData) {
        this.historyData = historyData;
    }

    public static class DetailBean {
        /**
         * date :
         * sjkk : MA==
         * bjkk : MA==
         * activityYoyo : OQ==
         * gjjlmksr : MTI2OA==
         * beatQtjlsrPercent : MCU=
         * dksbgjj : MTAxMA==
         * gdbtksr : MTUw
         * id : MTkxMw==
         * xjflsr : NjA1
         * attendanceYoyoPercent : NDgl
         * zhgzksr : NDc1MA==
         * email : emhhb3pob25nbmFuQGh5ZXRlYy5jb20=
         * fId : MTAz
         * txbt : MTAw
         * seecount : MTA=
         * attendanceYoyo : Nw==
         * bysrhj : Njc4OQ==
         * activityYoyoPercent : MjQl
         * userno : SDAyNg==
         * gs : MA==
         * jjsr : MC4w
         * cdkk : MA==
         * beatBonusIncomePercent : MCU=
         * dnbt : NTA=
         * sqgzhj : NTc2MA==
         * beatXjflsrPercent : NjUl
         * jxjc : MA==
         * name : 6LW16ZKf5qWg
         */

        private String date;
        private String sjkk;
        private String bjkk;
        private String activityYoyo;
        private String gjjlmksr;
        private String beatQtjlsrPercent;
        private String dksbgjj;
        private String gdbtksr;
        private String id;
        private String xjflsr;
        private String attendanceYoyoPercent;
        private String zhgzksr;
        private String email;
        private String fId;
        private String txbt;
        private String seecount;
        private String attendanceYoyo;
        private String bysrhj;
        private String activityYoyoPercent;
        private String userno;
        private String gs;
        private String jjsr;
        private String cdkk;
        private String beatBonusIncomePercent;
        private String dnbt;
        private String sqgzhj;
        private String beatXjflsrPercent;
        private String jxjc;
        private String name;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSjkk() {
            return sjkk;
        }

        public void setSjkk(String sjkk) {
            this.sjkk = sjkk;
        }

        public String getBjkk() {
            return bjkk;
        }

        public void setBjkk(String bjkk) {
            this.bjkk = bjkk;
        }

        public String getActivityYoyo() {
            return activityYoyo;
        }

        public void setActivityYoyo(String activityYoyo) {
            this.activityYoyo = activityYoyo;
        }

        public String getGjjlmksr() {
            return gjjlmksr;
        }

        public void setGjjlmksr(String gjjlmksr) {
            this.gjjlmksr = gjjlmksr;
        }

        public String getBeatQtjlsrPercent() {
            return beatQtjlsrPercent;
        }

        public void setBeatQtjlsrPercent(String beatQtjlsrPercent) {
            this.beatQtjlsrPercent = beatQtjlsrPercent;
        }

        public String getDksbgjj() {
            return dksbgjj;
        }

        public void setDksbgjj(String dksbgjj) {
            this.dksbgjj = dksbgjj;
        }

        public String getGdbtksr() {
            return gdbtksr;
        }

        public void setGdbtksr(String gdbtksr) {
            this.gdbtksr = gdbtksr;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getXjflsr() {
            return xjflsr;
        }

        public void setXjflsr(String xjflsr) {
            this.xjflsr = xjflsr;
        }

        public String getAttendanceYoyoPercent() {
            return attendanceYoyoPercent;
        }

        public void setAttendanceYoyoPercent(String attendanceYoyoPercent) {
            this.attendanceYoyoPercent = attendanceYoyoPercent;
        }

        public String getZhgzksr() {
            return zhgzksr;
        }

        public void setZhgzksr(String zhgzksr) {
            this.zhgzksr = zhgzksr;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFId() {
            return fId;
        }

        public void setFId(String fId) {
            this.fId = fId;
        }

        public String getTxbt() {
            return txbt;
        }

        public void setTxbt(String txbt) {
            this.txbt = txbt;
        }

        public String getSeecount() {
            return seecount;
        }

        public void setSeecount(String seecount) {
            this.seecount = seecount;
        }

        public String getAttendanceYoyo() {
            return attendanceYoyo;
        }

        public void setAttendanceYoyo(String attendanceYoyo) {
            this.attendanceYoyo = attendanceYoyo;
        }

        public String getBysrhj() {
            return bysrhj;
        }

        public void setBysrhj(String bysrhj) {
            this.bysrhj = bysrhj;
        }

        public String getActivityYoyoPercent() {
            return activityYoyoPercent;
        }

        public void setActivityYoyoPercent(String activityYoyoPercent) {
            this.activityYoyoPercent = activityYoyoPercent;
        }

        public String getUserno() {
            return userno;
        }

        public void setUserno(String userno) {
            this.userno = userno;
        }

        public String getGs() {
            return gs;
        }

        public void setGs(String gs) {
            this.gs = gs;
        }

        public String getJjsr() {
            return jjsr;
        }

        public void setJjsr(String jjsr) {
            this.jjsr = jjsr;
        }

        public String getCdkk() {
            return cdkk;
        }

        public void setCdkk(String cdkk) {
            this.cdkk = cdkk;
        }

        public String getBeatBonusIncomePercent() {
            return beatBonusIncomePercent;
        }

        public void setBeatBonusIncomePercent(String beatBonusIncomePercent) {
            this.beatBonusIncomePercent = beatBonusIncomePercent;
        }

        public String getDnbt() {
            return dnbt;
        }

        public void setDnbt(String dnbt) {
            this.dnbt = dnbt;
        }

        public String getSqgzhj() {
            return sqgzhj;
        }

        public void setSqgzhj(String sqgzhj) {
            this.sqgzhj = sqgzhj;
        }

        public String getBeatXjflsrPercent() {
            return beatXjflsrPercent;
        }

        public void setBeatXjflsrPercent(String beatXjflsrPercent) {
            this.beatXjflsrPercent = beatXjflsrPercent;
        }

        public String getJxjc() {
            return jxjc;
        }

        public void setJxjc(String jxjc) {
            this.jxjc = jxjc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class HistoryDataBean {
        /**
         * bysrhj : MC4w
         * ym : MjAxOS0wMQ==
         */

        private String bysrhj;
        private String ym;

        public String getBysrhj() {
            return bysrhj;
        }

        public void setBysrhj(String bysrhj) {
            this.bysrhj = bysrhj;
        }

        public String getYm() {
            return ym;
        }

        public void setYm(String ym) {
            this.ym = ym;
        }
    }
}
