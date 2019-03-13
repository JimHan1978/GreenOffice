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

import java.util.List;

public class GroupContactEntity {

	private String groupName;// 组名
	private int userNum;
	private List<ContactEntity> list;
	
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<ContactEntity> getList() {
		return list;
	}
	public void setList(List<ContactEntity> list) {
		this.list = list;
	}
	
}
  