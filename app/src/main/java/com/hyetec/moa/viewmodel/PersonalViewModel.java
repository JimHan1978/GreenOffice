package com.hyetec.moa.viewmodel;

import android.app.Application;

import com.hyetec.moa.model.PersonalModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.BaseViewModel;

import javax.inject.Inject;

@FragmentScope
public class PersonalViewModel extends BaseViewModel<PersonalModel> {
    @Inject
    public PersonalViewModel(Application application, PersonalModel model) {
        super(application, model);
    }
}
