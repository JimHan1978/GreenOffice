package com.hyetec.moa.model;

import android.app.Application;

import com.hyetec.hmdp.core.mvvm.BaseModel;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * @author : created by ${USER}
 * 版本：1.0
 * 创建日期：${DATE}
 * 描述：
 **/

public class MessageModel extends BaseModel {
    private RxErrorHandler mErrorHandler;

    @Inject
    public MessageModel(Application application) {
        super(application);
    }
}
