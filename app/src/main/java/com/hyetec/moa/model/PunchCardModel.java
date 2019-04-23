package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.BssidEntity;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.PunchCardEntity;
import com.hyetec.moa.model.entity.ResultEntity;

import org.reactivestreams.Subscription;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;

public class PunchCardModel extends BaseModel {

    private RxErrorHandler mErrorHandler;

    private MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> mResource;
    private MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> mDakaResource;
    private MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> mResultResource;
    private MutableLiveData<Resource<BaseResponse<BssidEntity>>> mBssidResource;
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLottery;
    @Inject
    public PunchCardModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mResource = null;
    }
    public MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> daKa(Map<String, String> request) {
        mDakaResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .attesign(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<PunchCardEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mDakaResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mDakaResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<PunchCardEntity> response) {
                        mDakaResource.setValue(Resource.success(response));
                    }
                });
        return mDakaResource;

    }

    public MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> getInfo(Map<String, String> request) {
        mResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getAttendance(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<PunchCardEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<PunchCardEntity> response) {
                        mResource.setValue(Resource.success(response));
                    }
                });
        return mResource;

    }


    public MutableLiveData<Resource<BaseResponse<BssidEntity>>> getBssid() {
        mBssidResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getApBSSIds()
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<BssidEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mBssidResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mBssidResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<BssidEntity> response) {
                        mBssidResource.setValue(Resource.success(response));
                    }
                });
        return mBssidResource;

    }

    public MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> getDrawLottery(Map<String, String> request) {
        mDrawLottery=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getDrawLottery(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<DrawLotteryEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mDrawLottery.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mDrawLottery.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<DrawLotteryEntity> response) {
                        mDrawLottery.setValue(Resource.success(response));
                    }
                });
        return mDrawLottery;

    }

    public MutableLiveData<Resource<BaseResponse<PunchCardEntity>>> submitReason(Map<String, String> request) {
        mResultResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .submitWorkremark(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<PunchCardEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mResultResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mResultResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<PunchCardEntity> response) {
                        mResultResource.setValue(Resource.success(response));
                    }
                });
        return mResultResource;

    }
}
