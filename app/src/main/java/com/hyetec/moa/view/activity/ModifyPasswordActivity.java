package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.viewmodel.ModifyPasswordViewModel;
import com.hyetec.moa.viewmodel.SettingViewModel;

import butterknife.ButterKnife;

public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordViewModel> {


    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);

        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ModifyPasswordViewModel.class);
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
