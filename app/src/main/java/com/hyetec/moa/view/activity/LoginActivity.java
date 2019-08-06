package com.hyetec.moa.view.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.util.StringUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.hmdp.view.SuperEditText;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.fragment.PersonalFragment;
import com.hyetec.moa.viewmodel.LoginViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.gujin.toast.ToastUtil;
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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LoginViewModel.class);
        return R.layout.activity_login;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        if(ACache.get(this).getAsString(MoaApp.USER_NAME)!=null){
            mUserNameView.setText(ACache.get(this).getAsString(MoaApp.USER_NAME));
        }
        if(ACache.get(this).getAsString(MoaApp.POSS_WORD)!=null){
            mPasswordView.setText(ACache.get(this).getAsString(MoaApp.POSS_WORD));
        }
    }

    @OnClick(R.id.btn_login)
    public void onLoginViewClicked() {

        String userName = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)){
            ToastUtil.show("用户名或密码不能为空！");
            return;
        }

        mViewModel.login(userName,password).observe(this, new Observer<BaseResponse<LoginUserEntity>>() {
            @Override
            public void onChanged(@Nullable BaseResponse<LoginUserEntity> loginData) {
                if(loginData!=null && loginData.isSuccess()){
                    //登录成功，跳转到主界面
                    Timber.d("登录成功: %s", loginData.getResult().getUserName());
                    ACache.get(LoginActivity.this).put(MoaApp.IS_LOGIN,"true");
                    ACache.get(LoginActivity.this).put(MoaApp.USER_DATA,loginData.getResult());
                    ACache.get(LoginActivity.this).put(MoaApp.USER_NAME,userName);
                    ACache.get(LoginActivity.this).put(MoaApp.POSS_WORD,password);
                    GetUserInfo(loginData.getResult().getUserId());

                }else {
                    Toast.makeText(LoginActivity.this,loginData.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void GetUserInfo(int id) {
        mViewModel.getUserInfo(id+ "").observe(this, userEntity -> {
            if (userEntity != null) {
                ACache.get(LoginActivity.this).put(MoaApp.CARTOON,userEntity.getCartoon());
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @OnClick(R.id.tv_reset_password)
    public void onViewClicked() {
        //startActivity(new Intent(this,ChangePasswordActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
