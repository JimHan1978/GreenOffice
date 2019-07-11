package com.hyetec.moa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

import java.util.Map;

public class SharedPreferencesUtil {

	public static String CHAT_FONT_SIZE = "chat_font_size";
	Context context;

	public static SharedPreferences getSharePreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	public static String getSettingMessage(Context context, String key, String defValue) {
		return getSharePreferences(context).getString(key, defValue);
	}

	public static int getIntSettingMessage(Context context, String key, int defValue) {
		return getSharePreferences(context).getInt(key, defValue);
	}

	public static boolean getBooleanSettingMessage(Context context, String key, boolean defValue) {
		return getSharePreferences(context).getBoolean(key, defValue);
	}

	public SharedPreferencesUtil(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 
	 * 方法说明：保存共享信�?
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void save(Context context, String key, Object value) {
		SharedPreferences sp = getSharePreferences(context);
		Editor editor = sp.edit();
		if (value instanceof String) {
			editor.putString(key, (String) value);
		} else if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}
		editor.commit();
	}

	public static void save(Context context, Map<String, Object> data) {
		SharedPreferences sp = getSharePreferences(context);
		Editor editor = sp.edit();
		for (Map.Entry<String, Object> map : data.entrySet()) {
			String key = map.getKey();
			Object value = map.getValue();
			if (value instanceof String)
				editor.putString(key, (String) value);
			if (value instanceof Boolean)
				editor.putBoolean(key, (Boolean) value);
			if (value instanceof Integer)
				editor.putInt(key, (Integer) value);
		}
		editor.commit();
	}

	public void save(String name, int mode, Map<String, Object> data) {
		SharedPreferences sp = context.getSharedPreferences(name, mode);
		Editor editor = sp.edit();
		for (Map.Entry<String, Object> map : data.entrySet()) {
			String key = map.getKey();
			Object value = map.getValue();
			if (value instanceof String)
				editor.putString(key, (String) value);
			if (value instanceof Boolean)
				editor.putBoolean(key, (Boolean) value);
			if (value instanceof Integer)
				editor.putInt(key, (Integer) value);
		}
		editor.commit();
	}

	public Map<String, ?> getData(String name, int mode) {
		Map<String, ?> data = null;
		SharedPreferences sp = context.getSharedPreferences(name, mode);
		data = sp.getAll();
		return data;
	}

	public void getClean(String name, int mode) {
		SharedPreferences settings = context.getSharedPreferences(name, mode);
		settings.edit().clear().commit();
	}

	/**
	 * 方法说明�?
	 * @return
	 */
	private static ApplicationInfo getApplicationMetaDataMessage(Context context) {

		try {
			return context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getStringMessageFromApplicationMetaData(Context context, String name) {
		ApplicationInfo appInfo = getApplicationMetaDataMessage(context);
		return appInfo.metaData.getString(name);
	}

	public static boolean getBooleanMessageFromApplicationMetaData(Context context, String name) {
		ApplicationInfo appInfo = getApplicationMetaDataMessage(context);
		return appInfo.metaData.getBoolean(name, false);
	}
}
