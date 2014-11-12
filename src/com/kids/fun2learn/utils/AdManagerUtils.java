package com.kids.fun2learn.utils;

import android.app.Activity;
import android.content.Context;

import com.chartboost.sdk.Chartboost;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class AdManagerUtils {

	public static void ShowAd(AdView adView) {

		// AdView mAdView = (AdView) getView().findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	/*******
	 * Chartboost ads setup***********************
	 * 
	 * @param context
	 * @param appId
	 * @param appSignature
	 */

	public static void createChartBoostAds(Context context, String appId,
			String appSignature) {

		Chartboost.startWithAppId((Activity) context, appId, appSignature);
		/*
		 * Optional: If you want to program responses to Chartboost events,
		 * supply a delegate object here and see step (10) for more information
		 */
		// Chartboost.setDelegate(delegate);
		Chartboost.onCreate((Activity) context);

	}

	public static void startChaBoostAds(Context context) {

		Chartboost.onStart((Activity) context);

	}

	public static void resumeCharBoostAds(Context context) {

		Chartboost.onResume((Activity) context);

	}

	public static void pauseChartBoostAds(Context context) {

		Chartboost.onPause((Activity) context);
	}

	public static void stopChartBoostAds(Context context) {

		Chartboost.onStop((Activity) context);
	}

	public static void destroyChartBoostAds(Context context) {

		Chartboost.onDestroy((Activity) context);
	}

	// public void onBackPressedChartBoost() {
	//
	// if (Chartboost.onBackPressed())
	// return;
	// else
	// super.onBackPressed();
	// }

	// =========================*******************

}
