package com.hyetec.hmdp.core.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyetec.hmdp.core.base.ConfigArch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaobailong24
 * @date 2017/6/16
 * AndroidManifest.xml ManifestArchParser
 */
public final class ManifestArchParser {
    private static final String MODULE_VALUE = "ConfigArch";

    private final Context context;

    public ManifestArchParser(Context context) {
        this.context = context;
    }

    public List<ConfigArch> parse() {
        List<ConfigArch> armses = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                for (String key : appInfo.metaData.keySet()) {
                    if (MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                        Log.d("Arch ---> ",
                                String.format("Find ConfigArch in [%s]", key));
                        armses.add(parseModule(key));
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse ConfigArch", e);
        }

        return armses;
    }

    private static ConfigArch parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find ConfigArch implementation", e);
        }

        Object arms;
        try {
            arms = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate ConfigArch implementation for " + clazz, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate ConfigArch implementation for " + clazz, e);
        }

        if (!(arms instanceof ConfigArch)) {
            throw new RuntimeException("Expected instanceof ConfigArch, but found: " + arms);
        }
        return (ConfigArch) arms;
    }
}