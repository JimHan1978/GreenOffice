package com.hyetec.hmdp.core.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.hyetec.hmdp.core.di.component.ArchComponent;
import com.hyetec.hmdp.core.di.module.ArchModule;
import com.hyetec.hmdp.lifecycle.delegate.AppLifecycles;
import com.hyetec.hmdp.lifecycle.delegate.ILifecycle;
import com.hyetec.hmdp.lifecycle.di.component.LifecycleComponent;
import com.hyetec.hmdp.lifecycle.di.module.LifecycleModule;
import com.hyetec.hmdp.repository.IRepository;
import com.hyetec.hmdp.repository.di.component.RepositoryComponent;
import com.hyetec.hmdp.repository.di.module.RepositoryModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author xiaobailong24
 * MVVMArch BaseApplication
 */
public class BaseApplication extends Application
        implements IArch, ILifecycle, IRepository, HasActivityInjector, HasSupportFragmentInjector {
    //Dagger.Android Activity 注入
    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;
    //Dagger.Android Fragment 注入
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    private AppLifecycles mAppDelegate;

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param context Context
     */
    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        mAppDelegate = new AppDelegate(context);
        mAppDelegate.attachBaseContext(context);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mAppDelegate.onTerminate(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.mActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.mFragmentInjector;
    }

    @Override
    public LifecycleComponent getLifecycleComponent() {
        return ((ILifecycle) mAppDelegate).getLifecycleComponent();
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        return ((ILifecycle) mAppDelegate).getLifecycleModule();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return ((IRepository) mAppDelegate).getRepositoryComponent();
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        return ((IRepository) mAppDelegate).getRepositoryModule();
    }

    @Override
    public ArchComponent getArchComponent() {
        return ((IArch) mAppDelegate).getArchComponent();
    }

    @Override
    public ArchModule getArchModule() {
        return ((IArch) mAppDelegate).getArchModule();
    }
}
