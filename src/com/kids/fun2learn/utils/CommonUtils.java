package com.kids.fun2learn.utils;

import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.BlendFunctionParticleInitializer;
import org.andengine.entity.particle.initializer.ColorParticleInitializer;
import org.andengine.entity.particle.initializer.RotationParticleInitializer;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.ScaleParticleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.appspartan.custom.CustomParticle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonUtils {
	public static void setFullScreenMode(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public static boolean isTablet(Context context) {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}

	/**
	 * Check if external storage is available
	 * 
	 * @return
	 */
	public static boolean isSdCardPresent() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}

	public static String findPath(Context context) {
		String _path = null;
		if (isSdCardPresent()) {
			_path = Environment.getExternalStorageDirectory().toString() + "/Fun2Learn/";
		} else {
			_path = context.getFilesDir().getAbsolutePath().toString() + "/Fun2Learn/";
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
		try {
			if (view != null) {
				int width = view.getWidth();
				int height = view.getHeight();
				screenshot = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
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
		((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
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

	public static ITextureRegion createParticleTextureRegion(Context context) {
		ITextureRegion particleTextureRegion = null;
		try {
			ITexture particleTexture = new AssetBitmapTexture(((SimpleBaseGameActivity) context).getTextureManager(), context.getAssets(), "gfx/common/particle.png");
			particleTextureRegion = TextureRegionFactory.extractFromTexture(particleTexture);
			particleTexture.load();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return particleTextureRegion;
	}

	public static void createParticalSystem(Context context, float x, float y, int parNo1, ITextureRegion particleTextureRegion, Scene scene) {
		final PointParticleEmitter particleEmitter = new PointParticleEmitter(x, y);
		final CustomParticle particleSystem = new CustomParticle(context, particleEmitter, 250, 250, 250, particleTextureRegion, ((SimpleBaseGameActivity) context).getVertexBufferObjectManager());
		particleSystem.addParticleInitializer(new BlendFunctionParticleInitializer<Sprite>(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE));
		particleSystem.addParticleInitializer(new ColorParticleInitializer<Sprite>(1, 1, 1));
		particleSystem.addParticleInitializer(new AlphaParticleModifier<Sprite>(2.5f, 3.5f, 1.0f, 0.0f));
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Sprite>(-250, 250, 250, -250));
		particleSystem.addParticleInitializer(new RotationParticleInitializer<Sprite>(150.0f, 360.0f));
		particleSystem.addParticleModifier(new AlphaParticleModifier<Sprite>(1, 1, 1, 1));
		particleSystem.addParticleModifier(new ScaleParticleModifier<Sprite>(0, 5, 0.5f, 2.0f));
		scene.attachChild(particleSystem);
	}
}
