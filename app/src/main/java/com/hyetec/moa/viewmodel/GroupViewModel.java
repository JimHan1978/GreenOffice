package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.GroupModel;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupContactEntity;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class GroupViewModel extends BaseViewModel<GroupModel> {

    private MutableLiveData<Resource<List<UserEntity>>> mContactListResponse;
    private MediatorLiveData<List<UserEntity>> mContactListData = new MediatorLiveData<List<UserEntity>>();

    private MutableLiveData<Resource<List<GroupEntity>>> mGroupListResponse;
    private MediatorLiveData<List<GroupEntity>> mGroupListData = new MediatorLiveData<List<GroupEntity>>();

    private MediatorLiveData<List<GroupContactEntity>> groupContactEntities;

    List<GroupEntity> groupEntityList ;
    List<UserEntity> userEntityList;


    @Inject
    public GroupViewModel(Application application, GroupModel model) {
        super(application, model);
//        getContactUser();
//        getGroupList();
    }

    public LiveData<List<UserEntity>> getContactUser() {
        mContactListResponse = mModel.getUserList();
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
                    List<UserEntity> result = contactResource.data;
                    userEntityList =result;
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

    public LiveData<List<GroupEntity>> getGroupList() {
        mGroupListResponse = mModel.getGroupList();
        mGroupListData.addSource(mGroupListResponse, observer -> {
            mGroupListData.removeSource(mGroupListResponse);
            mGroupListData.addSource(mGroupListResponse, contactResource -> {
                if (contactResource == null) {
                    contactResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", contactResource.status);
                if (contactResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (contactResource.status == Status.SUCCESS) {
                    List<GroupEntity> result = contactResource.data;
                    mGroupListData.postValue(result);
                    groupEntityList=result;
                    //STATUS.set(Status.SUCCESS);
                } else if (contactResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mGroupListData;
    }


    public MediatorLiveData<List<GroupContactEntity>> getContactList() {
        if (groupContactEntities == null) {
            groupContactEntities = new MediatorLiveData<List<GroupContactEntity>>();
        }
        List<GroupContactEntity> groupContactEntityList = new ArrayList<>();

        if (groupEntityList!=null && userEntityList!=null) {
            for (int i = 0; i < groupEntityList.size(); i++) {
                GroupContactEntity groupContactEntity = new GroupContactEntity();
                List<UserEntity> userEntities = new ArrayList<>();
                for (int j = 0; j < userEntityList.size(); j++) {
                    if (groupEntityList.get(i).getOrgId() == userEntityList.get(j).getOrgId()) {
                        userEntities.add(userEntityList.get(j));
                    }
                }
                groupContactEntity.setUserEntities(userEntities);
                groupContactEntity.setGroupName(groupEntityList.get(i).getName());
                groupContactEntity.setUserNum(userEntities.size());
                groupContactEntityList.add(groupContactEntity);
            }
        }
        groupContactEntities.postValue(groupContactEntityList);
        return groupContactEntities;
    }
}
