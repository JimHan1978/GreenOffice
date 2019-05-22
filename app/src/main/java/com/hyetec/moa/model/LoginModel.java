package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;
import com.hyetec.moa.model.api.service.ContactsService;
import com.hyetec.moa.model.entity.BaseResponse;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.utils.HMDPPersistenCookieJar;

import org.reactivestreams.Subscription;

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
public class LoginModel extends BaseModel {
    private RxErrorHandler mErrorHandler;
    private MutableLiveData<Resource<BaseResponse<LoginUserEntity>>> mUserResource;

    @Inject
    public LoginModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }

    /**
     * 用户登录
     * @param request
     * @return
     */
    public MutableLiveData<Resource<BaseResponse<LoginUserEntity>>> login(Map<String,String> request) {
     //   if (mUserResource == null) {
            mUserResource = new MutableLiveData<>();
     //   }

        mRepositoryManager
                .obtainRetrofitService(ContactsService.class)
                .login(request)
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .doOnNext(userResponse -> {
                    if (userResponse.getResult()==null) {
                        //throw new RuntimeException("userResponse no result");
                    }
                    //保存用户信息到本地
                    //saveLocation(weatherNowResponse.getResults().get(0).getLocation());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriberOfFlowable<BaseResponse<LoginUserEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                      //  mUserResource.postValue(Resource.loading(null));
                        s.request(1);

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mUserResource.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(BaseResponse<LoginUserEntity> response) {
                        mUserResource.postValue(Resource.success(response));

                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();


                    }
                });
        return mUserResource;

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserResource = null;
    }
}
