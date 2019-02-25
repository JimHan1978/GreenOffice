package com.hyetec.moa.view.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.hmdp.view.EditText_Clear;
import com.hyetec.moa.R;
import com.hyetec.moa.view.ui.SideBar;
import com.hyetec.moa.viewmodel.ContactsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment<ContactsViewModel> {


    @BindView(R.id.tv_title)
    TextView mTitleView;
    @BindView(R.id.et_search)
    EditText_Clear mSearchView;
    @BindView(R.id.sticky_header)
    ConstraintLayout mOrgView;
    @BindView(R.id.lv_contacts)
    StickyListHeadersListView mContactsListView;
    @BindView(R.id.sidebar)
    SideBar mSidebar;
    @BindView(R.id.fl_content)
    FrameLayout mContentFrame;

    Unbinder unbinder;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance() {
        ContactsFragment contactsFragment = new ContactsFragment();
        return contactsFragment;
    }


    /**
     * UI 初始化
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * Activity 与 Fragment 通信接口
     * 此方法是让外部调用使 Fragment 做一些操作的,比如说外部的 Fragment 想让 Fragment 对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传 {@link Message},通过what字段,来区分不同的方法,
     * 在此方法中就可以 switch 做不同的操作,这样就可以用统一的入口方法做不同的事
     * <p>
     * 新姿势：可以通过 Activity 的 ViewModel 共享数据给包含的 Fragment，配合 LiveData 好用到爆。
     *
     * @param data Object
     * @see <a href="https://developer.android.com/topic/libraries/architecture/viewmodel.html#sharing_data_between_fragments">Sharing Data Between Fragments</a>
     */
    @Override
    public void setData(Object data) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.sticky_header)
    public void onViewClicked() {
    }
}
