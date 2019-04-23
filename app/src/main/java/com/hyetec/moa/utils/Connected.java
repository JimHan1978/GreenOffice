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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connected {
	public static boolean isConnected(Context context) {
	    Object obj = context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (obj != null) {
	      ConnectivityManager conn = (ConnectivityManager) obj;
	      NetworkInfo info = conn.getActiveNetworkInfo();
	      return (info != null && info.isConnected());
	    }
	    return false;
	  }

}
  