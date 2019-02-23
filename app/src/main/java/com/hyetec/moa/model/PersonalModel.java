package com.hyetec.moa.model;

import android.app.Application;

import com.hyetec.hmdp.core.mvvm.BaseModel;

import javax.inject.Inject;

public class PersonalModel extends BaseModel {
    @Inject
    public PersonalModel(Application application) {
        super(application);
    }
}
