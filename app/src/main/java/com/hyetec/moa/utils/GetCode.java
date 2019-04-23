/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */     
package com.hyetec.moa.utils;
    
public class GetCode {
	public static String zaoOrWanState(String code, boolean b){
		if(code.equals("0")){
			return "正常";
		}else if (code.equals("1")) {
			if(b){
				return "迟到";
			}else {
				return "早退";
			}
		}
		return "";
	}
	
	public static String chiOrTuiState(String code, boolean b){
		if(code.equals("1")){
			if(b){
				return "迟到";
			}else {
				return "早退";
			}
		}else if (code.equals("2")) {
			return "活动";
		}else if (code.equals("3")) {
			return "外出";
		}else if (code.equals("4")) {
			return "未打卡";
		}else if (code.equals("0")) {
			return "正常";
		}
		return "";
	}

}
  