package com.hyetec.moa.model;

import android.app.Application;

import com.hyetec.hmdp.core.mvvm.BaseModel;

import javax.inject.Inject;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/2/20
 * 描述：
 **/
public class ApplicationModel extends BaseModel {
    @Inject
    public ApplicationModel(Application application) {
        super(application);
    }
}
