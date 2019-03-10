package com.hyetec.moa.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述：用户实体类
 **/
@Entity(tableName = "User")
public class UserEntity implements Serializable {


    /**
     * orgId : 4
     * email : hanjinming@hyetec.com
     * positionId : 29
     * userId : 44
     * userName : 韩锦明
     * code : H007
     * orgName : 创新孵化部
     * positionName : Android工程师
     * photo : upload/2016/08/24/a7af8c696a5f99e399df716d899effea_hjm.jpg
     * mobile : 18600261196
     */

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private int orgId;

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    private int deptId;
    private String email;
    private int positionId;

    private String userName;
    private String code;
    private String orgName;

    //@ColumnInfo()
    private String positionName;
    private String photo;
    private String mobile;
    private boolean delFlag;

    private String shortName;// 简拼
    private String pinyinName;// 全拼
    private String initialIndex;// 首字母


    public UserEntity(@NonNull int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }


    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getInitialIndex() {
        return initialIndex;
    }

    public void setInitialIndex(String initialIndex) {
        this.initialIndex = initialIndex;
    }


    public boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }


    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
