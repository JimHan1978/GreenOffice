package com.hyetec.moa.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/2/26
 * 描述：用户实体类
 **/
@Entity(tableName = "User")
public class UserEntity {

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
	@PrimaryKey
	private String userId;

	private String orgId;
	private String email;
	private String positionId;

	private String userName;
	private String code;
	private String orgName;
	private String positionName;
	private String photo;
	private String mobile;

	public UserEntity(@NonNull String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}


	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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
