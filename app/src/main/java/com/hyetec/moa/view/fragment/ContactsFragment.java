package com.hyetec.moa.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseFragment;
import com.hyetec.hmdp.view.EditText_Clear;
import com.hyetec.hmdp.view.StickyLayout;
import com.hyetec.moa.R;
import com.hyetec.moa.model.db.GreenOfficeDb;
import com.hyetec.moa.model.db.UserDao;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.activity.GroupActivity;
import com.hyetec.moa.view.adapter.SearchListAdapter;
import com.hyetec.moa.view.adapter.TestBaseAdapter;
import com.hyetec.moa.view.ui.SideBar;
import com.hyetec.moa.viewmodel.ContactsViewModel;
import com.hyetec.moa.viewmodel.PersonalViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment<ContactsViewModel> implements StickyLayout.OnGiveUpTouchEventListener {


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
    @BindView(R.id.sticky_layout)
    StickyLayout stickyLayout;
    @BindView(R.id.sticky_content)
    ConstraintLayout stickyContent;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;

    private TestBaseAdapter adapter;
    Unbinder unbinder;
    private List<UserEntity> mContactList = new ArrayList<>();

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
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ContactsViewModel.class);
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


        stickyLayout.setOnGiveUpTouchEventListener(ContactsFragment.this);
        mContentFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getActivity().onTouchEvent(event);
                return false;
            }
        });

        mSidebar.setTextView(tvDialog);
        // 设置右侧[A-Z]快速导航栏触摸监听
        mSidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // scrollView.requestDisallowInterceptTouchEvent(true);
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s);
                if (position != -1) {
                    mContactsListView.setSelection(position);
                }
            }
        });

        mContactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
            }
        });
        mViewModel.getUserList();
        mViewModel.getPositionList();
        mViewModel.getGroupList();

        mViewModel.getContactList().observe(ContactsFragment.this, contactUserList -> {
            if (contactUserList != null ) {
                mContactList = contactUserList;
                adapter = new TestBaseAdapter(getActivity(), mContactList);
                mContactsListView.setAdapter(adapter);
            }
        });

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
        startActivity(new Intent(getActivity(),GroupActivity.class));
    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (mContactsListView.getFirstVisiblePosition() == 0) {
            View view = mContactsListView.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }
}
