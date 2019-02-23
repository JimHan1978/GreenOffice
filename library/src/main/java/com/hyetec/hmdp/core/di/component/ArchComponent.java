package com.hyetec.hmdp.core.di.component;

import android.app.Application;

import com.hyetec.hmdp.core.base.ArchInjector;
import com.hyetec.hmdp.core.di.module.ArchConfigModule;
import com.hyetec.hmdp.core.di.module.ArchModule;
import com.hyetec.hmdp.core.di.module.ViewModelFactoryModule;
import com.hyetec.hmdp.core.http.imageloader.ImageLoader;
import com.hyetec.hmdp.core.utils.ArchUtils;
import com.hyetec.hmdp.lifecycle.di.module.LifecycleModule;
import com.hyetec.hmdp.repository.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author xiaobailong24
 * @date 2017/7/13
 * DaggerArchComponent 向外提供一些方法获取需要的对象，
 * 通过 {@link ArchUtils} 获取
 */
@Singleton
@Component(modules = {ViewModelFactoryModule.class,
        RepositoryModule.class, LifecycleModule.class,
       ArchModule.class, ArchConfigModule.class})
public interface ArchComponent {
    /**
     * 获取 Application
     *
     * @return Application
     */
    Application application();


    /**
     * 图片加载管理器，策略模式，默认使用 Glide
     *
     * @return ImageLoader
     */
    ImageLoader imageLoader();

    /**
     * Dagger 注入
     *
     * @param armsInjector ArchInjector
     */
    void inject(ArchInjector armsInjector);
}
