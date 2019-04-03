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

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.cndemoz.avalidations.ValidationExecutor;

public class PasswordValidation extends ValidationExecutor {

	@Override
	public boolean doValidate(Context context, String text) {
		if (TextUtils.isEmpty(text)) {
			Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		} else if (text.length() < 6 || text.length() > 16) {
			Toast.makeText(context, "请输入大于等于6位或小于等于16位的密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;

	}
}
