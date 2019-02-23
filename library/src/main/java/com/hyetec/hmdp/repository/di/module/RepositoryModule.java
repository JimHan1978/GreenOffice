package com.hyetec.hmdp.repository.di.module;

import android.app.Application;

import com.hyetec.hmdp.repository.IRepositoryManager;
import com.hyetec.hmdp.repository.RepositoryManager;
import com.hyetec.hmdp.repository.cache.Cache;
import com.hyetec.hmdp.repository.cache.CacheType;

import javax.inject.Singleton;

import dagger.Lazy;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * @author xiaobailong24
 * @date 2017/9/28
 * Dagger RepositoryModule
 */
@Module
public class RepositoryModule {
    private Application mApplication;

    public RepositoryModule(Application application) {
        this.mApplication = application;
    }

    @Singleton
    @Provides
    IRepositoryManager provideRepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache,
                                                Cache.Factory cacheFactory,
                                                DatabaseModule.RoomConfiguration roomConfiguration) {
        return new RepositoryManager(mApplication, retrofit, rxCache, cacheFactory, roomConfiguration);
    }

    @Singleton
    @Provides
    Cache<String, Object> provideExtras(Cache.Factory cacheFactory) {
        return cacheFactory.build(CacheType.EXTRAS_CACHE_TYPE);
    }
}
