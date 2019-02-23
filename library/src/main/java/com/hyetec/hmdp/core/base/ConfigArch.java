package com.hyetec.hmdp.core.base;

import android.content.Context;

import com.hyetec.hmdp.core.di.module.ArchConfigModule;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * 框架配置接口
 */
public interface ConfigArch {
    /**
     * 使用{@link ArchConfigModule.Builder}给框架配置一些配置参数
     *
     * @param context: Context
     * @param builder: ArchConfigModule.Builder
     */
    void applyOptions(Context context, ArchConfigModule.Builder builder);
}
