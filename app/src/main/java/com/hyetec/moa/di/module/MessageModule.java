package com.hyetec.moa.di.module;

import com.hyetec.moa.model.MessageModel;
import com.hyetec.hmdp.core.di.scope.FragmentScope;
import com.hyetec.hmdp.core.mvvm.IModel;

import dagger.Module;
import dagger.Provides;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019-02-20
 * 描述：消息Model注入模块
 **/
@Module
public class MessageModule {
    @FragmentScope
    @Provides
    IModel provideMessageModel(MessageModel messageModel) {
        return messageModel;
    }
}
