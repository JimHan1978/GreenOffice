package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.view.adapter.ActivityImgAdapter;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.RoundImageView;
import com.hyetec.moa.viewmodel.CompanyViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyActivityImgActivity extends BaseActivity<CompanyViewModel> {




    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.layout_dot)
    LinearLayout linearLayout;

    private List<ActivityEventEntity.ImgListBean> mActivityImgList = new ArrayList<>();
    private ActivityEventEntity activityEventEntity;
    private List<View> mViewList = new ArrayList<>();
    private int size;
    private int pos;
    private ActivityImgAdapter mAdapter;


    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_img);
        ButterKnife.bind(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);
        return R.layout.activity_company_img;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        activityEventEntity = (ActivityEventEntity)getIntent().getSerializableExtra("data");
        mActivityImgList = activityEventEntity.getImgList();
        pos = getIntent().getIntExtra("pos",-1);
        size = mActivityImgList.size();
        for(int i=0;i!=size;i++){
            String s = Api.IMG_URL_ATTACHMENT+mActivityImgList.get(i).getUrl();
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View view = layoutInflater.inflate(R.layout.layout_ativity_img,null);
            //View view2 = layoutInflater.inflate(R.layout.item_dot,null);
            //linearLayout.addView(view2);
            RoundImageView roundImageView = view.findViewById(R.id.iv_activity_photos);
            roundImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Glide.with(CompanyActivityImgActivity.this).load(s).into(roundImageView);
            mViewList.add(view);
        }
        mAdapter = new ActivityImgAdapter(mViewList);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(pos);

    }

    /*@Override
    public boolean onSingleTapUp(MotionEvent e) {
        finish();
        return false;
    }
    //用户按下屏幕就会触发：

    @Override
    public boolean
    onDown(MotionEvent e) {

        return false;
    }

    //短按触摸屏

    @Override
    public void
    onShowPress(MotionEvent e) {

    }

    //点击屏幕后抬起时触发该事件



    //在屏幕上拖动控件

    @Override
    public boolean
    onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

        return false;
    }

//长按触摸屏

    @Override
    public void
    onLongPress(MotionEvent e) {

    }



    //switching from right to left
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final int FLING_MIN_DISTANCE = 100;
        final int FLING_MIN_VELOCITY = 200;
        if ((int) (e1.getX() - e2.getX()) > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            if(pos!=size-1){
                pos = pos + 1;
                String temp = Api.IMG_URL_ATTACHMENT+mActivityImgList.get(pos).getUrl();
                Glide.with(CompanyActivityImgActivity.this).load(temp).into(roundImageView);
            } else{

            }
        }
        if((int)(e2.getX()-e1.getX())> FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY){
            if(pos!=0){
                pos = pos - 1;
                String temp = Api.IMG_URL_ATTACHMENT+mActivityImgList.get(pos).getUrl();
                Glide.with(CompanyActivityImgActivity.this).load(temp).into(roundImageView);
            } else{

            }
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }*/
}
