package com.hyetec.moa.viewmodel;

import android.app.Application;

import com.hyetec.moa.model.ApplicationModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.BaseViewModel;

import javax.inject.Inject;

@FragmentScope
public class ApplicationViewModel extends BaseViewModel<ApplicationModel> {
    @Inject
    public ApplicationViewModel(Application application, ApplicationModel model) {
        super(application, model);
    }
}
