package com.kids.fun2learn.utils;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import android.app.Activity;
import android.util.DisplayMetrics;

public class EngineUtilits {

	/**
	 * method to load engine
	 * 
	 * @param activity
	 * @return engine
	 */

	public static EngineOptions onLoadEngine(Activity activity) {

		CommonUtils.setFullScreenMode(activity);

		final Camera camera = new Camera(0, 0, CommonConstants.CAMERA_WIDTH,
				CommonConstants.CAMERA_HEIGHT);

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int mWidth = dm.widthPixels;
		int mHeight = dm.heightPixels;

		RatioResolutionPolicy ratioPolicy = new RatioResolutionPolicy(mWidth,
				mHeight);
		
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
				ratioPolicy, camera);

		engineOptions.getAudioOptions().setNeedsSound(true);
        engineOptions.getAudioOptions().setNeedsMusic(true);

		return engineOptions;
		// DisplayMetrics dM = new DisplayMetrics();
		// activity.getWindowManager().getDefaultDisplay().getMetrics(dM);
		// int ScreenWidth = dM.widthPixels;
		// int ScreenHeight = dM.heightPixels;
		//
		//
		// return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
		// new RatioResolutionPolicy(CommonConstants.CAMERA_WIDTH,
		// CommonConstants.CAMERA_HEIGHT), camera);
		//

	}

	// private void getDeviceResolution() {
	// DisplayMetrics dM = new DisplayMetrics();
	// this.getWindowManager().getDefaultDisplay().getMetrics(dM);
	// this.ScreenWidth = dM.widthPixels;
	// this.ScreenHeight = dM.heightPixels;
	// this.resolutionRatio = this.ScreenWidth/this.ScreenHeight;
	//
	// resolutionRatio = (double)Math.round(resolutionRatio * 100) / 100;
	//
	// Log.d("Resolution","ScrennWidth: "+this.ScreenWidth );
	// Log.d("Resolution","ScrennHeight: "+this.ScreenHeight );
	// Log.d("Resolution","Resolution Ratio: " + this.resolutionRatio );
	// }

}
