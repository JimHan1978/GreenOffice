package com.hyetec.moa.di.component;

import com.hyetec.moa.app.OfficeApp;
import com.hyetec.moa.di.module.AppModule;
import com.hyetec.hmdp.core.di.component.ArchComponent;
import com.hyetec.hmdp.core.di.scope.AppScope;

import dagger.Component;

/**
 * @author xiaobailong24
 * @date 2017/7/15
 * Dagger AppComponent
 */
@AppScope
@Component(dependencies = ArchComponent.class,
        modules = {AppModule.class})
public interface AppComponent {
    /**
     * Dagger 注入
     *
     * @param officeApp OfficeApp
     */
    void inject(OfficeApp officeApp);
}
