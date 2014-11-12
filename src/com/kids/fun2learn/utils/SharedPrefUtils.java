package com.kids.fun2learn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtils {

	public static final String PREFS_NAME = "In_App_purchaed_Settings";
	public static final String PREFS_KEY = "In_App_purchaed_pref_key";


	public static void putHasPurchased(Context context,Boolean hasPurchased) {
		SharedPreferences prefs = context.getSharedPreferences(
				SharedPrefUtils.PREFS_NAME, 2);
		Editor edit = prefs.edit();
		edit.putBoolean(SharedPrefUtils.PREFS_KEY, hasPurchased);
		edit.commit();
	}

	public static boolean getIsPurchased(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				SharedPrefUtils.PREFS_NAME, 2);
		return prefs.getBoolean(SharedPrefUtils.PREFS_KEY, false);
	}

}
