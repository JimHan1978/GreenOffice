package com.hyetec.moa.di.module;

import android.arch.lifecycle.ViewModel;

import com.hyetec.moa.model.ChangPasswordModel;
import com.hyetec.moa.viewmodel.ChangPasswordViewModel;
import com.hyetec.moa.viewmodel.CompanyViewModel;
import com.hyetec.moa.viewmodel.DetailsViewModel;
import com.hyetec.moa.viewmodel.GroupViewModel;
import com.hyetec.moa.viewmodel.LoginViewModel;
import com.hyetec.moa.viewmodel.MainViewModel;
import com.hyetec.hmdp.core.di.scope.ViewModelScope;
import com.hyetec.moa.viewmodel.PunchCardViewModel;
import com.hyetec.moa.viewmodel.SettingViewModel;
import com.hyetec.moa.viewmodel.WebViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：${DATE}
 * 描述：
 **/

@Module(includes = {MainModule.class})
public abstract class MainViewModelModule {
    @Binds
    @IntoMap
    @ViewModelScope(MainViewModel.class)
    abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel loginViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(SettingViewModel.class)
    abstract ViewModel bindSettingViewModel(SettingViewModel settingViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(GroupViewModel.class)
    abstract ViewModel bindGroupViewModel(GroupViewModel groupViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(WebViewModel.class)
    abstract ViewModel bindWebViewModel(WebViewModel webViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(DetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(WebViewModel webViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(ChangPasswordViewModel.class)
    abstract ViewModel bindChangePasswordViewModel(ChangPasswordViewModel changPasswordViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(PunchCardViewModel.class)
    abstract ViewModel bindPunchCardViewModel(PunchCardViewModel punchCardViewModel);

    @Binds
    @IntoMap
    @ViewModelScope(CompanyViewModel.class)
    abstract ViewModel bindCompanyViewModel(CompanyViewModel companyViewModel);


}
