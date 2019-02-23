package com.hyetec.hmdp.core.utils;

import android.app.Application;
import android.content.Context;

import com.hyetec.hmdp.core.base.IArch;
import com.hyetec.hmdp.core.di.component.ArchComponent;

/**
 * @author xiaobailong24
 * @date 2017/8/23
 * MVVMArch Utils
 * https://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
 * 获取 ArchComponent 来拿到框架里的一切
 * {@link ArchComponent}
 */
public enum ArchUtils {
    /**
     * 单例模式枚举实现
     */
    INSTANCE;

    /**
     * 获取 {@link ArchComponent}，使用 Dagger 对外暴露的方法
     *
     * @param context Context
     * @return ArchComponent
     */
    public ArchComponent obtainArchComponent(Context context) {
        return obtainArchComponent((Application) context.getApplicationContext());
    }

    /**
     * 获取 {@link ArchComponent}，使用 Dagger 对外暴露的方法
     *
     * @param application Application
     * @return ArchComponent
     */
    public ArchComponent obtainArchComponent(Application application) {
        Preconditions.checkState(application instanceof IArch, "Application does not implements IArch");
        return ((IArch) application).getArchComponent();
    }
}
