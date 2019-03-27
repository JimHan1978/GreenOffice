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


public class MessageEntity implements Serializable {

	private String mseeageId;
	private String mseeageName;
	private String mseeageTime;
	private String mseeageContent;
	private String isNew;


	public String getMseeageId() {
		return mseeageId;
	}

	public void setMseeageId(String mseeageId) {
		this.mseeageId = mseeageId;
	}

	public String getMseeageName() {
		return mseeageName;
	}

	public void setMseeageName(String mseeageName) {
		this.mseeageName = mseeageName;
	}

	public String getMseeageTime() {
		return mseeageTime;
	}

	public void setMseeageTime(String mseeageTime) {
		this.mseeageTime = mseeageTime;
	}

	public String getMseeageContent() {
		return mseeageContent;
	}

	public void setMseeageContent(String mseeageContent) {
		this.mseeageContent = mseeageContent;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}



}
