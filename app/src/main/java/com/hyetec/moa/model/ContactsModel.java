package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.icu.lang.UScript;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.db.GreenOfficeDb;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupEntity;
import com.hyetec.moa.model.entity.PositionEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.utils.PinyinComparator;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;
import timber.log.Timber;

public class ContactsModel extends BaseModel {

    private RxErrorHandler mErrorHandler;
    private GreenOfficeDb db;
    private MutableLiveData<Resource<BaseResponse<List<UserEntity>>>> mUserResource;
    private MutableLiveData<Resource<BaseResponse<List<GroupEntity>>>> mGroupResource;
    private MutableLiveData<Resource<BaseResponse<List<PositionEntity>>>> mPositionResource;

    private MutableLiveData<Resource<List<ContactEntity>>> mContactResource;
    @Inject
    public ContactsModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
        db = mRepositoryManager
                .obtainRoomDatabase(GreenOfficeDb.class, GreenOfficeDb.DB_NAME);
    }


    /**
     * 服务器获取用户信息
     *
     * @param request
     * @return
     */
    public MutableLiveData<Resource<BaseResponse<List<UserEntity>>>> getUserList(Map<String, String> request) {
        if (mUserResource == null) {
            mUserResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getUserList(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(userResponse -> {
                    if (userResponse.getResult() == null) {
                        throw new RuntimeException("userResponse no result");
                    } else {
                        saveUser(userResponse.getResult());
                    }
                    //保存用户信息到本地
                    // List<UserEntity> userEntities=selUser();

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<UserEntity>>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mUserResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mUserResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<List<UserEntity>> response) {
                        mUserResource.setValue(Resource.success(response));
                    }
                });
        return mUserResource;

    }


    /**
     * 服务器获取Group信息
     *
     * @param request
     * @return
     */
    public MutableLiveData<Resource<BaseResponse<List<GroupEntity>>>> getGroupList(Map<String, String> request) {
        if (mGroupResource == null) {
            mGroupResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getGroupList(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(grouResponse -> {
                    if (grouResponse.getResult() == null) {
                        throw new RuntimeException("userResponse no result");
                    } else {
                        saveGroup(grouResponse.getResult());
                    }
                    //保存用户信息到本地
                    //saveLocation(weatherNowResponse.getResults().get(0).getLocation());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<GroupEntity>>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mGroupResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mGroupResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<List<GroupEntity>> response) {
                        mGroupResource.setValue(Resource.success(response));
                    }
                });
        return mGroupResource;

    }

    /**
     * 服务器获取PositionEntity信息
     *
     * @param request
     * @return
     */
    public MutableLiveData<Resource<BaseResponse<List<PositionEntity>>>> getPositionList(Map<String, String> request) {
        if (mPositionResource == null) {
            mPositionResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getPositionList(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(positionResource -> {
                    if (positionResource.getResult() == null) {
                        throw new RuntimeException("userResponse no result");
                    } else {
                        savePosition(positionResource.getResult());
                    }
                    //保存用户信息到本地
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<List<PositionEntity>>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mPositionResource.setValue(Resource.loading(null));
                        s.request(1);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mPositionResource.setValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<List<PositionEntity>> response) {
                        mPositionResource.setValue(Resource.success(response));
                    }
                });
        return mPositionResource;

    }

    public MutableLiveData<Resource<List<ContactEntity>>>  getContactUser() {

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



    /**
     * 保存user到数据库
     */
    public void saveUser(List<UserEntity> userEntity) {

        for (int i = 0; i < userEntity.size(); i++) {
            if (db.userDao().getUserById(userEntity.get(i).getUserId()) == null) {
                db.userDao().insertUser(userEntity.get(i));
            } else {
                db.userDao().updateUser(userEntity.get(i));
            }
        }


    }



    /**
     * PositionEntity
     */
    public void savePosition(List<PositionEntity> positionEntities) {

        for (int i = 0; i < positionEntities.size(); i++) {
            if (db.positionDao().getPositionById(positionEntities.get(i).getPositionId()) == null) {
                db.positionDao().insertPosition(positionEntities.get(i));
            } else {
                db.positionDao().updatePosition(positionEntities.get(i));
            }
        }
    }

    /**
     * 保存group到数据库
     */
    public void saveGroup(List<GroupEntity> groupEntities) {

        for (int i = 0; i < groupEntities.size(); i++) {
            if (db.groupDao().getGroupBgid(groupEntities.get(i).getGroupId()) == null) {
                db.groupDao().insertGroup(groupEntities.get(i));
            } else {
                db.groupDao().updata(groupEntities.get(i));
            }
        }


    }
}
