package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.hyetec.moa.utils.DensityUtil;
import com.hyetec.moa.utils.TagAliasOperatorHelper;
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
import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

public class MainActivity extends BaseActivity<MainViewModel> {
    public static boolean isForeground = false;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    private int mReplace = 0;
    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;
    private int iconImgs [] = {
            R.drawable.tab_message_selector,
            R.drawable.tab_app_selector,
            R.drawable.tab_contacts_selector,
            R.drawable.tab_setting_selector
    };

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.content_pager)
    ViewPager contentViewPager;

    @Override
    public int initView(Bundle savedInstanceState) {


        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        if (savedInstanceState != null) {
            //Restore data
            mReplace = savedInstanceState.getInt(EventBusTags.ACTIVITY_FRAGMENT_REPLACE);
        }
        return R.layout.activity_main;
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
        ApplicationFragment applicationFragment = (ApplicationFragment) getSupportFragmentManager()
                .findFragmentByTag("ApplicationFragment");
        ContactsFragment contactsFragment = (ContactsFragment) getSupportFragmentManager()
                .findFragmentByTag("ContactsFragment");
        PersonalFragment personalFragment = (PersonalFragment) getSupportFragmentManager()
                .findFragmentByTag("PersonalFragment");
        if (messageFragment == null) {
            messageFragment = MessageFragment.newInstance();
        }
        if (applicationFragment == null) {
            applicationFragment = ApplicationFragment.newInstance();
        }
        if (contactsFragment == null) {
            contactsFragment = ContactsFragment.newInstance();
        }
        if (personalFragment == null) {
            personalFragment = PersonalFragment.newInstance();
        }


        mFragments.add(messageFragment);
        mFragments.add(applicationFragment);
        mFragments.add(contactsFragment);
        mFragments.add(personalFragment);

        mFragmentTitles.add("消息");
        mFragmentTitles.add("应用");
        mFragmentTitles.add("通讯录");
        mFragmentTitles.add("我的");

        //Setup ViewPager
        MainPagerAdapter pagerAdapter =
                new MainPagerAdapter(getSupportFragmentManager(), mFragments, mFragmentTitles);
        contentViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(contentViewPager);
        //自定义tab样式
        resetTabLayout();

        // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
        JPushInterface.init(getApplicationContext());
        JPushInterface.resumePush(this);
        //注册推送别名
        //setPushAlias("");

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

    private void setPushAlias(String userId){
        TagAliasOperatorHelper helper = TagAliasOperatorHelper.getInstance();
        TagAliasOperatorHelper.TagAliasBean tagAliasBean =  helper.createTagAliasBean(TagAliasOperatorHelper.ACTION_SET,true,userId,null);
        TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),tagAliasBean);
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
    protected void onResume() {
        super.onResume();
        isForeground = true;
        JPushInterface.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        isForeground = false;
        JPushInterface.onPause(this);
    }
}
