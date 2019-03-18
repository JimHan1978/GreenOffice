/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hyetec.moa.di.module;

import com.hyetec.moa.view.activity.GroupActivity;
import com.hyetec.moa.view.activity.LoginActivity;
import com.hyetec.moa.view.activity.MainActivity;
import com.hyetec.hmdp.core.di.scope.ActivityScope;
import com.hyetec.moa.view.activity.SettingActivity;
import com.hyetec.moa.view.activity.WebViewActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BaseActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class,MainViewModelModule.class})
    abstract MainActivity contributeMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class,MainViewModelModule.class})
    abstract LoginActivity contributeLoginActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class,MainViewModelModule.class})
    abstract SettingActivity contributeSettingActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class,MainViewModelModule.class})
    abstract GroupActivity contributeGroupActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {MainModule.class,MainViewModelModule.class})
    abstract WebViewActivity contributeWebViewActivity();
}
