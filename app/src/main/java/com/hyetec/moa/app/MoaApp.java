package com.hyetec.moa.app;

import android.content.Context;

import com.hyetec.hmdp.core.base.BaseApplication;
import com.hyetec.moa.di.component.AppComponent;
import com.hyetec.moa.di.component.DaggerAppComponent;

import cn.jpush.android.api.JPushInterface;
//import com.hyetec.moa.di.component.DaggerAppComponent;

/**
 * @author jimhan
 * @date 2017/7/13
 * MoaApp 配置框架
 * {@link BaseApplication}
 */
public class MoaApp extends BaseApplication {

    public static final String IS_LOGIN = "isLogin";
    public static final String USER_DATA = "userData";
    private AppComponent mAppComponent;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //DoraemonKit.install(this);
       mAppComponent = DaggerAppComponent
                .builder()
                .archComponent(getArchComponent())
                .build();
        mAppComponent.inject(this);
        mContext = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }


    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }

}
