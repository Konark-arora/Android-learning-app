package com.kids.fun2learn.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class CustomAnimationUtils {

	private Timer timer;

	private AppsOnTimerTask appsOnTimerTask;

	private volatile int counter, i;

	private View imageView[];

	private ImageView mascotImageView;

	private int animTranscordToX[];

	private Context context;

	private int randomNumberAnimation = 0;

	public CustomAnimationUtils(Context context, View imageView[],
			int animTranscordToX[], ImageView mascotImageView,
			int randomNumberAnimation) {

		this.context = context;

		this.imageView = imageView;

		this.mascotImageView = mascotImageView;

		this.animTranscordToX = animTranscordToX;
		this.counter = 0;

		this.i = -1;

		this.randomNumberAnimation = randomNumberAnimation;

	}

	public void startAnimationTimer(int startTime, int secondStartTime) {

		if (timer == null) {

			timer = new Timer();

			appsOnTimerTask = new AppsOnTimerTask();

			timer.schedule(appsOnTimerTask, startTime, secondStartTime);

		}

	}

	public void stopAnimationTimer() {

		if (timer != null) {

			timer.cancel();

			timer = null;
		}
	}

	public void startAnimation(final View imageView, float translateFromDeltaX,
			float translatetodeltaX, float rotateFromDegree,
			float rotateToDegree, final int i) {

		imageView.setVisibility(View.VISIBLE);

		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		set.setDuration(500);

		TranslateAnimation transX = new TranslateAnimation(translateFromDeltaX,
				translatetodeltaX, 0, 0);
		transX.setStartOffset(0);
		transX.setFillAfter(true);

		RotateAnimation an = new RotateAnimation(rotateFromDegree,
				rotateToDegree, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);

		an.setRepeatCount(0);
		an.setFillAfter(false);

		ScaleAnimation scaleAnimation = new ScaleAnimation(.0f, 1f, .0f, 1f,
				Animation.RELATIVE_TO_SELF, (float) 0.5,
				Animation.RELATIVE_TO_SELF, (float) 0.5);

		switch (randomNumberAnimation) {
		case 0:

			set.addAnimation(an);
			set.addAnimation(transX);
			set.addAnimation(scaleAnimation);
			// set.setInterpolator(new BounceInterpolator());
			break;

		case 1:
			set.addAnimation(scaleAnimation);
			// set.addAnimation(an);
			// set.addAnimation(transX);
			// set.addAnimation(scaleAnimation);
			// set.setInterpolator(new BounceInterpolator());

			break;

		/*
		 * case 2: // set.addAnimation(an); // set.addAnimation(transX);
		 * set.addAnimation(scaleAnimation); break;
		 */

		//
		// default:
		// set.addAnimation(an);
		// set.addAnimation(transX);
		// set.addAnimation(scaleAnimation);
		// // set.setInterpolator(new BounceInterpolator());
		// break;
		}

		imageView.startAnimation(set);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				// if (mascotImageView != null) {
				//
				// if (i < 3) {
				// givebouncingAnimation(imageView);
				//
				// }
				//
				// }
			}
		});

	}

	private void animateMascot() {

		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TranslateAnimation translation;
				translation = new TranslateAnimation(0f, 0F, 0f, 10);
				translation.setStartOffset(100);
				translation.setDuration(100);
				translation.setFillAfter(true);
				translation.setInterpolator(new BounceInterpolator());
				mascotImageView.startAnimation(translation);

				translation.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {

						// ((MainActivity) (context)).hideSplashScreen();
						CommonUtils.launchActivity(context,
								com.kids.fun2learn.MainActivity.class);
						((Activity) context).finish();

					}
				});

			}
		});
	}

	private void givebouncingAnimation(final View imageView) {

		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				TranslateAnimation translation;
				translation = new TranslateAnimation(0f, 0F, 0f,
						getDisplayHeight() / 2);
				translation.setStartOffset(500);
				translation.setDuration(2000);
				translation.setFillAfter(true);
				translation.setInterpolator(new BounceInterpolator());
				imageView.startAnimation(translation);

			}
		});
	}

	private int getDisplayHeight() {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		return metrics.widthPixels;
	}

	private class AppsOnTimerTask extends TimerTask {

		@Override
		public void run() {

			if (counter == 3) {

				i++;
				if (i < imageView.length) {

					System.out
							.println("hi counter------\t" + counter + "i" + i);

					((Activity) context).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							try {
								startAnimation(imageView[i], 500f,
										animTranscordToX[i], 360f, 0f, i);
							} catch (Exception exception) {
								exception.printStackTrace();
							}

						}
					});

				} else {
					stopAnimationTimer();

					if (mascotImageView != null) {
						animateMascot();
					}
				}

			} else if (counter > 3) {
				counter = 0;
				// stopAnimationTimer();

			}

			counter++;

		}

	}

	// ===================Scale Animation=============

	public static void CustomScaleAnimation(View view) {

		view.setVisibility(View.VISIBLE);

		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		set.setDuration(700);

		ScaleAnimation scaleAnimation = new ScaleAnimation(.0f, 1f, .0f, 1f,
				Animation.RELATIVE_TO_SELF, (float) 0.5,
				Animation.RELATIVE_TO_SELF, (float) 0.5);

		set.addAnimation(scaleAnimation);

		view.startAnimation(set);

	}

	public static void removeLoadingAnimationEnd(final View view) {

		view.setVisibility(View.VISIBLE);
		view.clearAnimation();

		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		set.setDuration(700);

		ScaleAnimation scaleAnimation = new ScaleAnimation(1f, .0f, 1f, .0f,
				Animation.RELATIVE_TO_SELF, (float) 0.5,
				Animation.RELATIVE_TO_SELF, (float) 0.5);

		set.addAnimation(scaleAnimation);

		
		view.startAnimation(set);
		
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				view.setVisibility(View.INVISIBLE);
			}
		});

		// AnimationSet set = new AnimationSet(true);
		// set.setFillAfter(true);
		// set.setDuration(500);
		//
		// TranslateAnimation transX = new TranslateAnimation(0,
		// 1, 0, 0);
		// transX.setStartOffset(0);
		// transX.setFillAfter(true);
		//
		// RotateAnimation an = new RotateAnimation(360,
		// 0, Animation.RELATIVE_TO_SELF, 0.5f,
		// Animation.RELATIVE_TO_SELF, 0.5f);
		//
		// an.setDuration(200);
		// an.setRepeatCount(0);
		// an.setFillAfter(false);
		//
		// ScaleAnimation scaleAnimation = new ScaleAnimation( 1f,.0f, 1f,.0f,
		// Animation.RELATIVE_TO_SELF, (float) 0.5,
		// Animation.RELATIVE_TO_SELF, (float) 0.5);
		//
		//
		//
		//
		// set.addAnimation(an);
		// set.addAnimation(transX);
		// set.addAnimation(scaleAnimation);

		// view.startAnimation(set);

	}

	public static void CustomScaleTranslateanimationEnd(View view) {

		TranslateAnimation transX = new TranslateAnimation(0, -100, 0, 0);
		transX.setStartOffset(0);
		transX.setFillAfter(true);

		view.startAnimation(transX);

	}

	// ===================Slide Animation==============================

	public static void slideAnimation(Context context, View view,
			final int ANIM_TYPE_ID) {

		Animation rightAnimation = AnimationUtils.loadAnimation(context,
				ANIM_TYPE_ID);

		rightAnimation.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation arg0) {
			}

			public void onAnimationRepeat(Animation arg0) {
			}

			public void onAnimationEnd(Animation arg0) {

			}
		});

		view.startAnimation(rightAnimation);
	}

}
