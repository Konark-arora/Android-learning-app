package com.kids.fun2learn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonUtils {

	public static void removeActivityTitle(Activity activity) {

		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	/**
	 * Check if external storage is available
	 * 
	 * @return
	 */

	public static boolean isSdCardPresent() {

		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	public static String findPath(Context context) {

		String _path = null;

		if (isSdCardPresent()) {

			_path = Environment.getExternalStorageDirectory().toString()
					+ "/Fun2Learn/";

		}

		else {

			_path = context.getFilesDir().getAbsolutePath().toString()
					+ "/Fun2Learn/";

		}

		return _path;
	}

	/**
	 * 
	 * @param context
	 * @param view
	 *            capture it may be any Layout  * @return  
	 * @return
	 */

	public static Bitmap takeScreenshot(Context context, View view) {

		Bitmap screenshot = null;

		// view.setDrawingCacheEnabled(true);
		//
		// screenshot = Bitmap.createBitmap(view.getDrawingCache());
		// view.setDrawingCacheEnabled(true);

		try {
			if (view != null) {

				int width = view.getWidth();
				int height = view.getHeight();

				screenshot = Bitmap.createBitmap(width, height,
						Bitmap.Config.RGB_565);

				Canvas canvas = new Canvas(screenshot);

				view.draw(canvas);

			}
		} catch (Exception exception) {

			CommonUtils.showToast(context, "captureScreen Failed");

		}
		return screenshot;

	}

	public static void showToast(Context context, String Msg) {

		Toast toast = Toast.makeText(context, Msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

	}

	public static void launchActivity(Context context, Class<?> activityClass) {
		/*
		 * Bundle bundle = new Bundle();
		 */

		Intent intent = new Intent(context, activityClass);
		/*
		 * intent.putExtra("aaa", 10);
		 */
		context.startActivity(intent);
	}

	/**
	 * 
	 * Make the passed view visible invisible
	 * 
	 * @param view
	 */
	public static void setVisibilty(View view) {

		if (view.getVisibility() == View.GONE) {

			view.setVisibility(View.VISIBLE);

		} else if (view.getVisibility() == View.VISIBLE) {

			view.setVisibility(View.GONE);

		}
	}

	/**
	 * It makes the passed view always visible
	 * 
	 * @param view
	 */

	public static void setVisibiltyOn(View view) {

		view.setVisibility(View.VISIBLE);
	}

	/**
	 * It makes the passed view always Invisible
	 * 
	 * @param view
	 */

	public static void setVisibiltyGone(View view) {

		view.setVisibility(View.GONE);
	}

	public static int getScreenResizeRatio(Context context) {
		long screenWidth = context.getResources().getDisplayMetrics().widthPixels;

		DisplayMetrics displayMetrics = new DisplayMetrics();
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getMetrics(displayMetrics);
		float ratio = 0.95f;

		switch (displayMetrics.densityDpi) {

		case DisplayMetrics.DENSITY_LOW:

			ratio = 1.0f;
			break;

		case DisplayMetrics.DENSITY_MEDIUM:

			Toast.makeText(context, "DENSITY_MEDIUM", Toast.LENGTH_LONG).show();
			break;

		case DisplayMetrics.DENSITY_HIGH:
			Toast.makeText(context, "DENSITY_HIGH", Toast.LENGTH_LONG).show();
			ratio = 0.60f;
			break;

		case DisplayMetrics.DENSITY_XHIGH:
			Toast.makeText(context, "DENSITY_XHIGH", Toast.LENGTH_LONG).show();
			ratio = 0.45f;
			break;

		case DisplayMetrics.DENSITY_XXHIGH:
			Toast.makeText(context, "DENSITY_XXHIGH", Toast.LENGTH_LONG).show();
			ratio = 0.45f;
			break;

		default:
			break;
		}
		return Math.round(screenWidth * ratio);
	}

}
