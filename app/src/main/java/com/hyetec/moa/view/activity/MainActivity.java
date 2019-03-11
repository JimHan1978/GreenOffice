package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.EventBusTags;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.view.adapter.MainPagerAdapter;
import com.hyetec.moa.view.fragment.ApplicationFragment;
import com.hyetec.moa.view.fragment.ContactsFragment;
import com.hyetec.moa.view.fragment.MessageFragment;
import com.hyetec.moa.view.fragment.PersonalFragment;
import com.hyetec.moa.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainViewModel> {

    private int mReplace = 0;
    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;
    private int iconImgs [] = {
            R.drawable.tab_message_selector,
            R.drawable.tab_contacts_selector,
            R.drawable.tab_app_selector,
            R.drawable.tab_setting_selector
    };

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.content_pager)
    ViewPager contentViewPager;

    @Override
    public int initView(Bundle savedInstanceState) {
//        String isLogin = ACache.get(this.getApplicationContext()).getAsString(MoaApp.IS_LOGIN);
//        if(isLogin==null|| isLogin.equals("false")){
//            startActivity(new Intent(this,LoginActivity.class));
//        }

        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        if (savedInstanceState != null) {
            //Restore data
            mReplace = savedInstanceState.getInt(EventBusTags.ACTIVITY_FRAGMENT_REPLACE);
        }
        return R.layout.main_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initViewPager();
    }

    private void initViewPager() {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        if (mFragmentTitles == null) {
            mFragmentTitles = new ArrayList<>();
        }

        MessageFragment messageFragment = (MessageFragment) getSupportFragmentManager()
                .findFragmentByTag("MessageFragment");
        ContactsFragment contactsFragment = (ContactsFragment) getSupportFragmentManager()
                .findFragmentByTag("ContactsFragment");
        ApplicationFragment applicationFragment = (ApplicationFragment) getSupportFragmentManager()
                .findFragmentByTag("ApplicationFragment");

        PersonalFragment personalFragment = (PersonalFragment) getSupportFragmentManager()
                .findFragmentByTag("PersonalFragment");
        if (messageFragment == null) {
            messageFragment = MessageFragment.newInstance();
        }
        if (contactsFragment == null) {
            contactsFragment = ContactsFragment.newInstance();
        }

        if (applicationFragment == null) {
            applicationFragment = ApplicationFragment.newInstance();
        }
        if (personalFragment == null) {
            personalFragment = PersonalFragment.newInstance();
        }

        mFragments.add(messageFragment);
        mFragments.add(contactsFragment);
        mFragments.add(applicationFragment);
        mFragments.add(personalFragment);
        mFragmentTitles.add("消息");
        mFragmentTitles.add("通讯录");
        mFragmentTitles.add("应用");
        mFragmentTitles.add("我的");

        //Setup ViewPager
        MainPagerAdapter pagerAdapter =
                new MainPagerAdapter(getSupportFragmentManager(), mFragments, mFragmentTitles);
        contentViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(contentViewPager);
        //自定义tab样式
        resetTabLayout();


        contentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Timber.i("onPageSelected: %s", position);
                mReplace = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 使用tablayout + viewpager时注意 如果设置了setupWithViewPager
     * 则需要重新执行下方对每个条目赋值
     * 否则会出现icon文字不显示的bug
     */
    private void resetTabLayout() {
        for (int i=0;i<tabLayout.getTabCount();i++){
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab!=null){
                tab.setText(mFragmentTitles.get(i));
                tab.setCustomView(getTabView(i));
            }
        }
    }

    /**
     * 自定义tab样式
     * @param position
     * @return
     */
    public View getTabView(int position) {
        View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_view, null);
        TextView tv = v.findViewById(R.id.tab_text);
        tv.setText(mFragmentTitles.get(position));
        ImageView img = v.findViewById(R.id.tab_icon);
        img.setImageResource(iconImgs[position]);
        return v;
    }

}
