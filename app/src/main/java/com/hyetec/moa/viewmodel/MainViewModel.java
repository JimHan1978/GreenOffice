package com.hyetec.moa.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.hyetec.moa.model.MainModel;
import com.hyetec.hmdp.core.di.scope.ActivityScope;
import com.hyetec.hmdp.core.mvvm.BaseViewModel;
import com.hyetec.hmdp.repository.http.Resource;
import com.hyetec.hmdp.repository.http.Status;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;


@ActivityScope
public class MainViewModel extends BaseViewModel<MainModel> {
    private final MediatorLiveData<List<String>> mMenuPaths = new MediatorLiveData<>();
    private MutableLiveData<Resource<List<String>>> mMenusResource;

    /**
     * 可以与 Fragment 共享此数据
     */
    private MutableLiveData<String> mMenu;
    private boolean mFirst = true;

    @Inject
    public MainViewModel(Application application, MainModel model) {
        super(application, model);
        //loadMenus();
    }

    public void loadMenus() {
        mMenusResource = mModel.getAllMenus();
        mMenuPaths.addSource(mMenusResource,observer->{
            mMenuPaths.removeSource(mMenusResource);
            mMenuPaths.addSource(mMenusResource, newResource -> {
                if (newResource == null) {
                    newResource = Resource.error("", null);
                }
                Timber.d("Load history menus: %s", newResource.status);

                if (newResource.status == Status.SUCCESS) {
                    mMenuPaths.postValue(newResource.data);
                    String location = newResource.data.get(0);

                    if (mFirst) {
                        //只有第一次时获取历史地址
                        mFirst = false;
                        mMenu.postValue(location);
                    }
                }
            });
        });
    }


    /**
     * 获取储存的位置记录
     *
     * @return 历史位置记录列表
     */
    public LiveData<List<String>> getHistoryMenus() {
        return mMenuPaths;
    }

    public MutableLiveData<String> getLocation() {
        if (mMenu == null) {
            mMenu = new MutableLiveData<>();
        }
        return mMenu;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        this.mMenu = null;
    }
}
