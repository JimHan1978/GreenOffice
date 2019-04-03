/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 *
 */
package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cndemoz.avalidations.EditTextValidator;
import com.cndemoz.avalidations.ValidationModel;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.utils.ConfirmPasswordValidation;
import com.hyetec.moa.utils.CustomConstants;
import com.hyetec.moa.utils.PasswordValidation;
import com.hyetec.moa.viewmodel.ChangPasswordViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChangePasswordActivity extends BaseActivity<ChangPasswordViewModel> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.affirm_password)
    TextView affirmPassword;
    @BindView(R.id.et_affirm_password)
    EditText etAffirmPassword;
    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    private EditTextValidator editTextValidator;
    private String mPassword;
    private String affirm_password;
    private String userId;
    private String username = "";

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ChangPasswordViewModel.class);
        return R.layout.activity_change_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if(ACache.get(this).getAsObject(MoaApp.USER_DATA)!=null) {
            UserEntity user = (UserEntity) ACache.get(this).getAsObject(MoaApp.USER_DATA);
            userId = user.getUserId() + "";
            username = user.getUserName();
        }

        tvTitle.setText("修改密码");
        ivLeft.setVisibility(View.GONE);
        ivLeft.setVisibility(View.VISIBLE);
        editTextValidator = new EditTextValidator(this).add(new ValidationModel(etPassword, new PasswordValidation()))
                .add(new ValidationModel(etAffirmPassword, new ConfirmPasswordValidation())).execute();

    }

    private void submit() {
        String regex = "((?=.*[a-zA-Z])(?=.*\\d)(?=.*[#@$%^&])[a-zA-Z\\d#@$%^&]{8,20})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mPassword);
        if (!matcher.matches()) {
            Toast.makeText(ChangePasswordActivity.this, "密码必须包含字母、数字、特殊字符的组合，长度在8-20之间!", Toast.LENGTH_LONG).show();
            return;
        }
        String initps = etOldPassword.getText().toString();
        if (TextUtils.isEmpty(initps)) {
            Toast.makeText(ChangePasswordActivity.this, "原密码不能为空!", Toast.LENGTH_LONG).show();
            return;
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_left)
    public void onViewReturnClicked() {
        finish();
    }

    @OnClick(R.id.tv_submit)
    public void onViewSubmitClicked() {
        mPassword = etPassword.getText().toString();
        affirm_password = etAffirmPassword.getText().toString();
        CustomConstants.confirmPassword = mPassword;
        if (editTextValidator.validate()) {
            submit();
        }
    }
}
