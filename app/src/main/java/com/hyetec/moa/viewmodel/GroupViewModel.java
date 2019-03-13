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

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class GroupViewModel extends BaseViewModel<GroupModel> {

    private MutableLiveData<Resource<List <ContactEntity>>> mContactListResponse;
    private MediatorLiveData<List <ContactEntity>> mContactListData = new MediatorLiveData<List <ContactEntity>>();

    @Inject
    public GroupViewModel(Application application, GroupModel model) {
        super(application, model);
    }

    public LiveData<List<ContactEntity>> getContactUser() {
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
                    List <ContactEntity> result = contactResource.data;
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
}
