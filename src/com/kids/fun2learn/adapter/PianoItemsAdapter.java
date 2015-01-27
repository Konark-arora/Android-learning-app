package com.kids.fun2learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.kids.fun2learn.R;

public class PianoItemsAdapter extends BaseAdapter {
	public interface PianoItemsClickCallBackListener {
		void onPianoActionDown();

		void onPianoActionUp(int position);
	}

	private int[] pianoItems;
	private Context context;
	private PianoItemsClickCallBackListener adapterCallBackListener;

	public PianoItemsAdapter(Context context, int pianoItems[]) {
		this.context = context;
		this.pianoItems = pianoItems;
		adapterCallBackListener = (PianoItemsClickCallBackListener) context;
	}

	@Override
	public int getCount() {
		return pianoItems.length;
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
		LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vv = vi.inflate(R.layout.piano_item, null);
		Button button = (Button) vv.findViewById(R.id.alpbabetbutton);
		ImageView imageView = (ImageView) vv.findViewById(R.id.alpbabet_imgv);
		imageView.setImageResource(pianoItems[position]);
		button.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
					case MotionEvent.ACTION_DOWN:
						adapterCallBackListener.onPianoActionDown();
						break;
					case MotionEvent.ACTION_UP:
						adapterCallBackListener.onPianoActionUp(position);
						break;
				}
				return false;
			}
		});
		return vv;
	}
}
