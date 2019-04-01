package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.viewmodel.SettingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/3/11
 * 描述：设置页面
 **/
public class SettingActivity extends BaseActivity<SettingViewModel> {
   /* @BindView(R.id.layout_account)
    View mLayoutAccount;*/

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingViewModel.class);
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.layout_account)
    public void onLayoutAccountClicked() {
    }


    @OnClick(R.id.layout_msg)
    public void onLayoutMsgClicked() {
    }

    @OnClick(R.id.layout_general)
    public void onLayoutGeneralClicked() {
    }

    @OnClick(R.id.layout_version)
    public void onLayoutVersionClicked() {
    }

    @OnClick(R.id.layout_about)
    public void onLayoutAboutClicked() {
    }

    @OnClick(R.id.tv_logout)
    public void onLogoutClicked() {
        //ACache.get()
        mViewModel.logout().observe(this, logoutData -> {
            if(logoutData!=null && logoutData.isSuccess()){
                ACache.get(this.getApplicationContext()).put(MoaApp.IS_LOGIN,"false");
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                JPushInterface.stopPush(this);
            }
        });

    }
}
