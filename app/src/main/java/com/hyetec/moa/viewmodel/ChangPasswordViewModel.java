package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Base64;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.ChangPasswordModel;
import com.hyetec.moa.model.LoginModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.ResultEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author : created by Jimhan
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class ChangPasswordViewModel extends BaseViewModel<ChangPasswordModel> {

    private final MediatorLiveData<BaseResponse<ResultEntity>> mLoginData = new MediatorLiveData<BaseResponse<ResultEntity>>();
    private MutableLiveData<Resource<BaseResponse<ResultEntity>>> mLoginResponse;
    @Inject
    public ChangPasswordViewModel(Application application, ChangPasswordModel model) {
        super(application, model);
    }


    public LiveData<BaseResponse<ResultEntity>> changePassword() {

      return mLoginData;
    }
}
