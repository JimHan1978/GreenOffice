package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import com.hyetec.hmdp.view.StickyLayout;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.GroupContactEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.view.adapter.ExpandableContactAdapter;
import com.hyetec.moa.view.ui.PinnedHeaderExpandableListView;
import com.hyetec.moa.viewmodel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GroupActivity extends BaseActivity<GroupViewModel>  {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rly_main)
    RelativeLayout rly_main;
    @BindView(R.id.plv_expandablelist)
    PinnedHeaderExpandableListView plvExpandablelist;
    @BindView(R.id.iv_left)
    ImageView ivLeft;


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
        ivLeft.setVisibility(View.VISIBLE);
        mViewModel.getGroupList().observe(this, groupEntities -> {
            mViewModel.getContactUser().observe(this, userEntities -> {
                mViewModel.getContactList().observe(this, contactUserList -> {
                    if (contactUserList != null) {
                        mGroupList = contactUserList;
//                        for (int i=0;i<mGroupList.size();i++){
//                            if(mGroupList.get(i).getUserEntities() !=null && mGroupList.get(i).getUserEntities().size()==0){
//                                mGroupList.remove(i);
//                                break;
//                            }
//                        }
                        plvExpandablelist.setHeaderView(LayoutInflater.from(this).inflate(R.layout.item_group_head,
                                plvExpandablelist, false));
                        mAdapter = new ExpandableContactAdapter(mGroupList, this);
                        plvExpandablelist.setAdapter(mAdapter);
                    }
                });
            });
        });
        plvExpandablelist.setOnGroupClickListener(new GroupClickListener());


    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
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
