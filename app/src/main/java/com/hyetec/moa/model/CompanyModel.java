package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.entity.ActivityDeleteEntity;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.ActivityLotteryEntity;
import com.hyetec.moa.model.entity.ActivitySignEntity;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.model.entity.ResultEntity;
import com.hyetec.moa.model.entity.UploadEntity;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    private MutableLiveData<Resource<BaseResponse<List<UploadEntity>>>> mUploadListResource;
    private MutableLiveData<Resource<BaseResponse<ResultEntity>>> mResultEventResource;

    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLottery;
    private MutableLiveData<Resource<BaseResponse<DrawLotteryEntity>>> mDrawLotteryNum;
    private MutableLiveData<Resource<BaseResponse<List<ActivityLotteryEntity>>>> mActivityLotteryUserResource;
    private MutableLiveData<Resource<BaseResponse<List<ActivitySignEntity>>>> mActivitySignResource;

    private MutableLiveData<Resource<BaseResponse<List<ResultEntity>>>> mDeleteActivityResource;
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

    public MutableLiveData<Resource<BaseResponse<List<ActivityLotteryEntity>>>> getActivityLotteryList(Map<String,String> request){
        mActivityLotteryUserResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .loadActivityLottery(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<ActivityLotteryEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ActivityLotteryEntity>> listBaseResponse) {
                        mActivityLotteryUserResource.setValue(Resource.success(listBaseResponse));
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        mActivityLotteryUserResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mActivityLotteryUserResource.setValue(Resource.error(t.getMessage(), null));
                    }
                });
        return  mActivityLotteryUserResource;
    }

    public MutableLiveData<Resource<BaseResponse<List<ActivitySignEntity>>>> getActivitySignList(Map<String,String> request){
        mActivitySignResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .loadActivitySign(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<ActivitySignEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ActivitySignEntity>> listBaseResponse) {
                        mActivitySignResource.setValue(Resource.success(listBaseResponse));
                    }

                    @Override
                    public void onSubscribe(Subscription s) {
                        mActivitySignResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mActivitySignResource.setValue(Resource.error(t.getMessage(), null));
                    }
                });
        return  mActivitySignResource;
    }

    public MutableLiveData<Resource<BaseResponse<List<UploadEntity>>>> uploadImg( List<MultipartBody.Part> parts ,Map<String,String> request) {

        mUploadListResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .uploadImg(parts)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<UploadEntity>>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mUploadListResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mUploadListResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<UploadEntity>> response) {
                        mUploadListResource.setValue(Resource.success(response));

                    }
                });
        return mUploadListResource;

    }

    public MutableLiveData<Resource<BaseResponse<List<ResultEntity>>>> deleteActivity(Map<String, String> request) {

        mDeleteActivityResource=new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .deleteActivity(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<ResultEntity>>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mDeleteActivityResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mDeleteActivityResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<ResultEntity>> response) {
                        mDeleteActivityResource.setValue(Resource.success(response));

                    }
                });
        return mDeleteActivityResource;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mActivityEventResource = null;
    }
}
