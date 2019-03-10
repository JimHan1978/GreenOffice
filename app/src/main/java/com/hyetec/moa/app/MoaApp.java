package com.hyetec.moa.app;

import com.hyetec.hmdp.core.base.BaseApplication;
import com.hyetec.moa.di.component.AppComponent;
import com.hyetec.moa.di.component.DaggerAppComponent;
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

    @Override
    public void onCreate() {
        super.onCreate();
        //DoraemonKit.install(this);
       mAppComponent = DaggerAppComponent
                .builder()
                .archComponent(getArchComponent())
                .build();
        mAppComponent.inject(this);

    }


    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }

}
