package com.hyetec.moa.di.module;

import android.arch.lifecycle.ViewModel;

import com.hyetec.moa.viewmodel.ApplicationViewModel;
import com.hyetec.hmdp.core.di.scope.ViewModelScope;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/2/20
 * 描述：
 **/
@Module(includes = {ApplicationModule.class})
public abstract class ApplicationViewModelModule {
    @Binds
    @IntoMap
    @ViewModelScope(ApplicationViewModel.class)
    abstract ViewModel bindApplicationViewModel(ApplicationViewModel applicationViewModel);
}
