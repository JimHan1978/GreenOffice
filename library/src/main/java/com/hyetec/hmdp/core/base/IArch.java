package com.hyetec.hmdp.core.base;


import com.hyetec.hmdp.core.di.component.ArchComponent;
import com.hyetec.hmdp.core.di.module.ArchModule;

/**
 * @author xiaobailong24
 * @date 2017/9/30
 * Application 继承该接口，就可以具有 ArchComponent 提供的方法。
 */
public interface IArch {
    /**
     * 获得全局 ArchComponent
     *
     * @return ArchComponent
     */
    ArchComponent getArchComponent();


    /**
     * 获得全局 ArchModule 重用
     *
     * @return ArchModule
     */
    ArchModule getArchModule();
}
