package com.hyetec.moa.model.entity;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述：用户实体类
 **/
@Entity(tableName = "User")
public class LoginUserEntity implements Serializable {


    /**
     * joindate : 2015-07-21
     * userno : H026
     * sex : 117
     * userName : 赵钟楠
     * userId : 73
     */

    private String joindate;
    private String userno;
    private int sex;
    private String userName;
    private int userId;

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
}
