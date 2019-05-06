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
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class SystemSettings {
	public static String getAppName() {
		return "Exhibits";
	}

	public static String getVersion(Context context)// 获取版本号
	{
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * mobileRegexp = "([0-9]{3})+-([0-9]{4})+-([0-9]{4})+";
	 * 方法说明：比较版本号
	 *
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int compareVersion(String s1, String s2) {
		if (s1 == null && s2 == null)
			return 0;
		else if (s1 == null)
			return -1;
		else if (s2 == null)
			return 1;

		String[] arr1 = s1.split("[^a-zA-Z0-9]+"), arr2 = s2.split("[^a-zA-Z0-9]+");

		int i1, i2, i3;

		for (int ii = 0, max = Math.min(arr1.length, arr2.length); ii <= max; ii++) {
			if (ii == arr1.length)
				return ii == arr2.length ? 0 : -1;
			else if (ii == arr2.length)
				return 1;

			try {
				i1 = Integer.parseInt(arr1[ii]);
			} catch (Exception x) {
				i1 = Integer.MAX_VALUE;
			}

			try {
				i2 = Integer.parseInt(arr2[ii]);
			} catch (Exception x) {
				i2 = Integer.MAX_VALUE;
			}

			if (i1 != i2) {
				return i1 - i2;
			}

			i3 = arr1[ii].compareTo(arr2[ii]);

			if (i3 != 0)
				return i3;
		}
		return 0;
	}

}
