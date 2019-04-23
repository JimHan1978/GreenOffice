package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.PunchCardModel;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.BssidEntity;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.PunchCardEntity;
import com.hyetec.moa.model.entity.ResultEntity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class PunchCardViewModel extends BaseViewModel<PunchCardModel> {

    private MediatorLiveData<BaseResponse<PunchCardEntity>> mResultData = new MediatorLiveData<BaseResponse<PunchCardEntity>>();
    private MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> mResultResponse;

    private MediatorLiveData<BaseResponse<PunchCardEntity>> mDakaData = new MediatorLiveData<BaseResponse<PunchCardEntity>>();
    private MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> mDakaResponse;

    private MediatorLiveData<BaseResponse<PunchCardEntity>> mPunchCardData = new MediatorLiveData<BaseResponse<PunchCardEntity>>();
    private MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> mPunchCardResponse;

    private MediatorLiveData<BaseResponse<BssidEntity>> mBssidsData = new MediatorLiveData<BaseResponse<BssidEntity>>();
    private MutableLiveData<Resource<BaseResponse<BssidEntity>>> mBssidsResponse;

    private MediatorLiveData<BaseResponse<DrawLotteryEntity>> mDrawLotteryData = new MediatorLiveData<BaseResponse<DrawLotteryEntity>>();
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLotteryResponse;

    @Inject
    public PunchCardViewModel(Application application, PunchCardModel model) {
        super(application, model);
    }


    public LiveData<BaseResponse<PunchCardEntity>> daKa(String userId, String deviceid) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", userId);
        request.put("deviceid", deviceid);

        mDakaData = new MediatorLiveData<>();
        mDakaResponse = mModel.daKa(request);
        mDakaData.addSource(mDakaResponse, infoResource -> {
            if (infoResource == null) {
                infoResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", infoResource.status);
            if (infoResource.status == Status.LOADING) {
                //STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (infoResource.status == Status.SUCCESS) {
                BaseResponse<PunchCardEntity> result = infoResource.data;
                mDakaData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (infoResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mDakaData;
    }

    public LiveData<BaseResponse<PunchCardEntity>> getInfo(String userId, String attedate) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", userId);
        request.put("attedate", attedate);

        mPunchCardData = new MediatorLiveData<>();
        mPunchCardResponse = mModel.getInfo(request);
        mPunchCardData.addSource(mPunchCardResponse, infoResource -> {
            if (infoResource == null) {
                infoResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", infoResource.status);
            if (infoResource.status == Status.LOADING) {
                //STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (infoResource.status == Status.SUCCESS) {
                BaseResponse<PunchCardEntity> result = infoResource.data;
                mPunchCardData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (infoResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });

        return mPunchCardData;
    }


    public LiveData<BaseResponse<BssidEntity>> getBssid() {
        mBssidsData = new MediatorLiveData<>();
        mBssidsResponse = mModel.getBssid();
        mBssidsData.addSource(mBssidsResponse, infoResource -> {
            if (infoResource == null) {
                infoResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", infoResource.status);
            if (infoResource.status == Status.LOADING) {
                //STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (infoResource.status == Status.SUCCESS) {
                BaseResponse<BssidEntity> result = infoResource.data;
                mBssidsData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (infoResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });

        return mBssidsData;
    }



    public LiveData<BaseResponse<DrawLotteryEntity>> getDrawLottery(String userId) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", userId);
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

    public LiveData<BaseResponse<PunchCardEntity>> submitReason(String userId, String attedate,String reason, int code,String attetype) {
        Map<String, String> request = new HashMap<>(1);
        request.put("userId", userId);
        request.put("attedate", attedate);
        request.put("workremark", reason);
        request.put("worktype", code+"");
        request.put("attetype", attetype);


        mResultData = new MediatorLiveData<>();
        mResultResponse = mModel.submitReason(request);
        mResultData.addSource(mResultResponse, infoResource -> {
            if (infoResource == null) {
                infoResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", infoResource.status);
            if (infoResource.status == Status.LOADING) {
                //STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (infoResource.status == Status.SUCCESS) {
                BaseResponse<PunchCardEntity> result = infoResource.data;
                mResultData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (infoResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });

        return mResultData;
    }

}
