package com.hyetec.hmdp.core.base;

import android.app.Application;
import android.content.Context;

import com.hyetec.hmdp.core.di.component.ArchComponent;
import com.hyetec.hmdp.core.di.module.ArchModule;
import com.hyetec.hmdp.lifecycle.LifecycleInjector;
import com.hyetec.hmdp.lifecycle.delegate.AppLifecycles;
import com.hyetec.hmdp.lifecycle.delegate.ILifecycle;
import com.hyetec.hmdp.lifecycle.di.component.LifecycleComponent;
import com.hyetec.hmdp.lifecycle.di.module.LifecycleModule;
import com.hyetec.hmdp.repository.IRepository;
import com.hyetec.hmdp.repository.RepositoryInjector;
import com.hyetec.hmdp.repository.di.component.RepositoryComponent;
import com.hyetec.hmdp.repository.di.module.RepositoryModule;


/**
 * @author xiaobailong24
 * @date 2017/6/16
 * Application 生命周期代理接口实现类
 */
public class AppDelegate implements AppLifecycles, ILifecycle, IRepository, IArch {
    private Application mApplication;
    /**
     * {@link RepositoryInjector}
     */
    private RepositoryInjector mRepositoryInjector;
    /**
     * {@link LifecycleInjector}
     */
    private LifecycleInjector mLifecycleInjector;
    /**
     * {@link ArchInjector}
     */
    private ArchInjector mArchInjector;


    public AppDelegate(Context context) {
        if (mRepositoryInjector == null) {
            mRepositoryInjector = new RepositoryInjector(context);
        }
        if (mLifecycleInjector == null) {
            mLifecycleInjector = new LifecycleInjector(context);
        }
        if (mArchInjector == null) {
            mArchInjector = new ArchInjector(context);
        }
    }

    @Override
    public void attachBaseContext(Context context) {
        mLifecycleInjector.attachBaseContext(context);
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;

        //Repository inject
        mRepositoryInjector.onCreate(mApplication);

        //Lifecycle inject
        mLifecycleInjector.onCreate(mApplication);

        //Arch Inject
        mArchInjector.onCreate(mApplication);

    }

    @Override
    public void onTerminate(Application application) {
        mLifecycleInjector.onTerminate(application);
        this.mLifecycleInjector = null;
        mArchInjector.onTerminate(application);
        this.mArchInjector = null;
        mRepositoryInjector.onTerminate(application);
        this.mRepositoryInjector = null;
        this.mApplication = null;
    }


    @Override
    public LifecycleComponent getLifecycleComponent() {
        return mLifecycleInjector.getLifecycleComponent();
    }

    @Override
    public LifecycleModule getLifecycleModule() {
        return mLifecycleInjector.getLifecycleModule();
    }

    @Override
    public RepositoryComponent getRepositoryComponent() {
        return mRepositoryInjector.getRepositoryComponent();
    }

    @Override
    public RepositoryModule getRepositoryModule() {
        return mRepositoryInjector.getRepositoryModule();
    }

    @Override
    public ArchComponent getArchComponent() {
        return mArchInjector.getArchComponent();
    }

    @Override
    public ArchModule getArchModule() {
        return mArchInjector.getArchModule();
    }
}