package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.db.GreenOfficeDb;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.utils.PinyinComparator;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;


public class GroupModel extends BaseModel {

    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<List<UserEntity>>> mUserResource;
    private MutableLiveData<Resource<List<GroupEntity>>> mGroupResource;

    @Inject
    public GroupModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }

    public MutableLiveData<Resource<List<UserEntity>>> getUserList() {

        if (mUserResource != null) {
            return mUserResource;
        } else {
            mUserResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRoomDatabase(GreenOfficeDb.class, GreenOfficeDb.DB_NAME)
                .userDao()
                .getUserSort(false)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<List<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //mUserResource.postValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mUserResource.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(List<UserEntity> uList) {
                        mUserResource.setValue(Resource.success(uList));
                    }
                });

        return mUserResource;
    }

    public MutableLiveData<Resource<List<GroupEntity>>> getGroupList() {

        if (mGroupResource != null) {
            return mGroupResource;
        } else {
            mGroupResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRoomDatabase(GreenOfficeDb.class, GreenOfficeDb.DB_NAME)
                .groupDao()
                .getGroupAll(false)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<List<GroupEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //mUserResource.postValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mGroupResource.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(List<GroupEntity> groupEntities) {

                        mGroupResource.setValue(Resource.success(groupEntities));
                    }
                });

        return mGroupResource;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
