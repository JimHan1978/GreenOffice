package com.hyetec.moa.di.module;

import com.hyetec.moa.model.ApplicationModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.IModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/2/20
 * 描述：
 **/
@Module
public class ApplicationModule {
    @FragmentScope
    @Provides
    IModel provideApplicationModel(ApplicationModel applicationModel) {
        return applicationModel;
    }
}
