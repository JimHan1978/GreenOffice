package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.viewmodel.LoginViewModel;
import com.hyetec.moa.viewmodel.MainViewModel;
import com.hyetec.moa.viewmodel.SettingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/3/11
 * 描述：设置页面
 **/
public class SettingActivity extends BaseActivity<SettingViewModel> {
    @BindView(R.id.layout_account)
    View mLayoutAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        ((TextView)mLayoutAccount.findViewById(R.id.tv_text)).setText("账户设置");

        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingViewModel.class);
        return R.layout.activity_setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
}
