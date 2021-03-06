package com.kids.fun2learn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.kids.fun2learn.R;
import com.kids.fun2learn.utils.CommonConstants;

public class ColorAndTexturePlateImageAdapter extends BaseAdapter {
	public interface ColorAndTexturePlateCallBackListener {
		void onChooseColorTexture(int position, String color_texture_plate_type_name);
	}

	Integer[] color_plate = { R.drawable._selector_color_plate_0, R.drawable._selector_color_plate_1, R.drawable._selector_color_plate_2, R.drawable._selector_color_plate_3, R.drawable._selector_color_plate_4, R.drawable._selector_color_plate_5, R.drawable._selector_color_plate_6,
			R.drawable._selector_color_plate_7, R.drawable._selector_color_plate_8, R.drawable._selector_color_plate_9, R.drawable._selector_color_plate_10, R.drawable._selector_color_plate_11, R.drawable._selector_color_plate_12, R.drawable._selector_color_plate_13,
			R.drawable._selector_color_plate_14, R.drawable._selector_color_plate_15 };
	Integer[] texture_plate = { R.drawable._selector_texture_plate_0, R.drawable._selector_texture_plate_1, R.drawable._selector_texture_plate_2, R.drawable._selector_texture_plate_3, R.drawable._selector_texture_plate_4, R.drawable._selector_texture_plate_5, R.drawable._selector_texture_plate_6,
			R.drawable._selector_texture_plate_7, R.drawable._selector_texture_plate_8, R.drawable._selector_texture_plate_9, R.drawable._selector_texture_plate_10, R.drawable._selector_texture_plate_11, R.drawable._selector_texture_plate_12, R.drawable._selector_texture_plate_13,
			R.drawable._selector_texture_plate_14, R.drawable._selector_texture_plate_15 };
	private Context ctx;
	private int imageBackground;
	private int itemClicked;
	private ImageView iv;
	private Button button;
	private String color_texture_plate;
	private ColorAndTexturePlateCallBackListener colorAndTexturePlateCallBackListener;

	public ColorAndTexturePlateImageAdapter(Context c, String color_texture_plate) {
		ctx = c;
		this.color_texture_plate = color_texture_plate;
		this.colorAndTexturePlateCallBackListener = (ColorAndTexturePlateCallBackListener) ctx;
	}

	@Override
	public int getCount() {
		return color_plate.length;
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
		LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vv = vi.inflate(R.layout.color_texture_plate_item, null);
		ImageView imageView = (ImageView) vv.findViewById(R.id.color_texture_plate_imgv);
		if (color_texture_plate.equalsIgnoreCase(CommonConstants.COLOR_PLATE)) {
			imageView.setImageResource(color_plate[position]);
		} else if (color_texture_plate.equalsIgnoreCase(CommonConstants.TEXTURE_PLATE)) {
			imageView.setImageResource(texture_plate[position]);
		}
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				colorAndTexturePlateCallBackListener.onChooseColorTexture(position, color_texture_plate);
			}
		});
		return vv;
	}
}
