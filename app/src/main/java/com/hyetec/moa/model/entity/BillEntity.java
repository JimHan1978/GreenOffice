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
     * beatXjflsrPercent : 0
     * beatQtjlsrPercent : 0
     * detail : {"date":"","fId":32,"txbt":"100","bysrhj":"5800","sjkk":"10","bjkk":"10","userno":"H065","gs":"500","qtjlsr":"10","jjsr":"100","cdkk":"10","dnbt":"300","sqgzhj":"6700","gjjlmksr":"1388","jxjc":"10000","name":"孙浩","dksbgjj":"1000","gdbtksr":"200","id":54,"xjflsr":"100","zhgzksr":"5600","email":"sunhao@hyetec.com"}
     * historyData : [{"bysrhj":"5800","ym":"2019-02"},{"bysrhj":"6000","ym":"2019-01"},{"bysrhj":"5800","ym":"2019-03"}]
     */

    private String beatXjflsrPercent;
    private String beatQtjlsrPercent;
    private DetailBean detail;
    private List<HistoryDataBean> historyData;

    public String getBeatXjflsrPercent() {
        return beatXjflsrPercent;
    }

    public void setBeatXjflsrPercent(String beatXjflsrPercent) {
        this.beatXjflsrPercent = beatXjflsrPercent;
    }

    public String getBeatQtjlsrPercent() {
        return beatQtjlsrPercent;
    }

    public void setBeatQtjlsrPercent(String beatQtjlsrPercent) {
        this.beatQtjlsrPercent = beatQtjlsrPercent;
    }

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
         * fId : 32
         * txbt : 100
         * bysrhj : 5800
         * sjkk : 10
         * bjkk : 10
         * userno : H065
         * gs : 500
         * qtjlsr : 10
         * jjsr : 100
         * cdkk : 10
         * dnbt : 300
         * sqgzhj : 6700
         * gjjlmksr : 1388
         * jxjc : 10000
         * name : 孙浩
         * dksbgjj : 1000
         * gdbtksr : 200
         * id : 54
         * xjflsr : 100
         * zhgzksr : 5600
         * email : sunhao@hyetec.com
         */

        private String date;
        private int fId;
        private String txbt;
        private String bysrhj;
        private String sjkk;
        private String bjkk;
        private String userno;
        private String gs;
        private String qtjlsr;
        private String jjsr;
        private String cdkk;
        private String dnbt;
        private String sqgzhj;
        private String gjjlmksr;
        private String jxjc;
        private String name;
        private String dksbgjj;
        private String gdbtksr;
        private int id;
        private String xjflsr;
        private String zhgzksr;
        private String email;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getFId() {
            return fId;
        }

        public void setFId(int fId) {
            this.fId = fId;
        }

        public String getTxbt() {
            return txbt;
        }

        public void setTxbt(String txbt) {
            this.txbt = txbt;
        }

        public String getBysrhj() {
            return bysrhj;
        }

        public void setBysrhj(String bysrhj) {
            this.bysrhj = bysrhj;
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

        public String getQtjlsr() {
            return qtjlsr;
        }

        public void setQtjlsr(String qtjlsr) {
            this.qtjlsr = qtjlsr;
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

        public String getGjjlmksr() {
            return gjjlmksr;
        }

        public void setGjjlmksr(String gjjlmksr) {
            this.gjjlmksr = gjjlmksr;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getXjflsr() {
            return xjflsr;
        }

        public void setXjflsr(String xjflsr) {
            this.xjflsr = xjflsr;
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
    }

    public static class HistoryDataBean {
        /**
         * bysrhj : 5800
         * ym : 2019-02
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
