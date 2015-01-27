package com.kids.fun2learn.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefUtils {
	public static final String SOUND_COUTING_SCREEN_SETTINGS_PREFS_NAME = "counting_screen_sound_settings";
	public static final String SOUND_COUTING_SCREEN__PREFS_KEY = "counting_screen_sound_settings_key";
	public static final String SOUND_SETTINGS_PREFS_NAME = "sound_settings";
	public static final String SOUND_SETTINGS_PREFS_KEY = "sound_settings_key";
	public static final String PREFS_NAME = "In_App_purchaed_Settings";
	public static final String PREFS_KEY = "In_App_purchaed_pref_key";

	public static void putHasPurchased(Context context, Boolean hasPurchased) {
		SharedPreferences prefs = context.getSharedPreferences(SharedPrefUtils.PREFS_NAME, 2);
		Editor edit = prefs.edit();
		edit.putBoolean(SharedPrefUtils.PREFS_KEY, hasPurchased);
		edit.commit();
	}

	public static boolean getIsPurchased(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(SharedPrefUtils.PREFS_NAME, 2);
		return prefs.getBoolean(SharedPrefUtils.PREFS_KEY, false);// TODO
	}

	public static void setSoundSetiings(Context context, Boolean onOff) {
		SharedPreferences prefs = context.getSharedPreferences(SharedPrefUtils.SOUND_SETTINGS_PREFS_NAME, 2);
		Editor edit = prefs.edit();
		edit.putBoolean(SharedPrefUtils.SOUND_SETTINGS_PREFS_KEY, onOff);
		edit.commit();
	}

	public static boolean getSoundSettings(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(SharedPrefUtils.SOUND_SETTINGS_PREFS_NAME, 2);
		return prefs.getBoolean(SharedPrefUtils.SOUND_SETTINGS_PREFS_KEY, true);
	}

	public static void setCountingScreenSoundSetiings(Context context, Boolean onOff) {
		SharedPreferences prefs = context.getSharedPreferences(SharedPrefUtils.SOUND_COUTING_SCREEN_SETTINGS_PREFS_NAME, 2);
		Editor edit = prefs.edit();
		edit.putBoolean(SharedPrefUtils.SOUND_COUTING_SCREEN__PREFS_KEY, onOff);
		edit.commit();
	}

	public static boolean getCountingScreenSoundSettings(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(SharedPrefUtils.SOUND_COUTING_SCREEN_SETTINGS_PREFS_NAME, 2);
		return prefs.getBoolean(SharedPrefUtils.SOUND_COUTING_SCREEN__PREFS_KEY, true);
	}
}
