package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.model.entity.ResultEntity;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class CompanyModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<BaseResponse<List<MessageEntity>>>> mActivityEventListResource;
    private MutableLiveData<Resource<BaseResponse<ActivityEventEntity>>> mActivityEventResource;
    private MutableLiveData<Resource<BaseResponse<ResultEntity>>> mResultEventResource;
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLottery;
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLotteryNum;
    @Inject
    public CompanyModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }

    public MutableLiveData<Resource<BaseResponse<List<MessageEntity>>>> getActivityEventLists() {

        mActivityEventListResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getActivityEventLists()
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<MessageEntity>>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mActivityEventListResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mActivityEventListResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<MessageEntity>> response) {
                        mActivityEventListResource.setValue(Resource.success(response));

                    }
                });
        return mActivityEventListResource;

    }

    public MutableLiveData<Resource<BaseResponse<ActivityEventEntity>>> getActivityEvent(Map<String, String> request) {

        mActivityEventResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getActivityEvent(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<ActivityEventEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mActivityEventResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mActivityEventResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<ActivityEventEntity> response) {
                        mActivityEventResource.setValue(Resource.success(response));

                    }
                });
        return mActivityEventResource;

    }

    public MutableLiveData<Resource<BaseResponse<ResultEntity>>> getResultEvent(Map<String, String> request) {

        mResultEventResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .activitySign(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<ResultEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mResultEventResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mResultEventResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<ResultEntity> response) {
                        mResultEventResource.setValue(Resource.success(response));

                    }
                });
        return mResultEventResource;

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


    public MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> getDrawLotteryNum(Map<String, String> request) {
        mDrawLotteryNum=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getDrawLotteryNum(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<DrawLotteryEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mDrawLotteryNum.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mDrawLotteryNum.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<DrawLotteryEntity> response) {
                        mDrawLotteryNum.setValue(Resource.success(response));
                    }
                });
        return mDrawLotteryNum;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivityEventResource = null;
    }
}
