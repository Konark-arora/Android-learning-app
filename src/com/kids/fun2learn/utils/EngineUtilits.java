package com.kids.fun2learn.utils;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import android.app.Activity;
import android.util.DisplayMetrics;

public class EngineUtilits {

	/**
	 * method to load engine
	 * 
	 * @param activity
	 * @return engine
	 */

	public static Engine onLoadEngine(Activity activity) {
		
		CommonUtils.removeActivityTitle(activity);
		
		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int mWidth = dm.widthPixels;
		int mHeight = dm.heightPixels;
		/*
		 * if (mWidth < 850) { mCamera = new Camera(0, 0, mWidth / dm.density,
		 * mHeight / dm.density); }
		 */
		Camera mCamera = new Camera(0, 0, CommonConstants.CAMERA_WIDTH,
				CommonConstants.CAMERA_HEIGHT);
		if (mWidth < 850) {
			RatioResolutionPolicy ratioPolicy = new RatioResolutionPolicy(
					mWidth, mHeight);
			final Engine engine = new Engine(new EngineOptions(true,
					ScreenOrientation.PORTRAIT, ratioPolicy, mCamera)
					.setNeedsMusic(true).setNeedsSound(true));

			return engine;
		} else {
			RatioResolutionPolicy ratioPolicy = new RatioResolutionPolicy(
					mWidth, mHeight);

			final Engine engine = new Engine(new EngineOptions(true,
					ScreenOrientation.PORTRAIT, ratioPolicy, mCamera)
					.setNeedsMusic(true).setNeedsSound(true));

			return engine;
		}

	}

}
