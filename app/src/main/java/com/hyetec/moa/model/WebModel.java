package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.db.TraingDb;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.BillEntity;
import com.hyetec.moa.model.entity.MenuEntity;
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

public class WebModel extends BaseModel {

    private RxErrorHandler mErrorHandler;

    private MutableLiveData<Resource<BaseResponse<BillEntity>>> mUserResource;
    @Inject
    public WebModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserResource = null;
    }

    public MutableLiveData<Resource<BaseResponse<BillEntity>>> getData(Map<String,String> request) {
        if (mUserResource == null) {
            mUserResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .monthBill(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(userResponse -> {
                    if (userResponse.getResult()==null) {
                        throw new RuntimeException("userResponse no result");
                    }
                    //保存用户信息到本地
                    //saveLocation(weatherNowResponse.getResults().get(0).getLocation());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<BillEntity>>(mErrorHandler) {
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
                    public void onNext(BaseResponse<BillEntity> response) {
                        mUserResource.setValue(Resource.success(response));

                    }
                });
        return mUserResource;

    }
}
