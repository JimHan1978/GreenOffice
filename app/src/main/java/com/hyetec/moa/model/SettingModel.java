package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.db.TraingDb;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.ResultEntity;
import com.hyetec.moa.model.entity.UserEntity;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;
import timber.log.Timber;

public class SettingModel extends BaseModel {

    private RxErrorHandler mErrorHandler;

    private MutableLiveData<Resource<BaseResponse<ResultEntity>>> mResource;
    @Inject
    public SettingModel(Application application) {
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

    public MutableLiveData<Resource<BaseResponse<ResultEntity>>> logout() {
        if (mResource == null) {
            mResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .logout()
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(logoutResponse -> {
//                    if (logoutResponse.getResult()==null) {
//                        throw new RuntimeException("userResponse no result");
//                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<ResultEntity>>(mErrorHandler) {
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
                    public void onNext(BaseResponse<ResultEntity> response) {
                        mResource.setValue(Resource.success(response));
                    }
                });
        return mResource;

    }
}
