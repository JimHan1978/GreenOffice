package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.ActivityPhotoEntity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.zxing.activity.CaptureActivity;
import com.hyetec.moa.viewmodel.PunchCardViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class CompanyActivity extends BaseActivity<PunchCardViewModel> {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.rly_scan)
    RelativeLayout rlyScan;
    @BindView(R.id.rly_shake)
    RelativeLayout rlyShake;
    @BindView(R.id.lv_item)
    MyListView lvItem;
    @BindView(R.id.iv_add)
    ImageView ivAdd;



    private CommonAdapter mAdapter;
    private List<ActivityPhotoEntity> mActivityImgList = new ArrayList<>();

    /**
     * UI 初始化
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_activities);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PunchCardViewModel.class);
        return R.layout.activity_bonus_list;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("活动");
        ivLeft.setVisibility(View.VISIBLE);

        mActivityImgList.add(new ActivityPhotoEntity());
        mActivityImgList.add(new ActivityPhotoEntity());
        lvItem.setAdapter(mAdapter = new CommonAdapter<ActivityPhotoEntity>(
                this, mActivityImgList, R.layout.layout_ativity_img) {
            @Override
            public void convert(ViewHolder helper, ActivityPhotoEntity item, int pos) {

                if (pos == 0) {
                    helper.setImageResource(R.id.iv_activity_photos, R.drawable.ic_activity_1);
                } else {
                    helper.setImageResource(R.id.iv_activity_photos, R.drawable.ic_activity_2);
                }


            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(this,scanResult,Toast.LENGTH_SHORT).show();

        }
    }
    @OnClick({R.id.rly_scan, R.id.rly_shake, R.id.iv_add, R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rly_scan:
                startActivityForResult(new Intent(this, CaptureActivity.class), 0);
                break;
            case R.id.rly_shake:
                break;
            case R.id.tv_add:
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
