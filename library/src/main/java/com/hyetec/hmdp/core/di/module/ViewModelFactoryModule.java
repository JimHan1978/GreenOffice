package com.hyetec.hmdp.core.di.module;

import android.arch.lifecycle.ViewModelProvider;

import com.hyetec.hmdp.core.mvvm.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * @author xiaobailong24
 * @date 2017/7/31
 * Dagger ViewModelFactoryModule
 */
@Module
public abstract class ViewModelFactoryModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
