package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;
import com.hyetec.moa.model.CompanyModel;
import com.hyetec.moa.model.LeaveModel;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.LeaveMessageEntity;
import com.hyetec.moa.model.entity.LeaveTypeEntity;
import com.hyetec.moa.model.entity.MyLeaveEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class LeaveViewModel extends BaseViewModel<LeaveModel> {

    private MediatorLiveData<BaseResponse<List<LeaveTypeEntity>>> mLeaveTypeListData = new MediatorLiveData<BaseResponse<List<LeaveTypeEntity>>>();
    private MutableLiveData<Resource<BaseResponse<List<LeaveTypeEntity>>>> mLeaveTypeListResponse;

    private MediatorLiveData<BaseResponse<List<String>>> mLeaveDaysListData = new MediatorLiveData<BaseResponse<List<String>>>();
    private MutableLiveData<Resource<BaseResponse<List<String>>>> mLeaveDaysListResponse;

    private MediatorLiveData<BaseResponse<List<MyLeaveEntity>>> mMyLeaveListData = new MediatorLiveData<BaseResponse<List<MyLeaveEntity>>>();
    private MutableLiveData<Resource<BaseResponse<List<MyLeaveEntity>>>> mMyLeaveListResponse;

    private MediatorLiveData<BaseResponse<LeaveMessageEntity>> mMyLeaveSaveData = new MediatorLiveData<BaseResponse<LeaveMessageEntity>>();
    private MutableLiveData<Resource<BaseResponse<LeaveMessageEntity>>> mMyLeaveSaveResponse;

    private MediatorLiveData<BaseResponse<MyLeaveEntity>> mMyLeaveDetail = new MediatorLiveData<BaseResponse<MyLeaveEntity>>();
    private MutableLiveData<Resource<BaseResponse<MyLeaveEntity>>> mMyLeaveDetailResponse;

    @Inject
    public LeaveViewModel(Application application, LeaveModel model) {
        super(application, model);
    }

    public LiveData<BaseResponse<LeaveMessageEntity>> getLeaveSaveData(MyLeaveEntity myLeaveEntity){
        Map<String, String> request = new HashMap<>(1);
        if(myLeaveEntity.getApplyUserName() != null){
            request.put("applyUserName", myLeaveEntity.getApplyUserName());
        }
        if(myLeaveEntity.getEndDate() != null){
            request.put("endDate", myLeaveEntity.getEndDate());
        }
        if(myLeaveEntity.getStartDate() != null){
            request.put("startDate", myLeaveEntity.getStartDate());
        }
        if(myLeaveEntity.getValidHoliday() != 0){
            request.put("validHoliday", myLeaveEntity.getValidHoliday()+"");
        }
        if(myLeaveEntity.getType() != 0){
            request.put("type", myLeaveEntity.getType()+"");
        }
        if(myLeaveEntity.getWorkReplace() != null){
            request.put("workReplace", myLeaveEntity.getWorkReplace());
        }
        if(myLeaveEntity.getApplyReason() != null){
            request.put("applyReason", myLeaveEntity.getApplyReason());
        }
        if(myLeaveEntity.getStatus() != 0){
            request.put("status", myLeaveEntity.getStatus()+"");
        }

        mMyLeaveSaveData = new MediatorLiveData<>();
        mMyLeaveSaveResponse = mModel.getmMyLeaveSaveResource(request);
        mMyLeaveSaveData.addSource(mMyLeaveSaveResponse, messageResource ->{
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<LeaveMessageEntity> result = messageResource.data;
                mMyLeaveSaveData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mMyLeaveSaveData;
    }



    public LiveData<BaseResponse<MyLeaveEntity>> getMyLeaveDetail(String id){
        Map<String, String> request = new HashMap<>(1);
        request.put("id", id);

        mMyLeaveDetail = new MediatorLiveData<>();
        mMyLeaveDetailResponse = mModel.getmMyLeaveDetailResource(request);
        mMyLeaveDetail.addSource(mMyLeaveDetailResponse, messageResource ->{
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<MyLeaveEntity> result = messageResource.data;
                mMyLeaveDetail.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mMyLeaveDetail;

    }



    public LiveData<BaseResponse<List<MyLeaveEntity>>> getMyLeaveList(String sidx, String sord, String pageable, String page){
        Map<String, String> request = new HashMap<>(1);
        request.put("sidx", sidx);
        request.put("sord", sord);
        request.put("pageable", pageable);
        request.put("page", page);

        mMyLeaveListData = new MediatorLiveData<>();
        mMyLeaveListResponse = mModel.getmMyLeaveListResource(request);
        mMyLeaveListData.addSource(mMyLeaveListResponse, messageResource ->{
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<List<MyLeaveEntity>> result = messageResource.data;
                mMyLeaveListData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mMyLeaveListData;
    }




    public LiveData<BaseResponse<List<String>>> getLeaveDaysList(String startDate, String endDate){
        Map<String, String> request = new HashMap<>(1);
        request.put("startDate",startDate);
        request.put("endDate",endDate);

        mLeaveDaysListData = new MediatorLiveData<>();
        mLeaveDaysListResponse = mModel.getmLeaveDaysResource(request);
        mLeaveDaysListData.addSource(mLeaveDaysListResponse, messageResource ->{
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<List<String>> result = messageResource.data;
                mLeaveDaysListData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }
        });
        return mLeaveDaysListData;
    }


    public LiveData<BaseResponse<List<LeaveTypeEntity>>> getLeaveTypeList(String type) {
        Map<String, String> request = new HashMap<>(1);
        request.put("type",type);

        mLeaveTypeListData = new MediatorLiveData<>();
        mLeaveTypeListResponse = mModel.getLeaveTypeLists(request);
        mLeaveTypeListData.addSource(mLeaveTypeListResponse, messageResource ->{
            if (messageResource == null) {
                messageResource = Resource.error("", null);
            }
            Timber.d("Load weather now: %s", messageResource.status);
            if (messageResource.status == Status.LOADING) {
//                    STATUS.set(Status.LOADING);
                Timber.d("Loadding.....");
            } else if (messageResource.status == Status.SUCCESS) {
                BaseResponse<List<LeaveTypeEntity>> result = messageResource.data;
                mLeaveTypeListData.postValue(result);
                //STATUS.set(Status.SUCCESS);
            } else if (messageResource.status == Status.ERROR) {
                //STATUS.set(Status.ERROR);
                Timber.d("Load error.....");
            }

        });
        return mLeaveTypeListData;
    }


}
