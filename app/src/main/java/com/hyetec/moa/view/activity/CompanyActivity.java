package com.hyetec.moa.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.api.Api;
import com.hyetec.moa.model.entity.ActivityEventEntity;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.MessageEntity;
import com.hyetec.moa.utils.ShakeListener;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.view.adapter.CommonAdapter;
import com.hyetec.moa.view.adapter.ViewHolder;
import com.hyetec.moa.view.ui.MyListView;
import com.hyetec.moa.view.ui.zxing.activity.CaptureActivity;
import com.hyetec.moa.viewmodel.CompanyViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : created by Administrator
 * 版本：1.0
 * 创建日期：2019/3/8
 * 描述：
 **/
public class CompanyActivity extends BaseActivity<CompanyViewModel> {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.rly_scan)
    RelativeLayout rlyScan;
    @BindView(R.id.rly_shake)
    RelativeLayout rlyShake;
    @BindView(R.id.lv_item)
    MyListView lvItem;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.iv_shake)
    ImageView ivShake;
    @BindView(R.id.tv_avtivity_title)
    TextView tvAvtivityTitle;
    @BindView(R.id.tv_avtivity_time)
    TextView tvAvtivityTime;
    @BindView(R.id.tv_avtivity_address)
    TextView tvAvtivityAddress;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.rly_shake_money)
    LinearLayout rlyShakeMoney;

    private boolean isVibrator;  //是否正在播放音频
    private Vibrator vibrator;     //振动器对象
    private MessageEntity messageEntity;
    private int actId;
    private CommonAdapter mAdapter;
    private List<ActivityEventEntity.ImgListBean> mActivityImgList = new ArrayList<>();
    private LoginUserEntity userInfo;
    private ShakeListener mShakeListener = null;
    private AlertDialog adBuilder = null;
    private double moneyCount = 0;
    private int reqCount = 0;
    private AnimationDrawable animationDrawable;
    private boolean dialogFlag =true;
    /**
     * UI 初始化
     *
     * @param savedInstanceState Bundle
     * @return int
     */
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_activities);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CompanyViewModel.class);
        return R.layout.activity_bonus_list;
    }

    /**
     * 数据初始化
     *
     * @param savedInstanceState Bundle
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        tvTitle.setText("活动");
        ivLeft.setVisibility(View.VISIBLE);
        ivAdd.setVisibility(View.GONE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        animationDrawable = (AnimationDrawable) ivShake.getDrawable();

        if (ACache.get(this).getAsObject(MoaApp.USER_DATA) != null) {
            userInfo = (LoginUserEntity) ACache.get(this.getApplicationContext()).getAsObject(MoaApp.USER_DATA);
        }
        if (getIntent().getSerializableExtra("date") != null) {
            messageEntity = (MessageEntity) getIntent().getSerializableExtra("date");
            tvTitle.setText(messageEntity.getTitle());
            actId = messageEntity.getActId();
            mViewModel.getActivityEventDetails(actId + "").observe(this, activityEvent -> {
                if (activityEvent != null && activityEvent.isSuccess()) {
                    setData(activityEvent.getResult());
                }
            });

        }


    }

    private void setData(ActivityEventEntity activityEventEntity) {
        Glide.with(CompanyActivity.this).load(Api.IMG_URL + activityEventEntity.getBgImgUrl()).into(ivHead);
        Glide.with(CompanyActivity.this).load(Api.IMG_URL + activityEventEntity.getLogoImgUrl()).into(ivLogo);
        tvAvtivityTitle.setText(activityEventEntity.getTarget_name());
        tvAvtivityTime.setText(activityEventEntity.getJoinDate());
        tvAvtivityAddress.setText(activityEventEntity.getVenue() + "-" + activityEventEntity.getOrganiser_name());
        mActivityImgList = activityEventEntity.getImgList();
        if (mActivityImgList != null && mActivityImgList.size() > 0) {
            lvItem.setVisibility(View.VISIBLE);
        } else {
            lvItem.setVisibility(View.GONE);
        }
        lvItem.setAdapter(mAdapter = new CommonAdapter<ActivityEventEntity.ImgListBean>(
                CompanyActivity.this, mActivityImgList, R.layout.layout_ativity_img) {
            @Override
            public void convert(ViewHolder helper, ActivityEventEntity.ImgListBean item, int pos) {

                helper.setImageAttachments(R.id.iv_activity_photos, item.getUrl(), CompanyActivity.this);
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
            if (activityId.equals(messageEntity.getActId() + "")) {
                mViewModel.activitySign(userInfo.getUserId() + "", activityId).observe(this, activityEvent -> {
                    if (activityEvent != null && activityEvent.isSuccess()) {
                        Toast.makeText(this, activityEvent.getMessage(), Toast.LENGTH_SHORT).show();
                        getLotteryData(userInfo.getUserId() + "", activityId,false);
                        rlyShakeMoney.setVisibility(View.VISIBLE);
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

    private void getLotteryData(String userId, String activityId,Boolean flag) {
        mViewModel.getDrawLotteryNumber(userId, activityId).observe(this, drawLotteryEntity -> {
            if (drawLotteryEntity != null && drawLotteryEntity.isSuccess()) {
                reqCount=drawLotteryEntity.getResult().getRemainder();
                moneyCount=drawLotteryEntity.getResult().getSumAmount();
                rlyShakeMoney.setVisibility(View.VISIBLE);
                if (reqCount > 0) {
                    tvWifi.setText("摇一摇抽取大奖");
                    tvCount.setText("剩余抽奖次数 " + reqCount + "次");
                    animationDrawable.start();
                    scan();
                }else {
                    tvWifi.setText("活动抽奖次数已达到上限");
                    tvCount.setText("活动红包总计:" + moneyCount + "元");
                    animationDrawable.stop();
                    dialogFlag=false;
                }
            } else {
                rlyShakeMoney.setVisibility(View.GONE);
                if(flag) {
                    Toast.makeText(this, drawLotteryEntity.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void scan() {
        mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                if (!TimeUtil.isFastDoubleClick(1000) && (adBuilder == null || (adBuilder != null && !adBuilder.isShowing()))) {
                    mShakeListener.stop();
                    mViewModel.getDrawLottery(userInfo.getUserId() + "", actId + "").observe(CompanyActivity.this, moneyData -> {
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
                                tvWifi.setText("摇一摇抽取大奖");
                                tvCount.setText("剩余抽奖次数 " + reqCount + "次");
                                animationDrawable.start();
                                if (mShakeListener != null) {
                                    mShakeListener.start();
                                }

                            } else {
                                tvWifi.setText("活动抽奖次数已达到上限");
                                tvCount.setText("活动红包总计:" + moneyCount + "元");
                                animationDrawable.stop();
                                if (mShakeListener != null) {
                                    mShakeListener.stop();
                                }
                                dialogFlag=false;
                            }
                        } else {
                            Toast.makeText(CompanyActivity.this, moneyData.getMessage(), Toast.LENGTH_SHORT).show();
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

    /**
     * 开启手机震动
     */
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
        adBuilder = new AlertDialog.Builder(CompanyActivity.this).setTitle("中奖提示")
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

    @Override
    protected void onStart() {
        super.onStart();
        rlyShakeMoney.setVisibility(View.GONE);
        getLotteryData(userInfo.getUserId() + "", actId+"",false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

    @OnClick({R.id.rly_scan, R.id.rly_shake, R.id.iv_add, R.id.iv_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rly_scan:
                startActivityForResult(new Intent(this, CaptureActivity.class), 0);
                break;
            case R.id.rly_shake:
                getLotteryData(userInfo.getUserId() + "", actId+"",true);
                break;
            case R.id.tv_add:
                break;
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
