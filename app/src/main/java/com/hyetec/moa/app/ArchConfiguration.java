package com.hyetec.moa.app;

import android.content.Context;

import com.hyetec.hmdp.core.base.ConfigArch;
import com.hyetec.hmdp.core.di.module.ArchConfigModule;

/**
 * @author xiaobailong24
 * @date 2017/7/24
 * Arch 的全局配置信息在此配置,需要将此实现类声明到 AndroidManifest 中
 */
public class ArchConfiguration implements ConfigArch {

    @Override
    public void applyOptions(Context context, ArchConfigModule.Builder builder) {
        //自己自定义图片加载逻辑
        //  builder.imageLoaderStrategy(new CustomLoaderStrategy());
    }
}
