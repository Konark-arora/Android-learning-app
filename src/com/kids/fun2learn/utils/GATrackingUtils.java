package com.kids.fun2learn.utils;

import android.app.Activity;
import android.content.Context;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GAServiceManager;

public class GATrackingUtils {

	public static void registerGA(Context ctx, String activityName) {

		EasyTracker.getInstance().activityStart((Activity) ctx);
		EasyTracker.getTracker().trackView(activityName);
		GAServiceManager.getInstance().dispatch();
	}

	public static void unRegisterGA(Context ctx) {

		EasyTracker.getInstance().activityStop((Activity) ctx);
	}

}
