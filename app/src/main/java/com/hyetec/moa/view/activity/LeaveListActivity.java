package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.HaveDoneLeaveEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MyLeaveEntity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.pullview.GdPullToRefreshView;
import com.hyetec.moa.viewmodel.LeaveViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeaveListActivity extends BaseActivity<LeaveViewModel> implements GdPullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_activity)
    MyListView lvActivity;
    @BindView(R.id.gv_activity)
    GdPullToRefreshView gvActivity;
    @BindView(R.id.list1)
    LinearLayout list1;
    @BindView(R.id.list2)
    LinearLayout list2;
    @BindView(R.id.list3)
    LinearLayout list3;


    private LoginUserEntity userInfo;
    private int listType = 1;
    private boolean commit = false;


    private CommonAdapter mAdapter;
    private List<MyLeaveEntity> myLeaveList;
    private List<HaveDoneLeaveEntity> doneLeaveList;
    private List<HaveDoneLeaveEntity> unfinishLeaveList;



    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.leave_application_list);
        ButterKnife.bind(this);

        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LeaveViewModel.class);
        return R.layout.activity_company_list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("请假列表");
        ivLeft.setVisibility(View.VISIBLE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.drawable.ic_add);
        gvActivity.setLoadMoreEnable(false);
        gvActivity.setOnHeaderRefreshListener(this);
        gvActivity.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        if (ACache.get(this).getAsObject(MoaApp.USER_DATA) != null) {
            userInfo = (LoginUserEntity) ACache.get(this.getApplicationContext()).getAsObject(MoaApp.USER_DATA);
        }
        lvActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LeaveListActivity.this, NewLeaveActivity.class);
                if (listType == 1){
                    intent.putExtra("id", String.valueOf(myLeaveList.get(position).getId()));
                }
                else if(listType == 2){
                    intent.putExtra("id", String.valueOf(unfinishLeaveList.get(position).getBussinesId()));
                    intent.putExtra("commitId",String.valueOf(unfinishLeaveList.get(position).getId()));
                }
                else{
                    intent.putExtra("id", String.valueOf(doneLeaveList.get(position).getBussinesId()));
                }
                intent.putExtra("intent","checkDetail");
                intent.putExtra("commit",commit);
                startActivity(intent);
            }
        });

        getMyLeaveListData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    private void getDoneLeaveListData(){
        commit = false;

        mViewModel.getDoneLeaveList("desc", "10", "1", "start_time").observe(this, myLeaveEventList -> {
            gvActivity.onHeaderRefreshFinish();
            if (myLeaveEventList != null && myLeaveEventList.isSuccess()) {
                doneLeaveList = myLeaveEventList.getResult();
                lvActivity.setAdapter(mAdapter = new CommonAdapter<HaveDoneLeaveEntity>(
                        getApplicationContext(), myLeaveEventList.getResult(), R.layout.leave_item_message) {
                    @Override
                    public void convert(ViewHolder helper, HaveDoneLeaveEntity item, int pos) {
                        helper.setText(R.id.tv_personal_message, item.getAssignee());
                        helper.setText(R.id.tv_message_name, item.getBussinesName());
//                        helper.setText(R.id.ap_status, "("+item.getStatusName()+")");
                        helper.setImageResource(R.id.iv_message, R.drawable.leave_item);
                        helper.setText(R.id.tv_message_content, item.getStartTime());
                    }
                });
            }
        });
    }

    private void getUnfinishLeaveListData(){
        commit = true;

        mViewModel.getUnfinishLeaveList("desc", "10", "1", "start_time").observe(this, myLeaveEventList -> {
            gvActivity.onHeaderRefreshFinish();
            if (myLeaveEventList != null && myLeaveEventList.isSuccess()) {
                unfinishLeaveList = myLeaveEventList.getResult();
                lvActivity.setAdapter(mAdapter = new CommonAdapter<HaveDoneLeaveEntity>(
                        getApplicationContext(), myLeaveEventList.getResult(), R.layout.leave_item_message) {
                    @Override
                    public void convert(ViewHolder helper, HaveDoneLeaveEntity item, int pos) {
                        helper.setText(R.id.tv_personal_message, item.getAssignee());
                        helper.setText(R.id.tv_message_name, item.getBussinesName());
//                        helper.setText(R.id.ap_status, "("+item.getStatusName()+")");
                        helper.setImageResource(R.id.iv_message, R.drawable.leave_item);
                        helper.setText(R.id.tv_message_content, item.getStartTime());
                    }
                });
            }
        });
    }


    private void getMyLeaveListData(){
        commit = false;

        mViewModel.getMyLeaveList("start_date","desc","10","1").observe(this,myLeaveEventList -> {
            gvActivity.onHeaderRefreshFinish();
            if (myLeaveEventList != null && myLeaveEventList.isSuccess()) {
                myLeaveList = myLeaveEventList.getResult();
                lvActivity.setAdapter(mAdapter = new CommonAdapter<MyLeaveEntity>(
                        getApplicationContext(), myLeaveEventList.getResult(), R.layout.leave_item_message) {
                    @Override
                    public void convert(ViewHolder helper, MyLeaveEntity item, int pos) {
                        helper.setText(R.id.tv_personal_message, item.getApplyUserName()+"   "+item.getApplyDeptName());
                        helper.setText(R.id.tv_leave_type, item.getTypeName());
                        helper.setText(R.id.tv_message_name, "请假");
                        helper.setText(R.id.ap_status, "("+item.getStatusName()+")");
                        helper.setImageResource(R.id.iv_message, R.drawable.leave_item);
                        helper.setText(R.id.tv_message_content, item.getStartDate()+" 到 "+item.getEndDate() +" 共计"+item.getValidHoliday()+"天");
                    }
                });
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onHeaderRefresh(GdPullToRefreshView view) {
        if (listType == 1){
            getMyLeaveListData();
        }
        else if (listType == 2){
            getUnfinishLeaveListData();
        }
        else{
            getDoneLeaveListData();
        }
             }

    @OnClick({R.id.iv_left, R.id.tv_title, R.id.iv_right, R.id.list1, R.id.list2, R.id.list3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                Intent intent = new Intent(this, NewLeaveActivity.class);
                intent.putExtra("intent", "create");
                startActivity(intent);
                break;
            case R.id.list1:
                listType = 1;
                ((ImageView)list1.findViewById(R.id.list_1_picture)).setImageResource(R.drawable.myapplication1);
                ((ImageView)list2.findViewById(R.id.list_2_picture)).setImageResource(R.drawable.unfinish2);
                ((ImageView)list3.findViewById(R.id.list_3_picture)).setImageResource(R.drawable.have_done2);
                getMyLeaveListData();
                break;
            case R.id.list2:
                listType = 2;
                ((ImageView)list1.findViewById(R.id.list_1_picture)).setImageResource(R.drawable.myapplication2);
                ((ImageView)list2.findViewById(R.id.list_2_picture)).setImageResource(R.drawable.unfinish1);
                ((ImageView)list3.findViewById(R.id.list_3_picture)).setImageResource(R.drawable.have_done2);
                getUnfinishLeaveListData();
                break;
            case R.id.list3:
                listType = 3;
                ((ImageView)list1.findViewById(R.id.list_1_picture)).setImageResource(R.drawable.myapplication2);
                ((ImageView)list2.findViewById(R.id.list_2_picture)).setImageResource(R.drawable.unfinish2);
                ((ImageView)list3.findViewById(R.id.list_3_picture)).setImageResource(R.drawable.have_done1);
                getDoneLeaveListData();
                break;
        }
    }

}
