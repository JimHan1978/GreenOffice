package com.hyetec.moa.viewmodel;

import android.app.Application;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.moa.model.MainModel;

import javax.inject.Inject;

public class DetailsViewModel extends BaseViewModel<MainModel> {
    @Inject
    public DetailsViewModel(Application application, MainModel model) {
        super(application, model);
    }
}
