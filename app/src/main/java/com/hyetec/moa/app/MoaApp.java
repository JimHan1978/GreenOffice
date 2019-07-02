package com.hyetec.moa.app;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.hyetec.hmdp.core.base.BaseApplication;
import com.hyetec.moa.di.component.AppComponent;
import com.hyetec.moa.di.component.DaggerAppComponent;
import com.pgyersdk.crash.PgyCrashManager;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
//import com.hyetec.moa.di.component.DaggerAppComponent;

/**
 * @author jimhan
 * @date 2017/7/13
 * MoaApp 配置框架
 * {@link BaseApplication}
 */
public class MoaApp extends BaseApplication {

    public static final String IS_LOGIN = "isLogin";
    public static final String USER_DATA = "userData";
    public static final String USER_NAME = "userName";
    public static final String POSS_WORD = "possWord";
    public static final String USER_ID = "USER_ID";
    public static final String BSSIDS = "BSSIDS";

    private AppComponent mAppComponent;
    public static Context mContext;
    private static MoaApp application;
    private ArrayList<Activity> list = new ArrayList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        //DoraemonKit.install(this);
       mAppComponent = DaggerAppComponent
                .builder()
                .archComponent(getArchComponent())
                .build();
        mAppComponent.inject(this);
        mContext = getApplicationContext();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        PgyCrashManager.register(this);


    }
    /**
     * ； 提供公共的方法返回当前类的对象
     *
     * @return
     */
    public static synchronized MoaApp getInstance() {

        if (application == null) {
            application = new MoaApp();
        }
        return application;
    }

    /**
     * 添加每个Activity的对象
     *
     * @param activity
     */
    public void addActivity(Activity activity) {

        list.add(activity);

    }

    /**
     * 遍历所有Activity 关闭
     */
    public void closeAllActivity() {
        for (int i = 0; i < list.size(); i++) {
            Activity activity = list.get(i);
            activity.finish();
        }
    }
    public AppComponent getAppComponent() {
        return this.mAppComponent;
    }

}
