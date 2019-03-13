package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.db.GreenOfficeDb;
import com.hyetec.moa.model.entity.ContactEntity;
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
    private MutableLiveData<Resource<List<ContactEntity>>> mContactResource;

    @Inject
    public GroupModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }

    public MutableLiveData<Resource<List<ContactEntity>>> getContactUser() {

        if (mContactResource != null) {
            return mContactResource;
        } else {
            mContactResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRoomDatabase(GreenOfficeDb.class, GreenOfficeDb.DB_NAME)
                .userDao()
                .getUserAll(false)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<List<UserEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //mContactResource.postValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mContactResource.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(List<UserEntity> uList) {
                        List<ContactEntity> contactlist=new ArrayList<>();
                        if (uList != null && uList.size() > 0) {
                            for (UserEntity u : uList) {
                                ContactEntity c = new ContactEntity();
                                c.setEmail(u.getEmail());
                                c.setName(u.getUserName());
                                c.setNumber(u.getMobile());
                                c.setPhoto(u.getPhoto());
                                c.setShortName(u.getShortName());
                                c.setInitialIndex(u.getInitialIndex());
                                c.setPinyinName(u.getPinyinName());
                                c.setPositionName(u.getPositionName());
                                contactlist.add(c);
                            }
                            Collections.sort(contactlist, new PinyinComparator());
                        }
                        mContactResource.setValue(Resource.success(contactlist));

                    }
                });

        return mContactResource;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
