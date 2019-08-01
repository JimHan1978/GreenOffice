package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.DaysCalculationEntity;
import com.hyetec.moa.model.entity.HaveDoneLeaveEntity;
import com.hyetec.moa.model.entity.LeaveMessageEntity;
import com.hyetec.moa.model.entity.LeaveTypeEntity;
import com.hyetec.moa.model.entity.MyLeaveEntity;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;

public class LeaveModel extends BaseModel {

    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<BaseResponse<List<LeaveTypeEntity>>>> mLeaveTypeListResource;
    private MutableLiveData<Resource<BaseResponse<List<DaysCalculationEntity>>>> mLeaveDaysResource;
    private MutableLiveData<Resource<BaseResponse<List<MyLeaveEntity>>>> mMyLeaveListResource;
    private MutableLiveData<Resource<BaseResponse<LeaveMessageEntity>>> mMyLeaveSaveResource;
    private MutableLiveData<Resource<BaseResponse<MyLeaveEntity>>> mMyLeaveDetailResource;
    private MutableLiveData<Resource<BaseResponse<List<HaveDoneLeaveEntity>>>> mHaveDoneLeaveResource;
    private MutableLiveData<Resource<BaseResponse<List<HaveDoneLeaveEntity>>>> mUnfinishLeaveResource;
    private MutableLiveData<Resource<BaseResponse<LeaveMessageEntity>>> mCommitLeaveResource;

    @Inject
    public LeaveModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }

    public MutableLiveData<Resource<BaseResponse<LeaveMessageEntity>>> getmCommitLeaveResource(Map<String, String> request){

        mCommitLeaveResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .leaveApply(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<LeaveMessageEntity>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mCommitLeaveResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mCommitLeaveResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<LeaveMessageEntity> response) {
                        mCommitLeaveResource.setValue(Resource.success(response));

                    }
                });
        return mCommitLeaveResource;
    }


    public MutableLiveData<Resource<BaseResponse<List<HaveDoneLeaveEntity>>>> getmUnfinishLeaveResource(Map<String, String> request){

        mUnfinishLeaveResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .leaveflowTask(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<HaveDoneLeaveEntity>>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mUnfinishLeaveResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mUnfinishLeaveResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<HaveDoneLeaveEntity>> response) {
                        mUnfinishLeaveResource.setValue(Resource.success(response));

                    }
                });
        return mUnfinishLeaveResource;
    }


    public MutableLiveData<Resource<BaseResponse<List<HaveDoneLeaveEntity>>>> getmHaveDoneLeaveResource(Map<String, String> request){

        mHaveDoneLeaveResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .leaveflowTaskHis(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<HaveDoneLeaveEntity>>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mHaveDoneLeaveResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mHaveDoneLeaveResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<HaveDoneLeaveEntity>> response) {
                        mHaveDoneLeaveResource.setValue(Resource.success(response));

                    }
                });
        return mHaveDoneLeaveResource;
    }

    public MutableLiveData<Resource<BaseResponse<MyLeaveEntity>>> getmMyLeaveDetailResource(Map<String, String> request){

        mMyLeaveDetailResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .selectById(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<MyLeaveEntity>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mMyLeaveDetailResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mMyLeaveDetailResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<MyLeaveEntity> response) {
                        mMyLeaveDetailResource.setValue(Resource.success(response));

                    }
                });
        return mMyLeaveDetailResource;
    }

    public MutableLiveData<Resource<BaseResponse<LeaveMessageEntity>>> getmMyLeaveSaveResource(Map<String, String> request){

        mMyLeaveSaveResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .leaveApplySave(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<LeaveMessageEntity>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mMyLeaveSaveResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mMyLeaveSaveResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<LeaveMessageEntity> response) {
                        mMyLeaveSaveResource.setValue(Resource.success(response));

                    }
                });
        return mMyLeaveSaveResource;
    }



    public MutableLiveData<Resource<BaseResponse<List<MyLeaveEntity>>>> getmMyLeaveListResource(Map<String, String> request){

        mMyLeaveListResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .selectByUserId(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<MyLeaveEntity>>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mMyLeaveListResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mMyLeaveListResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<MyLeaveEntity>> response) {
                        mMyLeaveListResource.setValue(Resource.success(response));

                    }
                });
        return mMyLeaveListResource;
    }





    public MutableLiveData<Resource<BaseResponse<List<DaysCalculationEntity>>>> getmLeaveDaysResource(Map<String, String> request){

        mLeaveDaysResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .excludingHolidays(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<DaysCalculationEntity>>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mLeaveDaysResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mLeaveDaysResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<DaysCalculationEntity>> response) {
                        mLeaveDaysResource.setValue(Resource.success(response));

                    }
                });
        return mLeaveDaysResource;

    }



    public MutableLiveData<Resource<BaseResponse<List<LeaveTypeEntity>>>> getLeaveTypeLists(Map<String, String> request) {

        mLeaveTypeListResource = new MutableLiveData<>();
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .selectByType(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(messageResponse -> {

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<LeaveTypeEntity>>>(mErrorHandler){
                    @Override
                    public void onSubscribe(Subscription s) {
                        mLeaveTypeListResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mLeaveTypeListResource.setValue(Resource.error(t.getMessage(), null));

                    }

                    @Override
                    public void onNext(BaseResponse<List<LeaveTypeEntity>> response) {
                        mLeaveTypeListResource.setValue(Resource.success(response));

                    }
                });
                return mLeaveTypeListResource;
    }
}
