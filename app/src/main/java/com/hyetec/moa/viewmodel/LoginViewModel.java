package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Base64;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.LoginModel;
import com.hyetec.moa.model.MainModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
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
public class LoginViewModel extends BaseViewModel<LoginModel> {

    private final MediatorLiveData<BaseResponse<UserEntity>> mLoginData = new MediatorLiveData<BaseResponse<UserEntity>>();
    private MutableLiveData<Resource<BaseResponse<UserEntity>>> mLoginResponse;
    @Inject
    public LoginViewModel(Application application, LoginModel model) {
        super(application, model);
    }

    /**
     *
     * @return
     * @param userName
     * @param password
     */
    public LiveData<BaseResponse<UserEntity>> login(String userName, String password) {
        Map<String, String> request = new HashMap<>(1);
        request.put(Api.API_USER_NAME_KEY, userName);
        request.put(Api.API_USER_PASSWORD_KEY, Base64.encodeToString(password.getBytes(),Base64.NO_WRAP));
        if (mLoginResponse != null) {
            mLoginData.removeSource(mLoginResponse);
        }else {

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
                    BaseResponse<UserEntity> result = loginResource.data;
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
    @Override
    public void onCleared() {
        super.onCleared();

    }
}
