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

import com.hyetec.moa.view.fragment.ApplicationFragment;
import com.hyetec.moa.view.fragment.ContactsFragment;
import com.hyetec.moa.view.fragment.MessageFragment;
import com.hyetec.moa.view.fragment.PersonalFragment;
import com.hyetec.hmdp.core.di.scope.FragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = {MessageModule.class, MessageViewModelModule.class})
    abstract MessageFragment contributeMessageFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = {ContactsModule.class, ContactsViewModelModule.class})
    abstract ContactsFragment contributeContactsFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = {ApplicationModule.class, ApplicationViewModelModule.class})
    abstract ApplicationFragment contributeApplicationFragment();

    @FragmentScope
    @ContributesAndroidInjector(modules = {PersonalModule.class, PersonalViewModelModule.class})
    abstract PersonalFragment contributePersonalFragment();


//    @ContributesAndroidInjector
//    abstract SearchFragment contributeSearchFragment();
}
