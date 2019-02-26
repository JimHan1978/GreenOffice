package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.UserEntity;

import org.reactivestreams.Subscription;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;

public class PersonalModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<BaseResponse<UserEntity>>> mUserResource;
    
    @Inject
    public PersonalModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }

    /**
     * 服务器获取用户信息
     * @param request
     * @return
     */
    public MutableLiveData<Resource<BaseResponse<UserEntity>>> getUserInfo(Map<String,String> request) {
        if (mUserResource == null) {
            mUserResource = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .getUserInfo(request)
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
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<UserEntity>>(mErrorHandler) {
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
                    public void onNext(BaseResponse<UserEntity> response) {
                        mUserResource.setValue(Resource.success(response));
                    }
                });
        return mUserResource;
        
    }
}
