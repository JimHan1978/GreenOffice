package com.hyetec.moa.view.ui.ImageBucket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hyetec.moa.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 选择相册
 * 
 */

public class ImageBucketChooseActivity extends Activity {
	private ImageFetcher mHelper;
	private List<ImageBucket> mDataList = new ArrayList<ImageBucket>();
	private ListView mListView;
	private ImageView back;
	private TextView title, save;
	private ImageBucketAdapter mAdapter;
	private int availableSize;
	private String info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_bucket_choose);

		mHelper = ImageFetcher.getInstance(getApplicationContext());
		initData();
		initView();
	}

	private void initData() {
		mDataList = mHelper.getImagesBucketList(false);
		availableSize = getIntent().getIntExtra(CustomConstants.EXTRA_CAN_ADD_IMAGE_SIZE, CustomConstants.MAX_IMAGE_SIZE);
		info = getIntent().getExtras().getString("info");
	}

	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		back = (ImageView) findViewById(R.id.iv_left);
		save = (TextView) findViewById(R.id.tv_add);
		save.setVisibility(View.GONE);
		title = (TextView) findViewById(R.id.tv_title);
		title.setText("相册");
		mAdapter = new ImageBucketAdapter(this, mDataList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				// selectOne(position);

				Intent intent = new Intent(ImageBucketChooseActivity.this, ImageChooseActivity.class);
				intent.putExtra(CustomConstants.EXTRA_IMAGE_LIST, (Serializable) mDataList.get(position).imageList);
				intent.putExtra(CustomConstants.EXTRA_BUCKET_NAME, mDataList.get(position).bucketName);
				intent.putExtra(CustomConstants.EXTRA_CAN_ADD_IMAGE_SIZE, availableSize);
				intent.putExtra("info", info);

				startActivity(intent);
				finish();
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(ImageBucketChooseActivity.this,
				// PublishActivity.class);
				// startActivity(intent);
				finish();
			}
		});
	}

	// private void selectOne(int position)
	// {
	// int size = mDataList.size();
	// for (int i = 0; i != size; i++)
	// {
	// if (i == position) mDataList.get(i).selected = true;
	// else
	// {
	// mDataList.get(i).selected = false;
	// }
	// }
	// mAdapter.notifyDataSetChanged();
	// }

}
