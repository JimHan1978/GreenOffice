package com.hyetec.moa.model;

import android.app.Application;

import com.hyetec.hmdp.core.mvvm.BaseModel;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

public class ContactsModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    @Inject
    public ContactsModel(Application application) {
        super(application);
    }
}
