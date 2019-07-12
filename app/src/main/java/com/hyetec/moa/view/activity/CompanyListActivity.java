package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.ActivityPhotoEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.pullview.GdPullToRefreshView;
import com.hyetec.moa.viewmodel.CompanyViewModel;
import com.hyetec.moa.viewmodel.PunchCardViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class CompanyListActivity extends BaseActivity<CompanyViewModel> implements GdPullToRefreshView.OnHeaderRefreshListener {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rly_title)
    RelativeLayout rlyTitle;
    @BindView(R.id.lv_activity)
    MyListView lvActivity;
    @BindView(R.id.gv_activity)
    GdPullToRefreshView gvActivity;
    @BindView(R.id.mPullRefreshView)
    LinearLayout mPullRefreshView;

    private CommonAdapter mAdapter;
    private List<ActivityPhotoEntity> mActivityImgList = new ArrayList<>();
    private List<MessageEntity> messageList;

    /**
     * UI 初始化
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);
        return R.layout.activity_company_list;
    }

    /**
     * 数据初始化
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("活动列表");
        ivLeft.setVisibility(View.VISIBLE);
        //ivRight.setVisibility(View.VISIBLE);
        gvActivity.setLoadMoreEnable(false);
        gvActivity.setOnHeaderRefreshListener(this);
        gvActivity.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));

        lvActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(CompanyListActivity.this, CompanyActivity.class).putExtra("date", messageList.get(i)));
            }
        });
        getListData();
    }

    private void getListData() {
        mViewModel.getActivityEventList().observe(this, activityEventList -> {
            gvActivity.onHeaderRefreshFinish();
            if (activityEventList != null && activityEventList.isSuccess()) {
                messageList=activityEventList.getResult();
                lvActivity.setAdapter(mAdapter = new CommonAdapter<MessageEntity>(
                        getApplicationContext(), activityEventList.getResult(), R.layout.item_message) {
                    @Override
                    public void convert(ViewHolder helper, MessageEntity item, int pos) {
                        helper.setText(R.id.tv_message_name, item.getTitle());
                        helper.setText(R.id.tv_message_content, item.getSubTitle());
                        helper.setImagehttpMessage(R.id.iv_message, item.getIconUrl(), CompanyListActivity.this);
                        helper.setViewVisibility(R.id.iv_message_new, View.GONE);
                    }
                });
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @OnClick({R.id.iv_left, R.id.tv_title, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            /*case R.id.iv_right:
                Intent intent = new Intent(CompanyListActivity.this,CreateActivity.class);
                startActivity(intent);*/
        }
    }

    @Override
    public void onHeaderRefresh(GdPullToRefreshView view) {
        getListData();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
