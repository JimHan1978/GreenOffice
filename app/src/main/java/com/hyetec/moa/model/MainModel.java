package com.hyetec.moa.model;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.moa.model.db.TraingDb;
import com.hyetec.moa.model.entity.MenuEntity;
import com.hyetec.hmdp.core.mvvm.BaseModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriberOfFlowable;
import timber.log.Timber;

public class MainModel extends BaseModel {

    private RxErrorHandler mErrorHandler;

    private MutableLiveData<Resource<List<String>>> mMenus;
    @Inject
    public MainModel(Application application) {
        super(application);
        mErrorHandler = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .rxErrorHandler();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        mMenus = null;
    }

    /**
     * 从Room数据库查询所有菜单信息
     *
     * @return 所有菜单信息列表
     */
    public MutableLiveData<Resource<List<String>>> getAllMenus() {

        if (mMenus != null) {
            // Memory Cache
            return mMenus;
        } else {
            mMenus = new MutableLiveData<>();
        }
        mRepositoryManager
                .obtainRoomDatabase(TraingDb.class, TraingDb.DB_NAME)
                .menuDao()
                .getAll()
                .onBackpressureLatest()
                .subscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleSubscriberOfFlowable<List<MenuEntity>>(mErrorHandler) {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mMenus.postValue(Resource.loading(null));
                        s.request(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mMenus.postValue(Resource.error(t.getMessage(), null));
                    }

                    @Override
                    public void onNext(List<MenuEntity> menus) {
                        List<String> paths = new ArrayList<>(menus.size());
                        if (menus.size() == 0) {
                            paths.add("员工手册");
                        } else {
                            for (MenuEntity l : menus) {
                                paths.add(l.getName());
                            }
                        }
                        Timber.d("loadLocationPaths: %s", Arrays.toString(paths.toArray()));
                        mMenus.postValue(Resource.success(paths));
                    }
                });
        return mMenus;
    }
}
