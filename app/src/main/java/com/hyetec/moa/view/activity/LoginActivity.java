package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.hmdp.view.SuperEditText;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.view.fragment.PersonalFragment;
import com.hyetec.moa.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class LoginActivity extends BaseActivity<LoginViewModel> {

    @BindView(R.id.tv_userName)
    SuperEditText mUserNameView;
    @BindView(R.id.tv_password)
    SuperEditText mPasswordView;
    @BindView(R.id.tv_reset_password)
    TextView mResetPasswordView;

    /**
     * UI 初始化
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
        return R.layout.login_activity;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btn_login)
    public void onLoginViewClicked() {

        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        mViewModel.login(userName,password).observe(this, loginData -> {
            if(loginData!=null && loginData.isSuccess()){
                //登录成功，跳转到主界面
                Timber.d("登录成功: %s", loginData.getResult().getUserName());
                ACache.get(this.getApplicationContext()).put(MoaApp.IS_LOGIN,"true");
                ACache.get(this.getApplicationContext()).put(MoaApp.USER_DATA,loginData.getResult());
                startActivity(new Intent(this,MainActivity.class));
                //Glide.with(this).load(Api.APP_DOMAIN+"urm/"+userEntity.getPhoto()).into(mAvatarView);
            }
        });
    }

    @OnClick(R.id.tv_reset_password)
    public void onViewClicked() {
    }
}
