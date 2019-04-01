package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Base64;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.MainModel;
import com.hyetec.moa.model.SettingModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.ResultEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class SettingViewModel extends BaseViewModel<SettingModel> {

    private final MediatorLiveData<BaseResponse<ResultEntity>> mLogoutData = new MediatorLiveData<BaseResponse<ResultEntity>>();
    private MutableLiveData<Resource<BaseResponse<ResultEntity>>> mLogoutResponse;
    @Inject
    public SettingViewModel(Application application, SettingModel model) {
        super(application, model);
    }

    public LiveData<BaseResponse<ResultEntity>> logout() {

        mLogoutResponse  = mModel.logout();
        mLogoutData.addSource(mLogoutResponse, observer -> {
            mLogoutData.removeSource(mLogoutResponse);
            mLogoutData.addSource(mLogoutResponse, loginResource -> {
                if (loginResource == null) {
                    loginResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", loginResource.status);
                if (loginResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (loginResource.status == Status.SUCCESS) {
                    BaseResponse<ResultEntity> result = loginResource.data;

                    mLogoutData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (loginResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mLogoutData;
    }

}
