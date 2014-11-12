package com.kids.fun2learn;

import java.io.File;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.util.ScreenCapture;
import org.anddev.andengine.entity.util.ScreenCapture.IScreenCaptureCallback;
import org.anddev.andengine.opengl.view.RenderSurfaceView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kids.fun2learn.utils.AdManagerUtils;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.CustomAnimationUtils;
import com.kids.fun2learn.utils.GATrackingUtils;
import com.kids.fun2learn.utils.RandomNumberGenerator;
import com.kids.fun2learn.utils.SharedPrefUtils;

public class MainActivity extends AppsPartanBaseGameActivity implements
		SceneManagerListener {

	/** Your ad unit id. Replace with your actual ad unit id. */
	private static final String AD_UNIT_ID = "ca-app-pub-5876483923420061/1862350835";

	/** The interstitial ad. */
	private InterstitialAd interstitialAd;

	private boolean isFirstTimeREsume;

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		GATrackingUtils.registerGA(this, "MainActivity");
		// AdManagerUtils.startChaBoostAds(this);
	}

	/** Called when the Load Interstitial button is clicked. */
	public void loadInterstitial() {

		// Create an ad.
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID);

		// Check the logcat output for your hashed device ID to get test ads on
		// a physical device.
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE").build();

		// Load the interstitial ad.
		interstitialAd.loadAd(adRequest);
	}

	/** Called when the Show Interstitial button is clicked. */
	public void showInterstitial() {

		if (interstitialAd.isLoaded()) {
			interstitialAd.show();
		}
	}

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(pSavedInstanceState);

		isFirstTimeREsume = false;
		loadInterstitial();
		// AdManagerUtils.createChartBoostAds(this, "543ff6a31873da648bbfc6e4",
		// "d0210aa03cf7183eb1e02e5d1288d1d122492d52");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		GATrackingUtils.unRegisterGA(this);

		// AdManagerUtils.stopChartBoostAds(this);

	}

	@Override
	public Engine onLoadEngine() {

		return super.onLoadEngine();
	}

	@Override
	public void onLoadResources() {

		super.onLoadResources();

	}

	@Override
	public Scene onLoadScene() {

		return super.onLoadScene();
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		super.onLoadComplete();

		// hideLoading();

		// hideLoading();
		//
		// if (mainMenuScene != null)
		// mainMenuScene.playBackgroundMusic();
		// mittiBgSprite.setVelocityY(-300);

		new DelaAsyncTask().execute();
		//
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (alphabetPaintingScene != null) {

			if (SceneManager.getScene() == alphabetPaintingScene.getScene()) {

				alphabetPaintingScene.stopBackgroundMusic();

			}
		}

		if (mainMenuScene != null) {
			if (SceneManager.getScene() == mainMenuScene.getScene()) {
				mainMenuScene.pauseBirdSound();
				mainMenuScene.stopBackgroundMusic();

			}
		}

		isFirstTimeREsume = true;

		// AdManagerUtils.pauseChartBoostAds(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if (alphabetPaintingScene != null) {

			if (SceneManager.getScene() == alphabetPaintingScene.getScene()) {

				if (sound_on_imgv.getVisibility() == View.VISIBLE) {
					alphabetPaintingScene.playBackgroundMusic();
				}
			}
		}
		if (mainMenuScene != null) {
			if (SceneManager.getScene() == mainMenuScene.getScene()) {

				mainMenuScene.replayBirdSound();
				mainMenuScene.playBackgroundMusic();
			}
		}

		if (isFirstTimeREsume) {
			showInterstitial();
		}

		// AdManagerUtils.resumeCharBoostAds(this);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		isFirstTimeREsume = false;
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		if (SceneManager.getScene() == alphabetPaintingScene.getScene()) {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub

					alphabetPaintingScene.handleScreenShotPost();

					CommonUtils.setVisibiltyGone(ads_layout);
					// PyoanoItemContainerLL.setVisibility(View.INVISIBLE);
					//
					// btn_play_ll.clearAnimation();
					// btn_play_ll.setVisibility(View.INVISIBLE);
					//
					//

					alphabetPaintingScene.stopBackgroundMusic();
					gameLL.setVisibility(View.INVISIBLE);

					mainMenuScene.resetScene();
					// PyoanoItemContainerLL.

					SceneManager.setScene(mainMenuScene.getScene());

				}
			});

		} else if (SceneManager.getScene() == mainMenuScene.getScene()) {

			// if (SharedPrefUtils.getIsPurchased(getApplicationContext()) ==
			// false) {
			//
			// super.showInAppPurchaseDialog();
			// }

			// super.onBackPressed();

			// if (Chartboost.onBackPressed())
			// return;
			// else

			// Chartboost.showInterstitial("MainActivity Back");

			showInterstitial();
			super.onBackPressed();
		}

	}

	public void exitAndFinishGame() {

		if (mainMenuScene != null) {
			if (SceneManager.getScene() == mainMenuScene.getScene()) {
				mainMenuScene.stopBirdSound();
			}
		}

		if (mainMenuScene != null) {
			mainMenuScene.freemMenory();
		}
		if (alphabetPaintingScene != null) {
			alphabetPaintingScene.freeMemory();
		}

		MainMenuScene.unloadScene();

		AlphabetPaintingScene.unloadScene();

		this.finish();

	}

	protected void showHideAds() {

		if (SharedPrefUtils.getIsPurchased(getApplicationContext())) {

			CommonUtils.setVisibiltyGone(ads_layout);

		} else {

			CommonUtils.setVisibiltyOn(ads_layout);

		}
	}

	public LinearLayout eraser_popup_ll, btn_play_ll;
	private LinearLayout PyoanoItemContainerLL;
	LinearLayout gameLL;
	RelativeLayout ads_layout;

	private LinearLayout imageView[] = new LinearLayout[6];

	int animTranscordToX[] = { -0, 5, 6, 7, 8, 9, 10, 11 };

	View vv;

	RelativeLayout loading_rl, loading_rl_child;
	ImageView loading_rotating_circle_imgv, sound_on_imgv, sound_off_imgv;
	LinearLayout parent_LinearLayout;

	protected void onSetContentView() {
		// TODO Auto-generated method stub
		Display display = this.getWindowManager().getDefaultDisplay();
		final RelativeLayout relativeLayout = new RelativeLayout(this);
		final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.mRenderSurfaceView = new RenderSurfaceView(this);
		this.mRenderSurfaceView.setRenderer(this.mEngine);
		final LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(
				super.createSurfaceViewLayoutParams());
		((android.widget.RelativeLayout.LayoutParams) surfaceViewLayoutParams)
				.addRule(RelativeLayout.CENTER_IN_PARENT);
		// ADD MY NEW VIEW ABOVE
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vv = vi.inflate(R.layout.alphabets_container_layout, null);
		// THIS IS MY CUSTOM VIEW

		ads_layout = (RelativeLayout) vv.findViewById(R.id.ads_layout);

		AdView mAdView = (AdView) vv.findViewById(R.id.adView);

		AdManagerUtils.ShowAd(mAdView);
		CommonUtils.setVisibiltyGone(ads_layout);

		sound_on_imgv = (ImageView) vv.findViewById(R.id.sound_on_imgv);
		sound_off_imgv = (ImageView) vv.findViewById(R.id.sound_off_imgv);

		loading_rl = (RelativeLayout) vv.findViewById(R.id.loading_rl);

		loading_rl_child = (RelativeLayout) vv
				.findViewById(R.id.loading_rl_child);

		loading_rotating_circle_imgv = (ImageView) vv
				.findViewById(R.id.loading_rotating_circle_imgv);

		eraser_popup_ll = (LinearLayout) vv.findViewById(R.id.eraser_popup_ll);

		parent_LinearLayout = (LinearLayout) vv.findViewById(R.id.parent_ll);

		btn_play_ll = (LinearLayout) vv.findViewById(R.id.btn_play_ll);

		gameLL = (LinearLayout) vv.findViewById(R.id.game_ll);

		PyoanoItemContainerLL = (LinearLayout) vv
				.findViewById(R.id.alphabet_btn_ll);

		// CustomAnimationUtils.CustomScaleAnimation(PyoanoItemContainerLL);

		// ======Animation Array for play btn==========
		LinearLayout playBtn = (LinearLayout) vv
				.findViewById(R.id.btn_play_ll1);
		LinearLayout playBtn1 = (LinearLayout) vv
				.findViewById(R.id.btn_play_ll2);
		LinearLayout playBtn2 = (LinearLayout) vv
				.findViewById(R.id.btn_play_ll4);
		LinearLayout playBtn3 = (LinearLayout) vv
				.findViewById(R.id.btn_play_ll3);
		LinearLayout playBtn4 = (LinearLayout) vv
				.findViewById(R.id.btn_play_ll5);

		LinearLayout playBtn6 = (LinearLayout) vv
				.findViewById(R.id.btn_play_ll6);

		imageView[0] = playBtn;
		imageView[1] = playBtn1;
		imageView[2] = playBtn2;
		imageView[3] = playBtn3;
		imageView[4] = playBtn4;
		imageView[5] = playBtn6;

		ShowLoading();
		// animatePlayBtn(imageView, animTranscordToX,
		// RandomNumberGenerator.genarateRandomNumber(3));

		attachAlphabets(vv);

		attachColorTexturePlate(vv);

		// CommonUtils.getScreenResizeRatio(MainActivity.this);

		// ==================================

		relativeLayout.setLayoutParams(new LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		relativeLayout.setBackgroundColor(Color.RED);
		relativeLayout
				.addView(this.mRenderSurfaceView, surfaceViewLayoutParams); // ANDENGINE
		// VIEW
		relativeLayout.addView(vv, createAdViewLayoutParams()); // MYVIEW

		this.setContentView(relativeLayout, relativeLayoutLayoutParams);

	}

	public void ShowLoading() {

		Animation rotation_circle = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.rotate_image);

		loading_rotating_circle_imgv.clearAnimation();

		loading_rotating_circle_imgv.startAnimation(rotation_circle);

		// CommonUtils.setVisibiltyOn(loading_rl);

		// CustomAnimationUtils.CustomScaleAnimation(loading_rl_child);

	}

	public void hideLoading() {

		// CustomAnimationUtils.CustomScaleAnimationEnd(loading_rl);

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				CustomAnimationUtils.removeLoadingAnimationEnd(loading_rl);

			}
		});

		// CommonUtils.setVisibil(loading_rl);

		// loading_rl.setVisibility(View.INVISIBLE);

	}

	public LinearLayout colorPlateLinearLayout, texture_PlateLinearLayout;
	HorizontalScrollView horizontalScrollView;

	private ColorAndTexturePlateImageAdapter colorPlateImageAdapter;
	private ColorAndTexturePlateImageAdapter texturePlateImageAdapter;

	private void attachColorTexturePlate(View vv) {

		colorPlateLinearLayout = (LinearLayout) vv.findViewById(R.id.color_ll);

		texture_PlateLinearLayout = (LinearLayout) vv
				.findViewById(R.id.texuture_ll);

		horizontalScrollView = (HorizontalScrollView) vv
				.findViewById(R.id.texuture_color_horizontalScrollView1);

		colorPlateImageAdapter = new ColorAndTexturePlateImageAdapter(this,
				CommonConstants.COLOR_PLATE);

		texturePlateImageAdapter = new ColorAndTexturePlateImageAdapter(this,
				CommonConstants.TEXTURE_PLATE);

		addViewsTOLayout(colorPlateImageAdapter, colorPlateLinearLayout);

		addViewsTOLayout(texturePlateImageAdapter, texture_PlateLinearLayout);

	}

	private void addViewsTOLayout(
			ColorAndTexturePlateImageAdapter colortexturePlateImageAdapter,
			LinearLayout plateLinearLayout) {

		plateLinearLayout.removeAllViews();

		View viw2[] = new View[16];

		int animTranscordToX2[] = { 0, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
				16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };

		for (int i = 0; i < colortexturePlateImageAdapter.getCount(); i++) {

			View view = colortexturePlateImageAdapter.getView(i, null, null);

			viw2[i] = view;

			plateLinearLayout.addView(view);

		}

	}

	int animAplphabetItemsTranscordToX1[] = { 0, 5, 6, 7, 8, 9, 10, 11, 12, 13,
			14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30 };

	View alphabetsItemsViews[];

	private void attachAlphabets(View vv) {

		ImageAdapter adapter = new ImageAdapter(this);

		alphabetsItemsViews = new View[26];

		for (int i = 0; i < adapter.getCount(); i++) {
			View view = adapter.getView(i, null, null);

			alphabetsItemsViews[i] = view;
		}

	}

	CustomAnimationUtils customAnimationUtils;

	private void animatePlayBtn(View view[], int animTranscordToX[], int random) {

		customAnimationUtils = new CustomAnimationUtils(MainActivity.this,
				view, animTranscordToX, null, random);

		customAnimationUtils.startAnimationTimer(50, 100);

	}

	public void showAplhabetScene() {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				showHideAds();

				if (customAnimationUtils != null)
					customAnimationUtils.stopAnimationTimer();

				gameLL.setVisibility(View.VISIBLE);

				for (int i = 0; i < alphabetsItemsViews.length; i++) {

					alphabetsItemsViews[i].clearAnimation();
					alphabetsItemsViews[i].setVisibility(View.INVISIBLE);
				}

				PyoanoItemContainerLL.removeAllViews();
				PyoanoItemContainerLL.invalidate();

				// for (int i = 0; i < alphabetsItemsViews.length; i++) {
				// alphabetsItemsViews[i].clearAnimation();
				// alphabetsItemsViews[i].setVisibility(View.INVISIBLE);
				// }
				for (int i = 0; i < alphabetsItemsViews.length; i++) {
					PyoanoItemContainerLL.addView(alphabetsItemsViews[i]);

				}

				// attachAlphabets(vv);

				// for (int i = 0; i < alphabetsItemsViews.length; i++) {
				//
				// alphabetsItemsViews[i].clearAnimation();
				// alphabetsItemsViews[i].setVisibility(View.INVISIBLE);}
				//

				// PyoanoItemContainerLL.addView(alphabetsItemsViews[i]);
				//
				// alphabetsItemsViews[i].setVisibility(View.VISIBLE);
				//
				// }

				// PyoanoItemContainerLL.invalidate();
				// R
				PyoanoItemContainerLL.setVisibility(View.VISIBLE);
				//
				animatePlayBtn(alphabetsItemsViews,
						animAplphabetItemsTranscordToX1, 1);

				// ===================================================
				btn_play_ll.clearAnimation();

				if (btn_play_ll.getChildCount() > 0) {
					for (int i = 0; i < imageView.length; i++) {
						btn_play_ll.removeView(imageView[i]);

					}
				}

				for (int i = 0; i < imageView.length; i++) {

					imageView[i].setVisibility(View.INVISIBLE);
				}

				for (int i = 0; i < imageView.length; i++) {
					btn_play_ll.addView(imageView[i]);

				}

				CustomAnimationUtils.CustomScaleAnimation(btn_play_ll);

				animatePlayBtn(imageView, animTranscordToX,
						RandomNumberGenerator.genarateRandomNumber(2));

			}
		});
	}

	/**
	 * Called from xml when eraser btn is clicked
	 * 
	 * @param view
	 */

	public void onClickBtn(View view) {
		if (view.getId() == R.id.eraser_imgv) {

			CommonUtils.setVisibiltyGone(texture_PlateLinearLayout);

			CommonUtils.setVisibiltyGone(colorPlateLinearLayout);

			CommonUtils.setVisibilty(eraser_popup_ll);

		} else if (view.getId() == R.id.color_imgv) {

			// addViewsTOLayout(colorPlateImageAdapter, colorPlateLinearLayout);

			CommonUtils.setVisibiltyGone(eraser_popup_ll);

			CommonUtils.setVisibiltyGone(texture_PlateLinearLayout);

			CommonUtils.setVisibilty(colorPlateLinearLayout);

			// animate(colorPlateLinearLayout);
		}

		else if (view.getId() == R.id.texture_imgv) {

			// addViewsTOLayout(texturePlateImageAdapter,
			// texture_PlateLinearLayout);

			CommonUtils.setVisibiltyGone(eraser_popup_ll);
			CommonUtils.setVisibiltyGone(colorPlateLinearLayout);

			CommonUtils.setVisibilty(texture_PlateLinearLayout);

			// animate(texture_PlateLinearLayout);

		}

		else if (view.getId() == R.id.eraser_left_imgv) {

			// it erarese by touching
			alphabetPaintingScene.erasePainting();

			CommonUtils.setVisibiltyGone(eraser_popup_ll);

		} else if (view.getId() == R.id.eraser_right_imgv) {

			// it erarese by touching
			alphabetPaintingScene.eraseWholePainting();

			CommonUtils.setVisibiltyGone(eraser_popup_ll);
		} else if (view.getId() == R.id.sound_on_imgv) {

			CommonUtils.setVisibilty(sound_off_imgv);
			CommonUtils.setVisibiltyGone(sound_on_imgv);

			alphabetPaintingScene.stopBackgroundMusic();

		} else if (view.getId() == R.id.sound_off_imgv) {

			CommonUtils.setVisibilty(sound_on_imgv);
			CommonUtils.setVisibiltyGone(sound_off_imgv);

			alphabetPaintingScene.playBackgroundMusic();
		}

		else if (view.getId() == R.id.save_imgv) {

			alphabetPaintingScene.handleScreenShotPre();
			saveScreenShot(view);

			// runOnUiThread(new Runnable() {
			//
			// @Override
			// public void run() {
			//
			// alphabetPaintingScene.handleScreenShotPost();
			//
			// }
			// });

		} else if (view.getId() == R.id.share_imgv) {

			alphabetPaintingScene.handleScreenShotPre();

			// alphabetPaintingScene.handleScreenShotPre();
			String savedImagePath = saveScreenShot(view);
			// runOnUiThread(new Runnable() {
			//
			// @Override
			// public void run() {
			//
			// alphabetPaintingScene.handleScreenShotPost();
			//
			// }
			// });

			shareImage(savedImagePath);

		}

	}

	private String saveScreenShot(View clickedView) {

		// =============Screen shot from andengine===================

		final String screenshot_from_andengine_imageName = "Fun2Learn_"
				+ alphabetPaintingScene.getSelectedLetterName() + ".png";

		String sreenshot_andengine_filePath = CommonUtils
				.findPath(MainActivity.this)
				+ screenshot_from_andengine_imageName;

		File imagesFolder = new File(CommonUtils.findPath(MainActivity.this));

		if (!imagesFolder.exists())
			imagesFolder.mkdirs();

		int width = this.mRenderSurfaceView.getWidth();
		int height = this.mRenderSurfaceView.getHeight();

		ScreenCapture screenCapture = new ScreenCapture();

		AlphabetPaintingScene.scene.getTopLayer().addEntity(screenCapture);

		screenCapture.capture(width, height, sreenshot_andengine_filePath,
				new IScreenCaptureCallback() {

					@Override
					public void onScreenCaptured(String pFilePath) {

					}

				});

		if (clickedView.getId() == R.id.save_imgv) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					CommonUtils.showToast(
							MainActivity.this,
							"Image Saved successfully..."
									+ CommonUtils.findPath(MainActivity.this)
									+ screenshot_from_andengine_imageName);

				}
			});
		}

		return CommonUtils.findPath(MainActivity.this)
				+ screenshot_from_andengine_imageName;

	}

	private void shareImage(String shareImagePath) {

		Uri uriShareImage = Uri.parse(shareImagePath);

		Intent share = new Intent(Intent.ACTION_SEND);

		// If you want to share a png image only, you can do:
		// setType("image/png"); OR for jpeg: setType("image/jpeg");
		share.setType("image/*");
		share.putExtra(Intent.EXTRA_STREAM, uriShareImage);

		startActivity(Intent.createChooser(share, "Share Image!"));

	}

	private LayoutParams createAdViewLayoutParams() {
		final LayoutParams adViewLayoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
		return adViewLayoutParams;
	}

	@Override
	public void showlphabetPaitingView() {

		// showAlphabetPaintLL();
	}

	class DelaAsyncTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			hideLoading();

			if (mainMenuScene != null)
				mainMenuScene.playBackgroundMusic();
			// mittiBgSprite.setVelocityY(-300);

		}
	}

}
