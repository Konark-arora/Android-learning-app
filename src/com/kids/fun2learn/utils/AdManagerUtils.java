package com.kids.fun2learn.utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kids.fun2learn.R;

public class AdManagerUtils {

	// ============*********admob**********===============
	public static void ShowAd(AdView adView) {

		// AdView mAdView = (AdView) getView().findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	public static InterstitialAd loadInterstitial(Activity context) {

		// // Create an ad.
		InterstitialAd interstitialAd = new InterstitialAd(context);
		interstitialAd.setAdUnitId(context.getResources().getString(
				R.string.admod_interstitial_ad_id));

		// AdRequest adRequest = new AdRequest.Builder().build();
		// // Load the interstitial ad.
		// interstitialAd.loadAd(adRequest);

		return interstitialAd;
	}

	// ===============******************

}
