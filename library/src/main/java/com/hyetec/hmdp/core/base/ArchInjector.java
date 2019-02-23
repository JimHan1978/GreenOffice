package com.hyetec.hmdp.core.base;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.hyetec.hmdp.core.di.component.ArchComponent;
import com.hyetec.hmdp.core.di.component.DaggerArchComponent;
import com.hyetec.hmdp.core.di.module.ArchConfigModule;
import com.hyetec.hmdp.core.di.module.ArchModule;
import com.hyetec.hmdp.core.http.imageloader.glide.ImageConfigImpl;
import com.hyetec.hmdp.core.utils.ArchUtils;
import com.hyetec.hmdp.core.utils.ManifestArchParser;
import com.hyetec.hmdp.core.utils.Preconditions;

import java.util.List;


/**
 * @author xiaobailong24
 * @date 2017/9/30
 * ArchInjector，需要在 Application 初始化，注入 ArchComponent
 */
public class ArchInjector implements IArch {
    private Application mApplication;
    private ArchComponent mArchComponent;
    private ArchModule mArchModule;
    private List<ConfigArch> mConfigArches;
    private ComponentCallbacks2 mComponentCallback;


    public ArchInjector(Context context) {
        mConfigArches = new ManifestArchParser(context).parse();
    }


    public void onCreate(Application application) {
        this.mApplication = application;

        if (mArchModule == null) {
            mArchModule = new ArchModule(mApplication);
        }
        mArchComponent = DaggerArchComponent.builder()
                // .lifecycleModule(((ILifecycle) mApplication).getLifecycleModule())
                // .repositoryModule(((IRepository) mApplication).getRepositoryModule())
                .archConfigModule(getArchConfigModule(mApplication, mConfigArches))
                .archModule(new ArchModule(mApplication))
                .build();
        mArchComponent.inject(this);

        mComponentCallback = new AppComponentCallbacks(mApplication);
        mApplication.registerComponentCallbacks(mComponentCallback);
    }


    public void onTerminate(Application application) {
        if (mComponentCallback != null) {
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }
        this.mComponentCallback = null;
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link ConfigArch}的实现类,和Glide的配置方式相似
     *
     * @return ArchConfigModule
     */
    private ArchConfigModule getArchConfigModule(Context context, List<ConfigArch> configArches) {
        ArchConfigModule.Builder builder = ArchConfigModule.builder();
        // 注册 Arch 自定义配置
        for (ConfigArch module : configArches) {
            module.applyOptions(context, builder);
        }
        return builder.build();
    }

    @Override
    public ArchComponent getArchComponent() {
        Preconditions.checkNotNull(mArchComponent,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                ArchComponent.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mArchComponent;
    }

    @Override
    public ArchModule getArchModule() {
        Preconditions.checkNotNull(mArchModule,
                "%s cannot be null,first call %s#onCreate(Application) in %s#onCreate()",
                ArchModule.class.getName(), getClass().getName(), mApplication.getClass().getName());
        return this.mArchModule;
    }


    private static class AppComponentCallbacks implements ComponentCallbacks2 {
        private Application mApplication;

        public AppComponentCallbacks(Application application) {
            this.mApplication = application;
        }

        @Override
        public void onTrimMemory(int level) {
            //在 App 被置换到后台的时候，清理图片请求框架的内存缓存
            if (level == TRIM_MEMORY_UI_HIDDEN) {
                ArchUtils.INSTANCE.obtainArchComponent(mApplication)
                        .imageLoader()
                        .clear(mApplication, ImageConfigImpl.builder().isClearMemory(true).build());
            }
            //交给 Glide 处理内存情况
            //GlideArch.get(mApplication).trimMemory(level);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {
            //内存不足时清理图片请求框架的内存缓存
            ArchUtils.INSTANCE.obtainArchComponent(mApplication)
                    .imageLoader()
                    .clear(mApplication, ImageConfigImpl.builder().isClearMemory(true).build());
        }
    }
}
