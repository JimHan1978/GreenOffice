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
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.PositionEntity;
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
    private final MediatorLiveData<List <PositionEntity>> mPositionListData = new MediatorLiveData<List <PositionEntity>>();

    private MutableLiveData<Resource<BaseResponse<List <UserEntity>>>> mUserListResponse;
    private MutableLiveData<Resource<BaseResponse<List <GroupEntity>>>> mGroupListResponse;
    private MutableLiveData<Resource<BaseResponse<List <PositionEntity>>>> mPositionListResponse;

    private  MediatorLiveData<List <UserEntity>> mContactListData = new MediatorLiveData<List <UserEntity>>();
    private MutableLiveData<Resource<List <UserEntity>>> mContactListResponse;

    private MutableLiveData<List <ContactEntity>> mContactList;
    private MutableLiveData<Resource<List <GroupEntity>>> mGroupResponse;
    private MutableLiveData<Resource<List <PositionEntity>>> mPositionResponse;
    @Inject
    public ContactsViewModel(Application application, ContactsModel model) {
        super(application, model);
        //getContactUser();
    }

    /**a
     * 获取用户信息列表
     * @return
     */
    public LiveData<List <UserEntity>> getUserList() {
        Map<String, String> request = new HashMap<>(1);
        request.put("since", "");
        if (mUserListResponse != null) {
            mUserListData.removeSource(mUserListResponse);
        }
        mUserListResponse = mModel.getUserList(request);
        mUserListData.addSource(mUserListResponse, observer -> {
            mUserListData.removeSource(mUserListResponse);
            mUserListData.addSource(mUserListResponse, userResource -> {
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


    /**a
     * 获取Group信息列表
     * @return
     */
    public LiveData<List <GroupEntity>> getGroupList() {
        Map<String, String> request = new HashMap<>(1);
        request.put("since", "");
        if (mGroupListResponse != null) {
            mGroupListData.removeSource(mGroupListResponse);
        }
        mGroupListResponse = mModel.getGroupList(request);
        mGroupListData.addSource(mGroupListResponse, observer -> {
            mGroupListData.removeSource(mGroupListResponse);
            mGroupListData.addSource(mGroupListResponse, groupResource -> {
                if (groupResource == null) {
                    groupResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", groupResource.status);
                if (groupResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (groupResource.status == Status.SUCCESS) {
                    List <GroupEntity> result = groupResource.data.getResult();
                    mGroupListData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (groupResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mGroupListData;
    }

    /**a
     * 获取位置信息列表
     * @return
     */
    public LiveData<List <PositionEntity>> getPositionList() {
        Map<String, String> request = new HashMap<>(1);
      //  request.put("since", "");
        if (mPositionListResponse != null) {
            mPositionListData.removeSource(mPositionListResponse);
        }
        mPositionListResponse = mModel.getPositionList(request);
        mPositionListData.addSource(mPositionListResponse, observer -> {
            mPositionListData.removeSource(mPositionListResponse);
            mPositionListData.addSource(mPositionListResponse, positionResource -> {
                if (positionResource == null) {
                    positionResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", positionResource.status);
                if (positionResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (positionResource.status == Status.SUCCESS) {
                    List <PositionEntity> result = positionResource.data.getResult();
                    mPositionListData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (positionResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mPositionListData;
    }

    public LiveData<List <UserEntity>> getContactUser() {
        if (mContactListResponse != null) {
            mContactListData.removeSource(mContactListResponse);
        }
        mContactListResponse = mModel.getContactUser();
        mContactListData.addSource(mContactListResponse, observer -> {
            mContactListData.removeSource(mContactListResponse);
            mContactListData.addSource(mContactListResponse, contactResource -> {
                if (contactResource == null) {
                    contactResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", contactResource.status);
                if (contactResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (contactResource.status == Status.SUCCESS) {
                    List <UserEntity> result = contactResource.data;
                    mContactListData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (contactResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mContactListData;
    }
    public MutableLiveData<List<UserEntity>> getContactList() {
        getContactUser();
        if (mContactListData == null) {
            mContactListData =  new MediatorLiveData<List <UserEntity>>();
        }
        return mContactListData;
    }


}
