package com.hyetec.moa.di.module;

import android.app.Application;

import com.hyetec.hmdp.core.di.module.ViewModelFactoryModule;

import dagger.Module;

/**
 * @author jimhan
 * @date 2017/7/22
 * Dagger AppModule
 */
@Module(includes = {ViewModelFactoryModule.class,
        BaseActivityModule.class,
        FragmentBuildersModule.class
})
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

}
