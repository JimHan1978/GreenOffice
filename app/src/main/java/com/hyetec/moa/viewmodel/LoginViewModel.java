package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Base64;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.LoginModel;
import com.hyetec.moa.model.MainModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.LoginUserEntity;
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

    private MediatorLiveData<BaseResponse<LoginUserEntity>> mLoginData = new MediatorLiveData<BaseResponse<LoginUserEntity>>();
    private MutableLiveData<Resource<BaseResponse<LoginUserEntity>>> mLoginResponse;

    private final MediatorLiveData<UserEntity> mUserData = new MediatorLiveData<UserEntity>();
    private MutableLiveData<Resource<BaseResponse<UserEntity>>> mUserInfoResponse;

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
    public LiveData<BaseResponse<LoginUserEntity>> login(String userName, String password) {
        Map<String, String> request = new HashMap<>(1);
        request.put(Api.API_USER_NAME_KEY, userName);
        String temp = Base64.encodeToString(password.getBytes(),Base64.NO_WRAP);
        request.put(Api.API_USER_PASSWORD_KEY, Base64.encodeToString(password.getBytes(),Base64.NO_WRAP));
        mLoginData=new MediatorLiveData<>();
        mLoginResponse=mModel.login(request);
        mLoginData.addSource(mLoginResponse, new Observer<Resource<BaseResponse<LoginUserEntity>>>() {
            @Override
            public void onChanged(@Nullable Resource<BaseResponse<LoginUserEntity>> baseResponseResource) {
                if (baseResponseResource == null) {
                    baseResponseResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", baseResponseResource.status);
                if (baseResponseResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (baseResponseResource.status == Status.SUCCESS) {
                    BaseResponse<LoginUserEntity> result = baseResponseResource.data;
                    mLoginData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (baseResponseResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            }
        });

        return mLoginData;

    }
    /**a
     * 获取用户信息
     * @return
     */
    public LiveData<UserEntity> getUserInfo(String Id) {

        Map<String, String> request = new HashMap<>(1);
        request.put(Api.API_USER_ID_KEY, Id);
        if (mUserInfoResponse != null) {
            mUserData.removeSource(mUserInfoResponse);
        }
        mUserInfoResponse = mModel.getUserInfo(request);
        mUserData.addSource(mUserInfoResponse, observer -> {
            mUserData.removeSource(mUserInfoResponse);
            mUserData.addSource(mUserInfoResponse, userResource -> {
                if (userResource == null) {
                    userResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", userResource.status);
                if (userResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (userResource.status == Status.SUCCESS) {
                    UserEntity result = userResource.data.getResult();
                    mUserData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (userResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mUserData;
    }
    @Override
    public void onCleared() {
        super.onCleared();

    }
}
