package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Base64;

import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.MessageModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.model.entity.UserEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

@FragmentScope
public class MessageViewModel extends BaseViewModel<MessageModel> {
    
    private final MediatorLiveData<BaseResponse<List<MessageEntity>>> mMessageData = new MediatorLiveData<BaseResponse<List<MessageEntity>>>();
    private MutableLiveData<Resource<BaseResponse<List<MessageEntity>>>> mMessageResponse;
    
    @Inject
    public MessageViewModel(Application application, MessageModel model) {
        super(application, model);
    }

    public LiveData<BaseResponse<List<MessageEntity>>> getMessageList() {

        if (mMessageResponse != null) {
            mMessageData.removeSource(mMessageResponse);
        }
        mMessageResponse = mModel.getMessageLists();
        mMessageData.addSource(mMessageResponse, observer -> {
            mMessageData.removeSource(mMessageResponse);
            mMessageData.addSource(mMessageResponse, messageResource -> {
                if (messageResource == null) {
                    messageResource = Resource.error("", null);
                }
                Timber.d("Load weather now: %s", messageResource.status);
                if (messageResource.status == Status.LOADING) {
                    //STATUS.set(Status.LOADING);
                    Timber.d("Loadding.....");
                } else if (messageResource.status == Status.SUCCESS) {
                    BaseResponse<List<MessageEntity>> result = messageResource.data;
                    mMessageData.postValue(result);
                    //STATUS.set(Status.SUCCESS);
                } else if (messageResource.status == Status.ERROR) {
                    //STATUS.set(Status.ERROR);
                    Timber.d("Load error.....");
                }
            });
        });
        return mMessageData;
    }
}
