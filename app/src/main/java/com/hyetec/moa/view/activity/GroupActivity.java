package com.hyetec.moa.view.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.view.StickyLayout;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.ContactEntity;
import com.hyetec.moa.model.entity.GroupContactEntity;
import com.hyetec.moa.view.adapter.ExpandableContactAdapter;
import com.hyetec.moa.view.ui.PinnedHeaderExpandableListView;
import com.hyetec.moa.viewmodel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GroupActivity extends BaseActivity<GroupViewModel> implements StickyLayout.OnGiveUpTouchEventListener {


    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.lly_sticky_header)
    LinearLayout llyStickyHeader;
    @BindView(R.id.plv_expandablelist)
    PinnedHeaderExpandableListView plvExpandablelist;
    @BindView(R.id.sticky_content)
    LinearLayout stickyContent;
    @BindView(R.id.sly_layout)
    StickyLayout slyLayout;


    private List<GroupContactEntity> mGroupList = new ArrayList<GroupContactEntity>();
    private List<ContactEntity> mContactList = new ArrayList<ContactEntity>();
    private int expandFlag = -1;// 控制列表的展开
    private ExpandableContactAdapter mAdapter;
    private static final int STR_MSG = 1;
    private static final int STR_MSG_CON = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
     //   setContentView(R.layout.activity_group);

        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GroupViewModel.class);
        return R.layout.activity_group;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mViewModel.getContactUser().observe(this, contactUserList -> {
            if (contactUserList != null) {
                plvExpandablelist.setHeaderView(LayoutInflater.from(this).inflate(R.layout.item_group,
                        plvExpandablelist, false));
                mAdapter = new ExpandableContactAdapter(mGroupList, this, plvExpandablelist);
                plvExpandablelist.setAdapter(mAdapter);
            }
        });
        plvExpandablelist.setOnGroupClickListener(new GroupClickListener());
        slyLayout.setOnGiveUpTouchEventListener(this);


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


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == STR_MSG) {
                mGroupList = (List<GroupContactEntity>) msg.obj;
            } else if (msg.what == STR_MSG_CON) {
                mContactList = (List<ContactEntity>) msg.obj;
            }
            mAdapter = new ExpandableContactAdapter(mGroupList, getApplication(), plvExpandablelist);
            plvExpandablelist.setAdapter(mAdapter);
            plvExpandablelist.expandGroup(0);
        }

        ;
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
