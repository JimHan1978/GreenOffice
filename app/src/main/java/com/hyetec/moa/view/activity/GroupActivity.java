package com.hyetec.moa.view.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.view.EditText_Clear;
import com.hyetec.hmdp.view.StickyLayout;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupContactEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.adapter.ExpandableContactAdapter;
import com.hyetec.moa.view.ui.PinnedHeaderExpandableListView;
import com.hyetec.moa.viewmodel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GroupActivity extends BaseActivity<GroupViewModel> implements StickyLayout.OnGiveUpTouchEventListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rly_main)
    RelativeLayout rly_main;
    @BindView(R.id.plv_expandablelist)
    PinnedHeaderExpandableListView plvExpandablelist;
    @BindView(R.id.CustomEditText)
    TextView CustomEditText;
    @BindView(R.id.sticky_layout)
    StickyLayout sticky_layout;


    private List<GroupContactEntity> mGroupList = new ArrayList<GroupContactEntity>();
    private List<UserEntity> mContactList = new ArrayList<UserEntity>();
    private int expandFlag = -1;// 控制列表的展开
    private ExpandableContactAdapter mAdapter;
    private static final int STR_MSG = 1;
    private static final int STR_MSG_CON = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GroupViewModel.class);
        return R.layout.activity_group;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tv_title.setText("通讯录");
        mViewModel.getGroupList().observe(this, groupEntities -> {
            mViewModel.getContactUser().observe(this, userEntities -> {
                mViewModel.getContactList().observe(this, contactUserList -> {
                    if (contactUserList != null) {
                        mGroupList =contactUserList;
                        plvExpandablelist.setHeaderView(LayoutInflater.from(this).inflate(R.layout.item_group_head,
                                plvExpandablelist, false));
                        mAdapter = new ExpandableContactAdapter(mGroupList, getApplicationContext());
                        plvExpandablelist.setAdapter(mAdapter);
                    }
                });
            });
        });
        plvExpandablelist.setOnGroupClickListener(new GroupClickListener());
        sticky_layout.setOnGiveUpTouchEventListener(this);

        rly_main.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });

    }

    @Override
    public boolean giveUpTouchEvent(MotionEvent event) {
        if (plvExpandablelist.getFirstVisiblePosition() == 0) {
            View view = plvExpandablelist.getChildAt(0);
            if (view != null && view.getTop() >= 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
            if (expandFlag == -1) {
                // 展开被选的group
                plvExpandablelist.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                plvExpandablelist.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            } else if (expandFlag == groupPosition) {
                plvExpandablelist.collapseGroup(expandFlag);
                expandFlag = -1;
            } else {
                plvExpandablelist.collapseGroup(expandFlag);
                // 展开被选的group
                plvExpandablelist.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                plvExpandablelist.setSelectedGroup(groupPosition);
                expandFlag = groupPosition;
            }
            return true;
        }
    }
}
