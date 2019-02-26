package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.ContactsModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

@FragmentScope
public class ContactsViewModel extends BaseViewModel<ContactsModel> {

    private final MediatorLiveData<List <GroupEntity>> mGroupListData = new MediatorLiveData<List <GroupEntity>>();
    private final MediatorLiveData<List <UserEntity>> mUserListData = new MediatorLiveData<List <UserEntity>>();
    private MutableLiveData<Resource<BaseResponse<List <UserEntity>>>> mUserInfoResponse;

    @Inject
    public ContactsViewModel(Application application, ContactsModel model) {
        super(application, model);
    }

    /**a
     * 获取用户信息
     * @return
     */
    public LiveData<List <UserEntity>> getUserList() {
        Map<String, String> request = new HashMap<>(1);
        request.put("since", "");
        if (mUserInfoResponse != null) {
            mUserListData.removeSource(mUserInfoResponse);
        }
        mUserInfoResponse = mModel.getUserList(request);
        mUserListData.addSource(mUserInfoResponse, observer -> {
            mUserListData.removeSource(mUserInfoResponse);
            mUserListData.addSource(mUserInfoResponse, userResource -> {
                if (userResource == null) {
                    userResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", userResource.status);
                if (userResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (userResource.status == Status.SUCCESS) {
                    List <UserEntity> result = userResource.data.getResult();
                    mUserListData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (userResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mUserListData;
    }

}
