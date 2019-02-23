package com.hyetec.moa.app;

import com.didichuxing.doraemonkit.DoraemonKit;
import com.hyetec.moa.di.component.AppComponent;
import com.hyetec.moa.di.component.DaggerAppComponent;
import com.hyetec.hmdp.core.base.BaseApplication;

/**
 * @author jimhan
 * @date 2017/7/13
 * OfficeApp 配置框架
 * {@link BaseApplication}
 */
public class OfficeApp extends BaseApplication {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DoraemonKit.install(this);
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
