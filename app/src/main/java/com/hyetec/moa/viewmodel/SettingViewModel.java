package com.hyetec.moa.viewmodel;

import android.app.Application;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.moa.model.MainModel;

import javax.inject.Inject;

public class SettingViewModel extends BaseViewModel<MainModel> {
    @Inject
    public SettingViewModel(Application application, MainModel model) {
        super(application, model);
    }
}
