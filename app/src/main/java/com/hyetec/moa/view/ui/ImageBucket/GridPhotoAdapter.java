package com.hyetec.moa.view.ui.ImageBucket;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;

import java.util.ArrayList;
import java.util.List;


public class GridPhotoAdapter extends BaseAdapter {
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private Context mContext;
	private boolean isShow;

	public GridPhotoAdapter(Context context, List<ImageItem> dataList, boolean isShow) {
		this.mContext = context;
		this.mDataList = dataList;
		this.isShow = isShow;
	}

	public int getCount() {
		if (isShow) {
			// 多返回一个用于展示添加图标
			if (mDataList == null) {
				return 1;
			} else if (mDataList.size() == CustomConstants.MAX_IMAGE_SIZE) {
				return CustomConstants.MAX_IMAGE_SIZE;
			} else {
				return mDataList.size() + 1;
			}
		} else {
			return mDataList.size();
		}
	}

	public Object getItem(int position) {
		if (mDataList != null && mDataList.size() == CustomConstants.MAX_IMAGE_SIZE) {
			return mDataList.get(position);
		}

		else if (mDataList == null || position - 1 < 0 || position > mDataList.size()) {
			return null;
		} else {
			return mDataList.get(position - 1);
		}
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(int position, View convertView, ViewGroup parent) {
		H h;
		if (convertView == null) {
			h = new H();
			convertView = View.inflate(mContext, R.layout.item_photo, null);
			h.imageIv = (ImageView) convertView.findViewById(R.id.item_grid_image);
			convertView.setTag(h);
		} else {
			h = (H) convertView.getTag();
		}
		int size = mDataList.size();
		if (size == position) {
			if (size < CustomConstants.MAX_IMAGE_SIZE)
				h.imageIv.setImageResource(R.drawable.btn_add_pic);
		} else {

			final ImageItem item = mDataList.get(position);
			if (!item.isSelected) {
				Glide.with(mContext).load(Api.IMG_URL+item.getSourcePath()).into(h.imageIv);
			} else {
				Glide.with(mContext).load(item.getSourcePath()).into(h.imageIv);

			}

		}

		return convertView;
	}



	class H {
		ImageView imageIv;
	}

}
