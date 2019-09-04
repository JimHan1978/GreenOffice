package com.hyetec.moa.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.ActivityEndEntity;
import com.hyetec.moa.model.entity.ActivitySignEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.pullview.GdPullToRefreshView;
import com.hyetec.moa.view.ui.zxing.activity.CaptureActivity;
import com.hyetec.moa.viewmodel.CompanyViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivitySignActivity extends BaseActivity<CompanyViewModel> implements GdPullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.end_activity)
    TextView endActivity;
    @BindView(R.id.rly_title)
    RelativeLayout rlyTitle;
    @BindView(R.id.lv_activity)
    MyListView lvActivity;
    @BindView(R.id.gv_activity)
    GdPullToRefreshView gvActivity;
    @BindView(R.id.mPullRefreshView)
    LinearLayout mPullRefreshView;

    private CommonAdapter mAdapter;
    private AnimationDrawable animationDrawable;
    private MessageEntity messageEntity;
    private String actId;
    private String userId;
    private String organiser="";

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);
        return R.layout.activity_company_list;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("扫一扫签到");
        ivLeft.setVisibility(View.VISIBLE);
        actId = getIntent().getStringExtra("actId");
        userId = getIntent().getStringExtra("userId");
        organiser = getIntent().getStringExtra("org");
       // messageEntity = (MessageEntity)getIntent().getSerializableExtra("message");

        if(organiser.equals(userId)){
            ivRight.setVisibility(View.GONE);
            endActivity.setVisibility(View.VISIBLE);
        }
        else{
            endActivity.setVisibility(View.GONE);
            ivRight.setVisibility(View.VISIBLE);
        }
        gvActivity.setLoadMoreEnable(false);
        gvActivity.setOnHeaderRefreshListener(this);
        gvActivity.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
       // animationDrawable = (AnimationDrawable) ivShake.getDrawable();
    }

    private void getActivitySignData() {
        gvActivity.onHeaderRefreshFinish();
        /*String actId = getIntent().getStringExtra("data");
        String userId = getIntent().getStringExtra("userId");*/
        if (userId != null && actId != null){
            mViewModel.getActivitySignList(actId).observe(this, activitySignEntities -> {
                List<ActivitySignEntity> list = activitySignEntities;
                if (list.size()!=0) {
                    lvActivity.setAdapter(mAdapter = new CommonAdapter<ActivitySignEntity>(this, list, R.layout.item_sign) {
                        @Override
                        public void convert(ViewHolder helper, ActivitySignEntity item, int position) {
                            helper.setText(R.id.sign_id, position + 1 + "");
                            helper.setText(R.id.sign_name, item.getUserName());
                            helper.setText(R.id.sign_time, item.getCreateDate());
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        getActivitySignData();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            activitySign(scanResult);

        }
    }

    private void activitySign(String scanResult) {
        String code = new String(Base64.decode(scanResult.getBytes(), Base64.DEFAULT));
        try {
            JSONObject jsonObject = new JSONObject(code);
            String activityId = jsonObject.getString("actId");
            if (activityId.equals(actId + "")) {
                mViewModel.activitySign(userId, activityId).observe(this, activityEvent -> {
                    if (activityEvent != null && activityEvent.isSuccess()) {
                        Toast.makeText(this, activityEvent.getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, activityEvent.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "请打开正确的活动扫码签到!", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void endActivity(String id){
        mViewModel.endActivity(actId).observe(this,endAct->{
            Toast.makeText(this,endAct.getMessage(),Toast.LENGTH_SHORT).show();
        });
    }

    @OnClick({R.id.iv_left,R.id.iv_right,R.id.end_activity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right:
                startActivityForResult(new Intent(this, CaptureActivity.class), 0);
                break;
            case R.id.end_activity:
                endActivity(actId);
        }
    }


    public void onHeaderRefresh(GdPullToRefreshView view) {
        getActivitySignData();
    }
}
