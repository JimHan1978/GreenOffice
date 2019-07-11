package com.hyetec.moa.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.model.entity.ActivityLotteryEntity;
import com.hyetec.moa.model.entity.BonusEntity;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.utils.ShakeListener;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.pullview.GdPullToRefreshView;
import com.hyetec.moa.viewmodel.CompanyViewModel;
import com.hyetec.moa.viewmodel.PunchCardViewModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityLotteryActivity extends BaseActivity<CompanyViewModel> implements GdPullToRefreshView.OnHeaderRefreshListener{

    @BindView (R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.lv_activity)
    MyListView lvItem;
    @BindView(R.id.gv_activity)
    GdPullToRefreshView gvActivity;
    @BindView(R.id.pic_shake)
    ImageView picShake;

    private CommonAdapter mAdapter;
    private AnimationDrawable animationDrawable;
    private double moneyCount = 0;
    private Vibrator vibrator;
    private int reqCount = 0;
    private String actId;
    private String userId;
    private ShakeListener mShakeListener = null;
    private boolean dialogFlag =true;
    private AlertDialog adBuilder = null;
    private boolean isVibrator;
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lottery);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);
        return R.layout.activity_lottery;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        actId = getIntent().getStringExtra("actid");
        userId = getIntent().getStringExtra("userid");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        tvTitle.setText("活动红包");
        ivLeft.setVisibility(View.VISIBLE);
        gvActivity.setLoadMoreEnable(false);
        gvActivity.setOnHeaderRefreshListener(this);
        gvActivity.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
        animationDrawable = (AnimationDrawable) picShake.getDrawable();
        animationDrawable.start();

    }

    private void getLotteryData(){
        gvActivity.onHeaderRefreshFinish();

        mViewModel.getDrawLotteryNumber(userId, actId).observe(this, drawLotteryEntity -> {
            if (drawLotteryEntity != null && drawLotteryEntity.isSuccess()) {
                reqCount=drawLotteryEntity.getResult().getRemainder();
                moneyCount=drawLotteryEntity.getResult().getSumAmount();
                if (reqCount > 0) {
                    animationDrawable.start();
                    scan();
                } else{
                    animationDrawable.stop();
                    dialogFlag=false;
                }
            }
        });

        mViewModel.getActivityLotteryList(actId).observe(this,activityLotteryEntities -> {
            if(activityLotteryEntities!=null){
                List<ActivityLotteryEntity> list = activityLotteryEntities;
                lvItem.setAdapter(mAdapter = new CommonAdapter<ActivityLotteryEntity>(
                        this, list, R.layout.item_bonus) {
                    @Override
                    public void convert(ViewHolder helper, ActivityLotteryEntity item, int pos) {
                        helper.setText(R.id.tv_ranking, pos+1+"");
                        helper.setText(R.id.tv_user_name, item.getUserName());
                        helper.setText(R.id.tv_amount_money, item.getSumAmount()+"元");
                        helper.setImagehttp(R.id.iv_head, item.getPhoto(),ActivityLotteryActivity.this);

                        if(pos==0){
                            helper.setImageResource(R.id.iv_crown,R.drawable.shape_ring_gold);
                            helper.setImageResource(R.id.iv_crown_type,R.drawable.ic_crown_gold);
                            helper.setViewVisibility(R.id.iv_crown,View.VISIBLE);
                            helper.setViewVisibility(R.id.iv_crown_type,View.VISIBLE);
                            helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.crown_gold));
                        }else if(pos==1){
                            helper.setImageResource(R.id.iv_crown,R.drawable.shape_ring_silver);
                            helper.setImageResource(R.id.iv_crown_type,R.drawable.ic_crown_silver);
                            helper.setViewVisibility(R.id.iv_crown,View.VISIBLE);
                            helper.setViewVisibility(R.id.iv_crown_type,View.VISIBLE);
                            helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.crown_silver));
                        }else if(pos==2){
                            helper.setImageResource(R.id.iv_crown,R.drawable.shape_ring_copper);
                            helper.setImageResource(R.id.iv_crown_type,R.drawable.ic_crown_copper);
                            helper.setViewVisibility(R.id.iv_crown,View.VISIBLE);
                            helper.setViewVisibility(R.id.iv_crown_type,View.VISIBLE);
                            helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.crown_copper));
                        }else {
                            helper.setViewVisibility(R.id.iv_crown,View.GONE);
                            helper.setViewVisibility(R.id.iv_crown_type,View.GONE);
                            helper.setTextColor(R.id.tv_ranking,getResources().getColor(R.color.text_gray));
                        }
                    }
                });
            } else{

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        getLotteryData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    @Override
    public void onHeaderRefresh(GdPullToRefreshView view) {
        getLotteryData();
    }

    private void scan() {
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                if (!TimeUtil.isFastDoubleClick(1000) && (adBuilder == null || (adBuilder != null && !adBuilder.isShowing()))) {
                    mShakeListener.stop();
                    mViewModel.getDrawLottery(userId, actId).observe(ActivityLotteryActivity.this, moneyData -> {
                        if (moneyData != null && moneyData.isSuccess()) {
                            DrawLotteryEntity drawLotteryEntity = moneyData.getResult();
                            reqCount = (int) drawLotteryEntity.getRemainder();
                            moneyCount = (double) drawLotteryEntity.getSumAmount();
                            double money = drawLotteryEntity.getWinAmount();
                            if(dialogFlag) {
                                showMoney(money, moneyCount);
                                vibratorPhone();
                            }

                            if (reqCount > 0) {
                                //tvWifi.setText("摇一摇抽取大奖");
                                //tvCount.setText("剩余抽奖次数 " + reqCount + "次");
                                animationDrawable.start();
                                if (mShakeListener != null) {
                                    mShakeListener.start();
                                }

                            } else {
                                //tvWifi.setText("活动抽奖次数已达到上限");
                                //tvCount.setText("活动红包总计:" + moneyCount + "元");
                                animationDrawable.stop();
                                if (mShakeListener != null) {
                                    mShakeListener.stop();
                                }
                                dialogFlag=false;
                            }
                        } else {
                            System.out.println(moneyData.getMessage());
                            Toast.makeText(ActivityLotteryActivity.this, moneyData.getMessage(), Toast.LENGTH_SHORT).show();
                            if (reqCount > 0) {
                                if (mShakeListener != null) {
                                    mShakeListener.start();
                                }
                            }else {
                                if (mShakeListener != null) {
                                    mShakeListener.stop();
                                }
                                dialogFlag=false;
                            }
                        }
                    });
                }
            }
        });
    }

    private void vibratorPhone() {
        if (!isVibrator) {
            isVibrator = true;
            vibrator.vibrate(300);  //振动时长300ms

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 400);        //延时时间根据振动时长决定
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (null != msg) {
                switch (msg.what) {
                    case 0:
                        isVibrator = false;
                        break;
                    default:
                        break;
                }
            }
        }
    };

    public void showMoney(double count, double sum) {
        adBuilder = new AlertDialog.Builder(ActivityLotteryActivity.this).setTitle("中奖提示")
                .setMessage(count != 0 ? "恭喜你摇出" + count + "元,总计" + sum + "元" : "本次未中奖,请继续努力").
                        setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();


        adBuilder.setCanceledOnTouchOutside(false);
        adBuilder.show();
    }
}
