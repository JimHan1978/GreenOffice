package com.hyetec.moa.view.ui.ImageBucket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.moa.R;
import com.hyetec.moa.view.activity.CompanyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 图片选择
 */
public class ImageChooseActivity extends Activity {
    private List<ImageItem> mDataList2 = new ArrayList<ImageItem>();
    private String mBucketName;
    private int availableSize;
    private GridView mGridView;
    private TextView mBucketNameTv;
    private ImageView back;
    private TextView save;
    private ImageGridAdapter mAdapter;
    private Button mFinishBtn;
    private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
    private String photo = "";
    private String mImageCatalog;
    private String info;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_image_choose);

        mDataList2 = (List<ImageItem>) getIntent().getSerializableExtra(CustomConstants.EXTRA_IMAGE_LIST);
        if (mDataList2 == null)
            mDataList2 = new ArrayList<ImageItem>();
        mBucketName = getIntent().getStringExtra(CustomConstants.EXTRA_BUCKET_NAME);

        if (TextUtils.isEmpty(mBucketName)) {
            mBucketName = "请选择";
        }
        availableSize = getIntent().getIntExtra(CustomConstants.EXTRA_CAN_ADD_IMAGE_SIZE, CustomConstants.MAX_IMAGE_SIZE);
        info = getIntent().getExtras().getString("info");
        initView();
        initListener();

    }

    private void initView() {
        mBucketNameTv = (TextView) findViewById(R.id.tv_title);
        mBucketNameTv.setText(mBucketName);

        mGridView = (GridView) findViewById(R.id.gridview);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList2);
        mGridView.setAdapter(mAdapter);
        mFinishBtn = (Button) findViewById(R.id.finish_btn);
        back = (ImageView) findViewById(R.id.iv_left);
        save = (TextView) findViewById(R.id.tv_add);
        save.setVisibility(View.GONE);

        mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + availableSize + ")");
        mAdapter.notifyDataSetChanged();
    }

    private void initListener() {
        mFinishBtn.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                CompanyActivity.photoList.addAll(selectedImgs.values());
                finish();
            }

        });
        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageItem item = mDataList2.get(position);
                if (item.isSelected) {
                    item.isSelected = false;
                    selectedImgs.remove(item.imageId);
                } else {
                    if (selectedImgs.size() >= availableSize) {
                        Toast.makeText(ImageChooseActivity.this, "最多选择" + availableSize + "张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    item.isSelected = true;
                    selectedImgs.put(item.imageId, item);
                }

                mFinishBtn.setText("完成" + "(" + selectedImgs.size() + "/" + availableSize + ")");
                mAdapter.notifyDataSetChanged();
            }

        });

        back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageChooseActivity.this, ImageBucketChooseActivity.class);
                intent.putExtra(CustomConstants.EXTRA_CAN_ADD_IMAGE_SIZE, availableSize);
                intent.putExtra("info", info);
                startActivity(intent);
                finish();
            }
        });

    }

}