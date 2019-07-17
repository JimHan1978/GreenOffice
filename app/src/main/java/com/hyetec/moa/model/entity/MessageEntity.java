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


	/**
	 * date : 2019-04-03
	 * messageType : report
	 * subTitle : 您2019年4月收入账单已生成，请查收>>>
	 * seeCount : 0
	 * yearAndMonth : 2019-04
	 * title : 月度报告
	 */

	private String date;
	private String messageType;
	private String subTitle;
	private int seeCount;
	private String yearAndMonth;
	private String title;

	public int getOrganiser() {
		return organiser;
	}

	public void setOrganiser(int organiser) {
		this.organiser = organiser;
	}

	private int organiser;
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	private int type;
	private int actId;
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	private String iconUrl;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public int getSeeCount() {
		return seeCount;
	}

	public void setSeeCount(int seeCount) {
		this.seeCount = seeCount;
	}

	public String getYearAndMonth() {
		return yearAndMonth;
	}

	public void setYearAndMonth(String yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
