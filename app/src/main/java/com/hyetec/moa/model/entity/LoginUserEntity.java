package com.hyetec.moa.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述：用户实体类
 **/
public class LoginUserEntity implements Serializable {


    /**
     * joindate : 2006-02-06
     * oaMenus : [{"menuIcon":"upload/f6052bb6-552c-4d9b-b1c6-09459cc83009_活动.png","menuCode":"event","menuName":"活动"},{"menuIcon":"upload/0cd3b09d-e241-4b8d-b1e3-75e9f774aa0a_红包.png","menuCode":"lottery","menuName":"红包"},{"menuIcon":"upload/e6f432fd-7c75-4136-8b92-84880b876d89_报表.png","menuCode":"bill","menuName":"账单"},{"menuIcon":"upload/f1e181d4-d1b6-42ad-a825-1c8fd402a45e_通知.png","menuCode":"notice","menuName":"通知"}]
     * kqMenus : [{"menuIcon":"upload/ccda3533-9aa9-4c67-a7b0-31dd02afc557_考勤.png","menuCode":"attendence","menuName":"考勤"}]
     * userno : H007
     * sex : 117
     * roles : ["GSTJ","GSGL","USER","SUP_USER","KHJL"]
     * userName : 韩锦明
     * userId : 44
     */

    private String joindate;
    private String userno;
    private int sex;
    private String userName;
    private int userId;
    private List<OaMenusBean> oaMenus;
    private List<KqMenusBean> kqMenus;
    private List<String> roles;

    public String getJoindate() {
        return joindate;
    }

    public void setJoindate(String joindate) {
        this.joindate = joindate;
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<OaMenusBean> getOaMenus() {
        return oaMenus;
    }

    public void setOaMenus(List<OaMenusBean> oaMenus) {
        this.oaMenus = oaMenus;
    }

    public List<KqMenusBean> getKqMenus() {
        return kqMenus;
    }

    public void setKqMenus(List<KqMenusBean> kqMenus) {
        this.kqMenus = kqMenus;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public static class OaMenusBean implements  Serializable{
        /**
         * menuIcon : upload/f6052bb6-552c-4d9b-b1c6-09459cc83009_活动.png
         * menuCode : event
         * menuName : 活动
         */

        private String menuIcon;
        private String menuCode;
        private String menuName;

        public String getMenuIcon() {
            return menuIcon;
        }

        public void setMenuIcon(String menuIcon) {
            this.menuIcon = menuIcon;
        }

        public String getMenuCode() {
            return menuCode;
        }

        public void setMenuCode(String menuCode) {
            this.menuCode = menuCode;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }
    }

    public static class KqMenusBean implements  Serializable{
        /**
         * menuIcon : upload/ccda3533-9aa9-4c67-a7b0-31dd02afc557_考勤.png
         * menuCode : attendence
         * menuName : 考勤
         */

        private String menuIcon;
        private String menuCode;
        private String menuName;

        public String getMenuIcon() {
            return menuIcon;
        }

        public void setMenuIcon(String menuIcon) {
            this.menuIcon = menuIcon;
        }

        public String getMenuCode() {
            return menuCode;
        }

        public void setMenuCode(String menuCode) {
            this.menuCode = menuCode;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }
    }
}
