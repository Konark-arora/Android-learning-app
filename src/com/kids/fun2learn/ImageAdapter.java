package com.kids.fun2learn;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	Integer[] pics = { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
			R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h

			, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
			R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p,
			R.drawable.q, R.drawable.r, R.drawable.s, R.drawable.t,
			R.drawable.u, R.drawable.v, R.drawable.w, R.drawable.x,
			R.drawable.y, R.drawable.z };

	private Context ctx;
	int imageBackground;
	int itemClicked;
	ImageView iv;
	Button button;

	public ImageAdapter(Context c) {
		ctx = c;
		// TypedArray ta = obtainStyledAttributes(R.styleable.Gallery1);
		// imageBackground = ta.getResourceId(
		// R.styleable.Gallery1_android_galleryItemBackground, 1);
		// ta.recycle();
	}

	@Override
	public int getCount() {

		return pics.length;
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {

		LayoutInflater vi = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vv = vi.inflate(R.layout.alphabet_item, null);
		Button button = (Button) vv.findViewById(R.id.alpbabetbutton);
		ImageView imageView = (ImageView) vv.findViewById(R.id.alpbabet_imgv);
		imageView.setImageResource(pics[position]);
		// button.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // Toast.makeText(ctx, position + " Clicked",
		// // Toast.LENGTH_SHORT)
		// // .show();
		//
		// }
		// });

		button.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub

				switch (arg1.getAction()) {

				case MotionEvent.ACTION_DOWN:

					((Activity) (ctx)).runOnUiThread(new Runnable() {

						@Override
						public void run() {

							((MainActivity) (ctx)).getAlphabetPaintingScene()
									.onAlphabetItemActionDown();
						}
					});

					break;

				case MotionEvent.ACTION_UP:

					((Activity) (ctx)).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							((MainActivity) (ctx)).getAlphabetPaintingScene()
									.onAlphabetItemActionUp(position);

//							((MainActivity) (ctx)).getAlphabetPaintingScene()
//									.playAlphabetSound(position);

						}
					});

					break;

				}
				return false;
			}
		});

		/*
		 * final LinearLayout linearLayout = new LinearLayout(ctx);
		 * linearLayout.setGravity(Gravity.CENTER);
		 * linearLayout.setOrientation(Orientation.V); iv = new ImageView(ctx);
		 * // iv.setTag(position); iv.setImageResource(pics[position]); //
		 * iv.setScaleType(ImageView.ScaleType.FIT_XY); //
		 * iv.setLayoutParams(new Gallery.LayoutParams(150, 120));
		 * iv.setBackgroundResource(R.drawable.alphabet_selector);
		 * iv.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Toast.makeText(ctx, position
		 * + " Clicked", Toast.LENGTH_SHORT) .show(); } });
		 * 
		 * button = new Button(ctx); linearLayout.addView(iv);
		 * linearLayout.addView(button);
		 */
		return vv;
	}
}
