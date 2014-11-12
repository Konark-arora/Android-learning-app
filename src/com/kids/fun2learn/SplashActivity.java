package com.kids.fun2learn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.kids.fun2learn.utils.CommonUtils;
import com.kids.fun2learn.utils.CustomAnimationUtils;
import com.kids.fun2learn.utils.RandomNumberGenerator;

public class SplashActivity extends Activity {

	ImageView imageView[] = new ImageView[6];

	ImageView mascotImageView;

	public static final int animTranscordToX[] = { 0, 5, 6, 7, 8, 9, 10 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		CommonUtils.removeActivityTitle(this);

		setContentView(R.layout.splash);

		// mascotImageView = (ImageView) findViewById(R.id.mascot);
		//
		// ImageView a = (ImageView) findViewById(R.id.aa);
		// ImageView p1 = (ImageView) findViewById(R.id.p1);
		// ImageView p2 = (ImageView) findViewById(R.id.p2);
		// ImageView s = (ImageView) findViewById(R.id.s);
		// ImageView o = (ImageView) findViewById(R.id.o);
		// ImageView n = (ImageView) findViewById(R.id.n);
		//
		// imageView[0] = a;
		// imageView[1] = p1;
		// imageView[2] = p2;
		// imageView[3] = s;
		// imageView[4] = o;
		// imageView[5] = n;
		//
		// CustomAnimationUtils customAnimationUtils = new
		// CustomAnimationUtils(SplashActivity.this,
		// imageView, animTranscordToX, mascotImageView,0);
		// customAnimationUtils.startAnimationTimer();

		ImageView mascotImageView = null;

		int animTranscordToX[] = { 0, 5, 6, 7, 8, 9, 10 };
		mascotImageView = (ImageView) findViewById(R.id.mascot);

		ImageView imageView[] = new ImageView[7];

		ImageView appspartan_0 = (ImageView) findViewById(R.id.appspartan_0);
		ImageView appspartan_1 = (ImageView) findViewById(R.id.appspartan_1);
		ImageView appspartan_2 = (ImageView) findViewById(R.id.appspartan_2);
		ImageView appspartan_3 = (ImageView) findViewById(R.id.appspartan_3);
		ImageView appspartan_4 = (ImageView) findViewById(R.id.appspartan_4);
		ImageView appspartan_5 = (ImageView) findViewById(R.id.appspartan_5);
		ImageView appspartan_6 = (ImageView) findViewById(R.id.appspartan_6);
		ImageView appspartan_7 = (ImageView) findViewById(R.id.appspartan_7);
		ImageView appspartan_8 = (ImageView) findViewById(R.id.appspartan_8);
		ImageView appspartan_9 = (ImageView) findViewById(R.id.appspartan_9);

		// imageView[0] = appspartan_0;
		// imageView[1] = appspartan_1;
		// imageView[2] = appspartan_2;
		// imageView[3] = appspartan_3;
		// imageView[4] = appspartan_4;
		// imageView[5] = appspartan_5;
		// imageView[6] = appspartan_6;
		// imageView[7] = appspartan_7;
		// imageView[8] = appspartan_8;
		// imageView[9] = appspartan_9;

		imageView[0] = appspartan_3;
		imageView[1] = appspartan_4;
		imageView[2] = appspartan_5;
		imageView[3] = appspartan_6;
		imageView[4] = appspartan_7;
		imageView[5] = appspartan_8;
		imageView[6] = appspartan_9;

		CustomAnimationUtils customAnimationUtils = new CustomAnimationUtils(
				SplashActivity.this, imageView, animTranscordToX,
				mascotImageView, 0);
		customAnimationUtils.startAnimationTimer(0, 70);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
	}

}
