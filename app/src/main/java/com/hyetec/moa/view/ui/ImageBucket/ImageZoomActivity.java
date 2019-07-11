package com.hyetec.moa.view.ui.ImageBucket;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.view.activity.CompanyActivity;


import java.util.ArrayList;
import java.util.List;

public class ImageZoomActivity extends Activity {

	private ViewPager pager;
	private MyPageAdapter adapter;
	private int currentPosition;
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();

	private RelativeLayout photo_relativeLayout;
	private String photo = "";
	private String strDel="";
	private Button photo_bt_del;
	private Button photo_bt_exit;
	private int isDel = 0X00008;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_zoom);

		photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
		photo_relativeLayout.setBackgroundColor(0x70000000);
		photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
		photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);

        strDel = getIntent().getStringExtra("idDel");
        if (strDel.equals("del")) {
            photo_bt_del.setVisibility(View.VISIBLE);
        }else {
            photo_bt_del.setVisibility(View.GONE);
        }
		initData();

		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		photo_bt_del.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mDataList.size() == 1) {
					removeImgs();
					finish();
				} else {
					removeImg(currentPosition);
					pager.removeAllViews();
					adapter.removeView(currentPosition);
					adapter.notifyDataSetChanged();
				}
				Toast.makeText(ImageZoomActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
			}
		});

		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);

		adapter = new MyPageAdapter(mDataList);

		pager.setAdapter(adapter);
		pager.setCurrentItem(currentPosition);
	}

	private void initData() {
		currentPosition = getIntent().getIntExtra(CustomConstants.POSITION, 0);
		photo = getIntent().getStringExtra(CustomConstants.STR_ACTIVITY);
		mDataList = CompanyActivity.photoList;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void removeImgs() {
		if (!mDataList.get(0).isSelected) {
			delIv(mDataList.get(0).getSourcePath());
		}
		mDataList.clear();
	}

	private void delIv(String sourcePath) {

	}

	private void removeImg(int location) {
		if (location + 1 <= mDataList.size()) {
			if (!mDataList.get(location).isSelected) {
				delIv(mDataList.get(location).getSourcePath());
			}
			mDataList.remove(location);
		}
	}

	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

		public void onPageSelected(int arg0) {
			currentPosition = arg0;
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {

		}
	};


	class MyPageAdapter extends PagerAdapter {
		private List<ImageItem> dataList = new ArrayList<ImageItem>();
		private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

		public MyPageAdapter(List<ImageItem> dataList) {
			this.dataList = dataList;
			int size = dataList.size();
			for (int i = 0; i != size; i++) {
				ImageView iv = new ImageView(ImageZoomActivity.this);
				if (!dataList.get(i).isSelected) {
					//Utility.loadImage(ImageZoomActivity.this, iv, dataList.get(i).sourcePath);
					//x.image().bind( iv, BaseHttpUrl.BASE_IMG_SERVER_URL+dataList.get(i).sourcePath);
					Glide.with(ImageZoomActivity.this).load(Api.IMG_URL +dataList.get(i).sourcePath).into(iv);
				} else {
				//ImageDisplayer.getInstance(ImageZoomActivity.this).displayBmp(iv, dataList.get(i).thumbnailPath, dataList.get(i).sourcePath);
				//	x.image().bind( iv, dataList.get(i).sourcePath);
					Glide.with(ImageZoomActivity.this).load(dataList.get(i).sourcePath).into(iv);

				}

				iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                iv.setScaleType(ImageView.ScaleType.FIT_CENTER );
				mViews.add(iv);
			}
		}

		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

		public Object instantiateItem(View arg0, int arg1) {
			ImageView iv = mViews.get(arg1);
			((ViewPager) arg0).addView(iv);
			return iv;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			if (mViews.size() >= arg1 + 1) {
				((ViewPager) arg0).removeView(mViews.get(arg1));
			}
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		public void removeView(int position) {
			if (position + 1 <= mViews.size()) {
				mViews.remove(position);
			}
		}

	}

}