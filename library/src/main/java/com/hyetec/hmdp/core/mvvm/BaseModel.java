package com.hyetec.hmdp.core.mvvm;

import android.app.Application;

import com.hyetec.hmdp.repository.IRepositoryManager;
import com.hyetec.hmdp.repository.utils.RepositoryUtils;


/**
 * @author xiaobailong24
 * @date 2017/6/16
 * MVVM BaseModel
 */
public class BaseModel implements IModel {

    protected IRepositoryManager mRepositoryManager;


    public BaseModel(Application application) {
        this.mRepositoryManager = RepositoryUtils.INSTANCE
                .obtainRepositoryComponent(application)
                .repositoryManager();
    }

    @Override
    public void onDestroy() {
        this.mRepositoryManager = null;
    }
}
