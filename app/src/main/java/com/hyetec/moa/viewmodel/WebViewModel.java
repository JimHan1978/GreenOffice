package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.MainModel;
import com.hyetec.moa.model.WebModel;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class WebViewModel extends BaseViewModel<WebModel> {

    private final MediatorLiveData<BaseResponse<String>> mLoginData = new MediatorLiveData<BaseResponse<String>>();
    private MutableLiveData<Resource<BaseResponse<String>>> mLoginResponse;

    @Inject
    public WebViewModel(Application application, WebModel model) {
        super(application, model);
    }

    public LiveData<BaseResponse<String>> getData(String userId, String time) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userno", userId);
        request.put("date",time);
        if (mLoginResponse != null) {
            mLoginData.removeSource(mLoginResponse);
        }
        mLoginResponse = mModel.login(request);
        mLoginData.addSource(mLoginResponse, observer -> {
            mLoginData.removeSource(mLoginResponse);
            mLoginData.addSource(mLoginResponse, loginResource -> {
                if (loginResource == null) {
                    loginResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", loginResource.status);
                if (loginResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (loginResource.status == Status.SUCCESS) {
                    BaseResponse<String> result = loginResource.data;
                    mLoginData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (loginResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mLoginData;
    }
}
