package com.kids.fun2learn;

import java.io.File;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.entity.util.ScreenCapture.IScreenCaptureCallback;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.aicatopnir.AdController;
import com.appfireworks.android.listener.AppModuleListener;
import com.appfireworks.android.track.AppTracker;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.Libraries.CBLogging.Level;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.kids.fun2learn.adapter.ColorAndTexturePlateImageAdapter;
import com.kids.fun2learn.adapter.ColorAndTexturePlateImageAdapter.ColorAndTexturePlateCallBackListener;
import com.kids.fun2learn.adapter.PianoItemsAdapter;
import com.kids.fun2learn.adapter.PianoItemsAdapter.PianoItemsClickCallBackListener;
import com.kids.fun2learn.scene.AlphabetPaintingScene;
import com.kids.fun2learn.scene.AlphabetPuzzleScene;
import com.kids.fun2learn.scene.CountingNumberPaintingScene;
import com.kids.fun2learn.scene.InAppPurchaseScene;
import com.kids.fun2learn.scene.MainMenuScene;
import com.kids.fun2learn.utils.AdManagerUtils;
import com.kids.fun2learn.utils.CommonConstants;
import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.CustomAnimationUtils;
import com.kids.fun2learn.utils.EngineUtilits;
import com.kids.fun2learn.utils.RandomNumberGenerator;
import com.kids.fun2learn.utils.SharedPrefUtils;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;

public class AppsPartanBaseGameActivity extends SimpleBaseGameActivity implements PianoItemsClickCallBackListener,ColorAndTexturePlateCallBackListener {
	protected AlphabetPuzzleScene alphabetPuzzleScene;
	protected MainMenuScene mainMenuScene;
	protected CountingNumberPaintingScene countingNumberPaintingScene;
	protected AlphabetPaintingScene alphabetPaintingScene;
	protected InAppPurchaseScene inAppPurchaseScene;
	/** The interstitial ad. */
	protected InterstitialAd interstitialAd;
	private int gap = 10;

	public MainMenuScene getMainMenuScene() {
		return mainMenuScene;
	}

	public CountingNumberPaintingScene getCountingNumberPaintingScene() {
		return countingNumberPaintingScene;
	}

	public void setCountingNumberPaintingScene(CountingNumberPaintingScene countingNumberPaintingScene) {
		this.countingNumberPaintingScene = countingNumberPaintingScene;
	}

	public AlphabetPaintingScene getAlphabetPaintingScene() {
		return alphabetPaintingScene;
	}

	public void setAlphabetPaintingScene(AlphabetPaintingScene alphabetPaintingScene) {
		this.alphabetPaintingScene = alphabetPaintingScene;
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		return EngineUtilits.onLoadEngine(this);
	}

	@Override
	protected void onCreateResources() {}

	@Override
	protected Scene onCreateScene() {
		SceneManager.init(this);
		mainMenuScene = null;
		mainMenuScene = new MainMenuScene(this);
		alphabetPaintingScene = null;
		alphabetPaintingScene = new AlphabetPaintingScene(this);
		countingNumberPaintingScene = null;
		countingNumberPaintingScene = new CountingNumberPaintingScene(this);
		inAppPurchaseScene = null;
		inAppPurchaseScene = new InAppPurchaseScene(this);
		hideLoading();
		alphabetPuzzleScene = null;
		alphabetPuzzleScene = new AlphabetPuzzleScene(this);
		return alphabetPuzzleScene.getScene();
	}

	public LinearLayout eraser_popup_ll, btn_play_ll, game_parent_child_rl;
	private LinearLayout PyoanoItemContainerLL;
	public RelativeLayout game_parent_rl, top_ads_rl, bottom_ads_rl, loading_rl;
	private LinearLayout playBtns[] = new LinearLayout[6];
	private int playBtnsAnimCords[] = { -0, 5, 6, 7, 8, 9, 10, 11 };
	ImageView loading_rotating_circle_imgv, sound_on_imgv, sound_off_imgv;
	private View vv;
	private View ads_spacing_view;

	protected void onSetContentView() {
		SharedPrefUtils.setSoundSetiings(this, true);
		SharedPrefUtils.setCountingScreenSoundSetiings(this, true);
		final RelativeLayout relativeLayout = new RelativeLayout(this);
		final FrameLayout.LayoutParams relativeLayoutLayoutParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mRenderSurfaceView = new RenderSurfaceView(this);
		this.mRenderSurfaceView.setRenderer(this.mEngine, this);
		final LayoutParams surfaceViewLayoutParams = new RelativeLayout.LayoutParams(super.createSurfaceViewLayoutParams());
		((android.widget.RelativeLayout.LayoutParams) surfaceViewLayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT);
		// ADD MY NEW VIEW ABOVE
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		vv = vi.inflate(R.layout.activity_main, null);
		// THIS IS MY CUSTOM VIEW
		relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		// relativeLayout.setBackgroundColor(Color.RED);
		relativeLayout.addView(this.mRenderSurfaceView, surfaceViewLayoutParams); // ANDENGINE
		// VIEW
		relativeLayout.addView(vv, createAdViewLayoutParams()); // MYVIEW
		this.setContentView(relativeLayout, relativeLayoutLayoutParams);
		initializeUi(vv);
		ShowLoading();
		loadAdmobInterstitial();
		setSoundBtnsVisibilty();
		if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			showRandonBannerAds();
		}
	}

	private void loadAdmobBannerAds() {
		for (int i = 0; i < bottom_ads_rl.getChildCount(); i++) {
			bottom_ads_rl.removeViewAt(i);
		}
		bottom_ads_rl.invalidate();
		AdView admobAdView = new AdView(AppsPartanBaseGameActivity.this);
		admobAdView.setAdSize(AdSize.SMART_BANNER);
		admobAdView.setAdUnitId(getResources().getString(R.string.admod_banner_ad_id));
		AdRequest adRequest = new AdRequest.Builder().build();
		admobAdView.loadAd(adRequest);
		AdView.LayoutParams adViewParams = new AdView.LayoutParams(AdView.LayoutParams.WRAP_CONTENT, AdView.LayoutParams.WRAP_CONTENT);
		bottom_ads_rl.addView(admobAdView, adViewParams);
	}

	private StartAppAd startAppAd = new StartAppAd(this);

	private void loadStartAppBannerAds() {
		for (int i = 0; i < bottom_ads_rl.getChildCount(); i++) {
			bottom_ads_rl.removeViewAt(i);
		}
		bottom_ads_rl.invalidate();
		StartAppSDK.init(this, getResources().getString(R.string.startapp_developer_id), getResources().getString(R.string.startapp_app_id), true);
		/**
		 * Add banner programmatically (within Java code, instead of within the
		 * layout xml)
		 **/
		// Create new StartApp banner
		Banner startAppBanner = new Banner(this);
		RelativeLayout.LayoutParams bannerParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// Add the banner to the main layout
		bottom_ads_rl.addView(startAppBanner, bannerParameters);
	}

	// ===air push ads
	private void loadAirPushAds() {
		com.exwau.fxlje167198.AdView adView = (com.exwau.fxlje167198.AdView) vv.findViewById(R.id.airpush_adview);
		adView.setAdListener(adlistener);
	}

	com.exwau.fxlje167198.AdListener.BannerAdListener adlistener = new com.exwau.fxlje167198.AdListener.BannerAdListener() {
		@Override
		public void onAdClickListener() {
			System.out.println("Airpush onAdClickListener");
			// This will get called when ad is clicked.
		}

		@Override
		public void onAdLoadedListener() {
			System.out.println("Airpush onAdLoadedListener");
			// This will get called when an ad has loaded.
		}

		@Override
		public void onAdLoadingListener() {
			System.out.println("Airpush onAdLoadingListener");
			// This will get called when a rich media ad is loading.
		}

		@Override
		public void onAdExpandedListner() {
			System.out.println("Airpush onAdExpandedListner");
			// This will get called when an ad is showing on a user's screen.
			// This may cover the whole UI.
		}

		@Override
		public void onCloseListener() {
			System.out.println("Airpush onCloseListener");
			// This will get called when an ad is closing/resizing from an
			// expanded state.
		}

		@Override
		public void onErrorListener(String message) {
			System.out.println("Airpush onErrorListener" + message);
			// This will get called when any error has occurred. This will also
			// get called if the SDK notices any integration mistakes.
		}

		@Override
		public void noAdAvailableListener() {
			System.out.println("Airpush noAdAvailableListener");
			// this will get called when ad is not available
		}
	};

	protected void showHideAds() {
		if (SharedPrefUtils.getIsPurchased(this)) {
			// remove all ads of app
			ads_spacing_view.setVisibility(View.GONE);
			top_ads_rl.setVisibility(View.GONE);
			bottom_ads_rl.setVisibility(View.GONE);
			btn_play_ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.btn_play_bg_height_adsfree)));
		} else {
			ads_spacing_view.setVisibility(View.GONE);
		}
	}

	public void loadAdmobInterstitial() {
		interstitialAd = AdManagerUtils.loadInterstitial(this);
	}

	/** Called when the Show Interstitial button is clicked. */
	public void showAdmobInterstitialAds() {
		if (!SharedPrefUtils.getIsPurchased(this)) {
			AdRequest adRequest = new AdRequest.Builder().build();
			// Load the interstitial ad.
			interstitialAd.loadAd(adRequest);
			interstitialAd.show();
		}
	}

	com.google.android.gms.ads.AdView mAdView;

	private void initializeUi(View vv) {
		bottom_ads_rl = (RelativeLayout) vv.findViewById(R.id.bottom_ads_rl);
		ads_spacing_view = vv.findViewById(R.id.ads_spacing_view);
		sound_on_imgv = (ImageView) vv.findViewById(R.id.sound_on_imgv);
		sound_off_imgv = (ImageView) vv.findViewById(R.id.sound_off_imgv);
		loading_rotating_circle_imgv = (ImageView) vv.findViewById(R.id.loading_rotating_circle_imgv);
		loading_rl = (RelativeLayout) vv.findViewById(R.id.loading_rl);
		top_ads_rl = (RelativeLayout) vv.findViewById(R.id.top_ads_rl);
		bottom_ads_rl = (RelativeLayout) vv.findViewById(R.id.bottom_ads_rl);
		eraser_popup_ll = (LinearLayout) vv.findViewById(R.id.eraser_popup_ll);
		btn_play_ll = (LinearLayout) vv.findViewById(R.id.btn_play_ll);
		game_parent_child_rl = (LinearLayout) vv.findViewById(R.id.game_parent_child_rl);
		game_parent_rl = (RelativeLayout) vv.findViewById(R.id.game_parent_rl);
		initPlayBtns(vv);
		attachColorTexturePlate(vv);
	}

	public void ShowLoading() {
		loading_rl.setVisibility(View.VISIBLE);
		bottom_ads_rl.setVisibility(View.INVISIBLE);
		top_ads_rl.setVisibility(View.INVISIBLE);
		Animation rotation_circle = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_image);
		loading_rotating_circle_imgv.clearAnimation();
		loading_rotating_circle_imgv.startAnimation(rotation_circle);
	}

	public void hideLoading() {
		new DelaAsyncTask().execute();
	}

	class DelaAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			CustomAnimationUtils.removeLoadingAnimationEnd(loading_rl);
			onShowAplhabetPuzzleScene();
		}
	}

	public LinearLayout colorPlateLinearLayout, texture_PlateLinearLayout;
	HorizontalScrollView horizontalScrollView;
	private ColorAndTexturePlateImageAdapter colorPlateImageAdapter;
	private ColorAndTexturePlateImageAdapter texturePlateImageAdapter;

	private void attachColorTexturePlate(View vv) {
		colorPlateLinearLayout = (LinearLayout) vv.findViewById(R.id.color_ll);
		texture_PlateLinearLayout = (LinearLayout) vv.findViewById(R.id.texuture_ll);
		horizontalScrollView = (HorizontalScrollView) vv.findViewById(R.id.texuture_color_horizontalScrollView1);
		colorPlateImageAdapter = new ColorAndTexturePlateImageAdapter(this, CommonConstants.COLOR_PLATE);
		texturePlateImageAdapter = new ColorAndTexturePlateImageAdapter(this, CommonConstants.TEXTURE_PLATE);
		addViewsTOLayout(colorPlateImageAdapter, colorPlateLinearLayout);
		addViewsTOLayout(texturePlateImageAdapter, texture_PlateLinearLayout);
	}

	private void addViewsTOLayout(ColorAndTexturePlateImageAdapter colortexturePlateImageAdapter, LinearLayout plateLinearLayout) {
		plateLinearLayout.removeAllViews();
		plateLinearLayout.invalidate();
		for (int i = 0; i < colortexturePlateImageAdapter.getCount(); i++) {
			View view = colortexturePlateImageAdapter.getView(i, null, null);
			plateLinearLayout.addView(view);
		}
	}

	private void initPlayBtns(View vv) {
		// ======Animation Array for play btn==========
		LinearLayout playBtn = (LinearLayout) vv.findViewById(R.id.btn_play_ll1);
		LinearLayout playBtn1 = (LinearLayout) vv.findViewById(R.id.btn_play_ll2);
		LinearLayout playBtn2 = (LinearLayout) vv.findViewById(R.id.btn_play_ll4);
		LinearLayout playBtn3 = (LinearLayout) vv.findViewById(R.id.btn_play_ll3);
		LinearLayout playBtn4 = (LinearLayout) vv.findViewById(R.id.btn_play_ll5);
		LinearLayout playBtn6 = (LinearLayout) vv.findViewById(R.id.btn_play_ll6);
		playBtns[0] = playBtn;
		playBtns[1] = playBtn1;
		playBtns[2] = playBtn2;
		playBtns[3] = playBtn3;
		playBtns[4] = playBtn4;
		playBtns[5] = playBtn6;
	}

	public void onShowMainMenuScene() {
		CommonUtils.setVisibiltyGone(game_parent_rl);
		try {
			mainMenuScene.playBackgroundMusic();
			if (mainMenuScene.birdAnimatedSprite.isAnimationRunning()) {
				mainMenuScene.playBirdSound();
			}
		} catch (Exception exception) {}
		if (SharedPrefUtils.getIsPurchased(this)) {
			showHideAds();
		} else {
			top_ads_rl.setVisibility(View.INVISIBLE);
			bottom_ads_rl.setVisibility(View.VISIBLE);
		}
	}

	public void onShowAplhabetScene() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				CommonUtils.setVisibiltyOn(game_parent_rl);
				animatePlayBtn(playBtns, playBtnsAnimCords, RandomNumberGenerator.genarateRandomNumber(2));
				attachPianoButtons(CommonConstants.ALPHABETS_PIANO_ITEMS);
				setSoundBtnsVisibilty();
				getAlphabetPaintingScene().setScenePosition();
				if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
					showRandonBannerAds();
				}
			}
		});
	}

	public void onShowAplhabetPuzzleScene() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				top_ads_rl.setVisibility(View.VISIBLE);
				bottom_ads_rl.setVisibility(View.VISIBLE);
				CommonUtils.setVisibiltyOn(game_parent_rl);
				animatePlayBtn(playBtns, playBtnsAnimCords, RandomNumberGenerator.genarateRandomNumber(2));
				attachPianoButtons(CommonConstants.ALPHABETS_PIANO_ITEMS);
				setSoundBtnsVisibilty();
				getAlphabetPaintingScene().setScenePosition();
				if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
					showRandonBannerAds();
				}
			}
		});
	}

	private void showRandonBannerAds() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int random = RandomNumberGenerator.genarateRandomNumber(2);
				if (random == 0) {
					loadAdmobBannerAds();
				} else {
					loadStartAppBannerAds();
				}
				loadLeadboltIBannerAds();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			startAppAd.onBackPressed();
		}
		try {
			mainMenuScene.resetDoorSprite();
		} catch (Exception exception) {}
		if (mainMenuScene != null && mainMenuScene.getScene() != null && SceneManager.getScene() == mainMenuScene.getScene()) {
			mainMenuScene.stopBackgroundMusic();
			mainMenuScene.stopBirdSound();
			if (SharedPrefUtils.getIsPurchased(this)) {
				try {
					alphabetPaintingScene.unloadScene();
				} catch (Exception exception) {}
				try {
					countingNumberPaintingScene.unloadScene();
				} catch (Exception exception) {}
				finish();
			} else {
				mainMenuScene.getScene().setChildScene(inAppPurchaseScene.getScene(), false, true, true);
				SceneManager.setScene(mainMenuScene.getScene());
			}
		} else if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
			try {
				alphabetPaintingScene.stopBackgroundMusic();
			} catch (Exception exception) {}
			alphabetPaintingScene.handleScreenShotPost();
			onShowMainMenuScene();
			SceneManager.setScene(getMainMenuScene().getScene());
		} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
			try {
				countingNumberPaintingScene.stopBackgroundMusic();
			} catch (Exception exception) {}
			countingNumberPaintingScene.handleScreenShotPost();
			onShowMainMenuScene();
			SceneManager.setScene(getMainMenuScene().getScene());
		}
		showRandomInterstitial();
	}

	public void showRandomInterstitial() {
		if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					int random = RandomNumberGenerator.genarateRandomNumber(2);
					showChartBoostInterStitial();
				}
			});
		}
	}

	private void loadChartBoost() {
		Chartboost.startWithAppId(this, getResources().getString(R.string.chartboost_App_ID), getResources().getString(R.string.chartboost_App_Signature));
		Chartboost.setLoggingLevel(Level.ALL);
		Chartboost.onCreate(this);
	}

	private void showChartBoostInterStitial() {
		Chartboost.showInterstitial(CBLocation.LOCATION_LEADERBOARD);
	}

	private void showStartAppInterstitial() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				CommonUtils.showToast(AppsPartanBaseGameActivity.this, "loading nd showing showing");
			}
		});
		// StartAppSDK.init(this, "Your Developer Id", "Your App ID", true);
		StartAppSDK.init(this, getResources().getString(R.string.startapp_developer_id_interstitial), getResources().getString(R.string.startapp_app_id_interstitial), true);
		startAppAd.showAd(); // show the ad
		startAppAd.loadAd(); // load the next ad
	}

	private void loadLeadboltIBannerAds() {
		AppTracker.startSession(this, getResources().getString(R.string.leadbolt_API_Key), new AppModuleListener() {
			@Override
			public void onModuleFailed() {
				LB_BANNER_ADS = new AdController(act, getResources().getString(R.string.leadbolt_BannerAds_section_id));
				LB_BANNER_ADS.loadAd();
			}

			@Override
			public void onModuleLoaded() {}

			@Override
			public void onModuleClosed() {}

			@Override
			public void onModuleCached() {}
		});
	}

	private Activity act = this;
	private AdController interstitial;
	private AdController LB_BANNER_ADS;

	private void showLeadboltInterstitial() {
		AppTracker.startSession(this, getResources().getString(R.string.leadbolt_API_Key), new AppModuleListener() {
			@Override
			public void onModuleFailed() {
				interstitial = new AdController(act, getResources().getString(R.string.leadbolt_interstitial_section_id));
				interstitial.loadAd();
			}

			@Override
			public void onModuleLoaded() {}

			@Override
			public void onModuleClosed() {}

			@Override
			public void onModuleCached() {}
		});
	}

	public void onShowCountingNumberScene() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getCountingNumberPaintingScene().setScenePosition();
				CommonUtils.setVisibiltyOn(game_parent_rl);
				animatePlayBtn(playBtns, playBtnsAnimCords, RandomNumberGenerator.genarateRandomNumber(2));
				attachPianoButtons(CommonConstants.COUNTING_NUMBERS_PIANO_ITEMS);
				getCountingNumberPaintingScene().playBackgroundMusic();
				setSoundBtnsVisibilty();
				if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
					showRandonBannerAds();
				}
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			loadChartBoost();
			Chartboost.onStart(this);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			Chartboost.onStop(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			startAppAd.onPause();
			if (!isFinishing()) {
				AppTracker.pause(getApplicationContext());
			}
			Chartboost.onPause(this);
		}
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
		if (countingNumberPaintingScene != null) {
			if (SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
				countingNumberPaintingScene.stopBackgroundMusic();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!SharedPrefUtils.getIsPurchased(AppsPartanBaseGameActivity.this)) {
			startAppAd.onResume();
			AppTracker.resume(getApplicationContext());
			Chartboost.onResume(this);
		}
		if (alphabetPaintingScene != null) {
			if (SceneManager.getScene() == alphabetPaintingScene.getScene()) {
				alphabetPaintingScene.playBackgroundMusic();
			}
		}
		if (mainMenuScene != null) {
			if (SceneManager.getScene() == mainMenuScene.getScene()) {
				mainMenuScene.replayBirdSound();
				mainMenuScene.playBackgroundMusic();
			}
		}
		if (countingNumberPaintingScene != null) {
			if (SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
				countingNumberPaintingScene.playBackgroundMusic();
			}
		}
	}

	private void animatePlayBtn(View view[], int animTranscordToX[], int random) {
		// CustomAnimationUtils.CustomScaleAnimation(btn_play_ll);
		btn_play_ll.clearAnimation();
		if (btn_play_ll.getChildCount() > 0) {
			for (int i = 0; i < playBtns.length; i++) {
				btn_play_ll.removeView(playBtns[i]);
			}
		}
		for (int i = 0; i < playBtns.length; i++) {
			playBtns[i].setVisibility(View.INVISIBLE);
		}
		for (int i = 0; i < playBtns.length; i++) {
			btn_play_ll.addView(playBtns[i]);
		}
		CustomAnimationUtils customAnimationUtils = new CustomAnimationUtils(this, view, animTranscordToX, null, random);
		customAnimationUtils.startAnimationTimer(50, 100);
	}

	private LayoutParams createAdViewLayoutParams() {
		final LayoutParams adViewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		return adViewLayoutParams;
	}

	protected void attachPianoButtons(int[] PIANO_ITEMS) {
		PyoanoItemContainerLL = (LinearLayout) vv.findViewById(R.id.piano_btn_ll);
		try {
			PyoanoItemContainerLL.removeAllViews();
			PyoanoItemContainerLL.invalidate();
		} catch (Exception exception) {}
		PianoItemsAdapter pianoItemsAdapter = new PianoItemsAdapter(this, PIANO_ITEMS);
		View pianoItemItemsViews[] = new View[PIANO_ITEMS.length];
		for (int i = 0; i < PIANO_ITEMS.length; i++) {
			View view = pianoItemsAdapter.getView(i, null, null);
			pianoItemItemsViews[i] = view;
		}
		for (int i = 0; i < PIANO_ITEMS.length; i++) {
			PyoanoItemContainerLL.addView(pianoItemItemsViews[i]);
		}
	}

	@Override
	public void onPianoActionUp(int position) {
		CommonUtils.setVisibiltyGone(eraser_popup_ll);
		CommonUtils.setVisibiltyGone(colorPlateLinearLayout);
		CommonUtils.setVisibiltyGone(texture_PlateLinearLayout);
		if (alphabetPaintingScene != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
			alphabetPaintingScene.onPianoButtonActionUp(position);
		} else if (countingNumberPaintingScene != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
			countingNumberPaintingScene.onPianoButtonActionUp(position);
		} else if (alphabetPuzzleScene != null && SceneManager.getScene() == alphabetPuzzleScene.getScene()) {
			alphabetPuzzleScene.onPianoButtonActionUp(position);
		}
	}

	@Override
	public void onPianoActionDown() {
		if (alphabetPaintingScene != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
			alphabetPaintingScene.onPianoButtonActiondown();
		}
	}

	public void onClickBtn(View view) {
		switch (view.getId()) {
			case R.id.eraser_imgv:
				CommonUtils.setVisibiltyGone(texture_PlateLinearLayout);
				CommonUtils.setVisibiltyGone(colorPlateLinearLayout);
				CommonUtils.setVisibilty(eraser_popup_ll);
				break;
			case R.id.color_imgv:
				CommonUtils.setVisibiltyGone(eraser_popup_ll);
				CommonUtils.setVisibiltyGone(texture_PlateLinearLayout);
				CommonUtils.setVisibilty(colorPlateLinearLayout);
				break;
			case R.id.texture_imgv:
				CommonUtils.setVisibiltyGone(eraser_popup_ll);
				CommonUtils.setVisibiltyGone(colorPlateLinearLayout);
				CommonUtils.setVisibilty(texture_PlateLinearLayout);
				break;
			case R.id.eraser_left_imgv:
				// it erarese by touching
				if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
					alphabetPaintingScene.erasePainting();
				} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
					countingNumberPaintingScene.erasePainting();
				}
				CommonUtils.setVisibiltyGone(eraser_popup_ll);
				break;
			case R.id.eraser_right_imgv:
				// it erarese by one click go
				if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
					alphabetPaintingScene.eraseWholePainting();
				} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
					countingNumberPaintingScene.eraseWholePainting();
				}
				CommonUtils.setVisibiltyGone(eraser_popup_ll);
				break;
			case R.id.sound_on_imgv:
				CommonUtils.setVisibilty(sound_off_imgv);
				CommonUtils.setVisibiltyGone(sound_on_imgv);
				if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
					SharedPrefUtils.setSoundSetiings(this, false);
					alphabetPaintingScene.stopBackgroundMusic();
				} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
					SharedPrefUtils.setCountingScreenSoundSetiings(this, false);
					countingNumberPaintingScene.stopBackgroundMusic();
				}
				break;
			case R.id.sound_off_imgv:
				CommonUtils.setVisibilty(sound_on_imgv);
				CommonUtils.setVisibiltyGone(sound_off_imgv);
				SharedPrefUtils.setSoundSetiings(this, true);
				if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
					SharedPrefUtils.setSoundSetiings(this, true);
					alphabetPaintingScene.playBackgroundMusic();
				} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
					SharedPrefUtils.setCountingScreenSoundSetiings(this, true);
					countingNumberPaintingScene.playBackgroundMusic();
				}
				break;
			case R.id.save_imgv:
				if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
					alphabetPaintingScene.handleScreenShotPre();
					final String screenshot_from_andengine_imageName = "Fun2Learn_" + alphabetPaintingScene.getSelectedLetterName() + ".png";
					saveScreenShot(view, screenshot_from_andengine_imageName);
					alphabetPaintingScene.handleScreenShotPost();
				} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
					countingNumberPaintingScene.handleScreenShotPre();
					final String screenshot_from_andengine_imageName = "Fun2Learn_" + countingNumberPaintingScene.getSelectedLetterName() + ".png";
					saveScreenShot(view, screenshot_from_andengine_imageName);
					countingNumberPaintingScene.handleScreenShotPost();
				}
				break;
			case R.id.share_imgv:
				if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
					alphabetPaintingScene.handleScreenShotPre();
					final String screenshot_from_andengine_imageName = "Fun2Learn_" + alphabetPaintingScene.getSelectedLetterName() + ".png";
					String savedImagePath = saveScreenShot(view, screenshot_from_andengine_imageName);
					shareImage(savedImagePath);
					alphabetPaintingScene.handleScreenShotPost();
				} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
					countingNumberPaintingScene.handleScreenShotPre();
					final String screenshot_from_andengine_imageName = "Fun2Learn_" + countingNumberPaintingScene.getSelectedLetterName() + ".png";
					String savedImagePath = saveScreenShot(view, screenshot_from_andengine_imageName);
					shareImage(savedImagePath);
					countingNumberPaintingScene.handleScreenShotPost();
				}
				break;
		}
	}

	public void setSoundBtnsVisibilty() {
		if (SharedPrefUtils.getSoundSettings(this)) {
			sound_on_imgv.setVisibility(View.VISIBLE);
			sound_off_imgv.setVisibility(View.GONE);
		} else {
			sound_on_imgv.setVisibility(View.GONE);
			sound_off_imgv.setVisibility(View.VISIBLE);
		}
		if (SharedPrefUtils.getCountingScreenSoundSettings(this)) {
			sound_on_imgv.setVisibility(View.VISIBLE);
			sound_off_imgv.setVisibility(View.GONE);
		} else {
			sound_on_imgv.setVisibility(View.GONE);
			sound_off_imgv.setVisibility(View.VISIBLE);
		}
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

	private String saveScreenShot(View clickedView, final String screenshot_from_andengine_imageName) {
		// =============Screen shot from andengine===================
		String sreenshot_andengine_filePath = CommonUtils.findPath(AppsPartanBaseGameActivity.this) + screenshot_from_andengine_imageName;
		File imagesFolder = new File(CommonUtils.findPath(AppsPartanBaseGameActivity.this));
		if (!imagesFolder.exists())
			imagesFolder.mkdirs();
		int width = this.mRenderSurfaceView.getWidth();
		int height = this.mRenderSurfaceView.getHeight();
		ScreenCapture screenCapture = new ScreenCapture();
		if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
			alphabetPaintingScene.getScene().attachChild(screenCapture);
		} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
			countingNumberPaintingScene.getScene().attachChild(screenCapture);
		}
		screenCapture.capture(width, height, sreenshot_andengine_filePath, new IScreenCaptureCallback() {
			@Override
			public void onScreenCaptured(String pFilePath) {}

			@Override
			public void onScreenCaptureFailed(String pFilePath, Exception pException) {
			}
		});
		if (clickedView.getId() == R.id.save_imgv) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					CommonUtils.showToast(AppsPartanBaseGameActivity.this, "Image Saved successfully..." + CommonUtils.findPath(AppsPartanBaseGameActivity.this) + screenshot_from_andengine_imageName);
				}
			});
		}
		return CommonUtils.findPath(AppsPartanBaseGameActivity.this) + screenshot_from_andengine_imageName;
	}

	@Override
	public void onChooseColorTexture(int position, String color_texture_plate_type_name) {
		if (alphabetPaintingScene != null && alphabetPaintingScene.getScene() != null && SceneManager.getScene() == alphabetPaintingScene.getScene()) {
			alphabetPaintingScene.chooseColorTexture(position, color_texture_plate_type_name);
		} else if (countingNumberPaintingScene != null && countingNumberPaintingScene.getScene() != null && SceneManager.getScene() == countingNumberPaintingScene.getScene()) {
			countingNumberPaintingScene.chooseColorTexture(position, color_texture_plate_type_name);
			// countingNumberPaintingScene..eraseWholePainting();
		} else if (alphabetPuzzleScene != null && alphabetPuzzleScene.getScene() != null && SceneManager.getScene() == alphabetPuzzleScene.getScene()) {
			alphabetPuzzleScene.chooseColorTexture(position, color_texture_plate_type_name);
			// countingNumberPaintingScene..eraseWholePainting();
		}
	}

	// will be overeridden
	public void showInAppPurchaseDialog() {}

	// will be overeridden
	public void startInAppBillibg() {}

	// will be overeridden
	public void startRestoringTransaction() {}
	// ==============startapp ads integration===========
}
