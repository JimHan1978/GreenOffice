package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.hmdp.repository.http.IRetry;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.PersonalModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

@FragmentScope
public class PersonalViewModel extends BaseViewModel<PersonalModel>  {

    private final MediatorLiveData<UserEntity> mUserData = new MediatorLiveData<UserEntity>();
    private MutableLiveData<Resource<BaseResponse<UserEntity>>> mUserInfoResponse;
    @Inject
    public PersonalViewModel(Application application, PersonalModel model) {
        super(application, model);
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


}
