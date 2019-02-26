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


public class ContactEntity implements Serializable {

	private int userId;
	private String groupName;// 组名
	private String name;
	private String number;
	private String come_from;// 归属地
	private String head_url;// 本地地址
	private String photo;// 网络地址
	private String depName;//工作中心
	private String simpleSpell;// 简拼
	private String wholeSpell;// 全拼
	private String sortLetters;// 首字母
	private String e_mail;
	private String qq;
	private String positionName;// 职务
	private String sex;
	private String age;
	private String job_number;
	private String code;
	private int sortNo;
	
	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getJob_number() {
		return job_number;
	}

	public void setJob_number(String job_number) {
		this.job_number = job_number;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}



	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSimpleSpell() {
		return simpleSpell;
	}

	public void setSimpleSpell(String simpleSpell) {
		this.simpleSpell = simpleSpell;
	}

	public String getWholeSpell() {
		return wholeSpell;
	}

	public void setWholeSpell(String wholeSpell) {
		this.wholeSpell = wholeSpell;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCome_from() {
		return come_from;
	}

	public void setCome_from(String come_from) {
		this.come_from = come_from;
	}

	public String getHead_url() {
		return head_url;
	}

	public void setHead_url(String head_url) {
		this.head_url = head_url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
