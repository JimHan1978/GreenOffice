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
import com.hyetec.moa.model.api.Api;


public class ConfirmPasswordValidation extends ValidationExecutor {

	@Override
	public boolean doValidate(Context context, String text) {
		// TODO Auto-generated method stub
		String confirmPassword = CustomConstants.confirmPassword;
		if (!TextUtils.isEmpty(text)) {
			if (text.equals(confirmPassword)) {
				return true;
			} else {
				Toast.makeText(context, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
				return false;

			}
		} else {
			Toast.makeText(context, "请输入确认密码", Toast.LENGTH_SHORT).show();
			return false;
		}

	}

}
