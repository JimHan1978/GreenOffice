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
import com.hyetec.moa.model.CompanyModel;
import com.hyetec.moa.model.LoginModel;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.model.entity.ResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * @author : created by Jimhan
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class CompanyViewModel extends BaseViewModel<CompanyModel> {

    private MediatorLiveData<BaseResponse<List<MessageEntity>>> mActivityEventListData = new MediatorLiveData<BaseResponse<List<MessageEntity>>>();
    private MutableLiveData<Resource<BaseResponse<List<MessageEntity>>>> mActivityEventListResponse;

    private MediatorLiveData<BaseResponse<ActivityEventEntity>> mActivityEventData = new MediatorLiveData<BaseResponse<ActivityEventEntity>>();
    private MutableLiveData<Resource<BaseResponse<ActivityEventEntity>>> mActivityEventResponse;

    private MediatorLiveData<BaseResponse<ResultEntity>> mActivityResultData = new MediatorLiveData<BaseResponse<ResultEntity>>();
    private MutableLiveData<Resource<BaseResponse<ResultEntity>>> mActivityResultResponse;

    private MediatorLiveData<BaseResponse<DrawLotteryEntity>> mDrawLotteryData = new MediatorLiveData<BaseResponse<DrawLotteryEntity>>();
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLotteryResponse;


    private MediatorLiveData<BaseResponse<DrawLotteryEntity>> mDrawLotteryNumData = new MediatorLiveData<BaseResponse<DrawLotteryEntity>>();
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLotteryNumResponse;
    @Inject
    public CompanyViewModel(Application application, CompanyModel model) {
        super(application, model);

    }

    public LiveData<BaseResponse<List<MessageEntity>>> getActivityEventList() {

        mActivityEventListData = new MediatorLiveData<>();
        mActivityEventListResponse = mModel.getActivityEventLists();
        mActivityEventListData.addSource(mActivityEventListResponse, messageResource -> {
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<List<MessageEntity>> result = messageResource.data;
                mActivityEventListData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });

        return mActivityEventListData;
    }

    public LiveData<BaseResponse<ActivityEventEntity>> getActivityEventDetails(String id) {
        Map<String, String> request = new HashMap<>(1);
        request.put("id", id);

        mActivityEventData = new MediatorLiveData<>();
        mActivityEventResponse = mModel.getActivityEvent(request);
        mActivityEventData.addSource(mActivityEventResponse, messageResource -> {
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<ActivityEventEntity> result = messageResource.data;
                mActivityEventData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });

        return mActivityEventData;
    }


    public LiveData<BaseResponse<ResultEntity>> activitySign(String id,String actId) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", id);
        request.put("actId", actId);

        mActivityResultData = new MediatorLiveData<>();
        mActivityResultResponse = mModel.getResultEvent(request);
        mActivityResultData.addSource(mActivityResultResponse, messageResource -> {
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<ResultEntity> result = messageResource.data;
                mActivityResultData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });

        return mActivityResultData;
    }

    public LiveData<BaseResponse<DrawLotteryEntity>> getDrawLottery(String userId,String actId) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", userId);
        request.put("actId", actId);
        mDrawLotteryData=new MediatorLiveData<>();
        mDrawLotteryResponse = mModel.getDrawLottery(request);
        mDrawLotteryData.addSource(mDrawLotteryResponse, infoResource -> {
            if (infoResource == null) {
                infoResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", infoResource.status);
            if (infoResource.status == Status.LOADING) {
                //STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (infoResource.status == Status.SUCCESS) {
                BaseResponse<DrawLotteryEntity> result = infoResource.data;
                mDrawLotteryData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (infoResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mDrawLotteryData;
    }
    public LiveData<BaseResponse<DrawLotteryEntity>> getDrawLotteryNumber(String userId,String actId) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", userId);
        request.put("actId", actId);
        mDrawLotteryNumData=new MediatorLiveData<>();
        mDrawLotteryNumResponse = mModel.getDrawLotteryNum(request);
        mDrawLotteryNumData.addSource(mDrawLotteryNumResponse, infoResource -> {
            if (infoResource == null) {
                infoResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", infoResource.status);
            if (infoResource.status == Status.LOADING) {
                //STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (infoResource.status == Status.SUCCESS) {
                BaseResponse<DrawLotteryEntity> result = infoResource.data;
                mDrawLotteryNumData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (infoResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mDrawLotteryNumData;
    }


    @Override
    public void onCleared() {
        super.onCleared();

    }
}
