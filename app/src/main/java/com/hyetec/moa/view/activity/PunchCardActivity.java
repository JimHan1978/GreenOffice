package com.hyetec.moa.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.DrawLotteryEntity;
import com.hyetec.moa.model.entity.LoginUserEntity;
import com.hyetec.moa.model.entity.PunchCardEntity;
import com.hyetec.moa.model.entity.TodayMoneyEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.utils.Connected;
import com.hyetec.moa.utils.GetCode;
import com.hyetec.moa.utils.MaxLengthWatcher;
import com.hyetec.moa.utils.ShakeListener;
import com.hyetec.moa.utils.TimeUtil;
import com.hyetec.moa.utils.WifiUtil;
import com.hyetec.moa.viewmodel.PunchCardViewModel;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : created by jimhan
 * 版本：1.0
 * 创建日期：2019/3/11
 * 描述：设置页面
 **/

public class PunchCardActivity extends BaseActivity<PunchCardViewModel> {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_shang)
    ImageView ivShang;
    @BindView(R.id.tv_zao_state)
    TextView tvZaoState;
    @BindView(R.id.tv_zao_time)
    TextView tvZaoTime;
    @BindView(R.id.iv_xia)
    ImageView ivXia;
    @BindView(R.id.tv_wan_state)
    TextView tvWanState;
    @BindView(R.id.tv_wan_time)
    TextView tvWanTime;
    @BindView(R.id.iv_daka)
    ImageView ivDaka;
    @BindView(R.id.tv_wifi)
    TextView tvWifi;
    @BindView(R.id.tv_good)
    TextView tvGood;
    @BindView(R.id.iv_shake)
    ImageView ivShake;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.rly_shake)
    RelativeLayout rlyShake;

    private Field field;
    private WifiUtil wifi;
    private String strBSSID = "";
    private String strIMEI = "";
    private String[] str_ = new String[]{"迟到/早退", "外出", "活动"};
    public AlertDialog ad = null;
    private int str_code = 1;// 原因编码
    private String nowData;
    private LoginUserEntity userEntity;
    private String userId;
    private String str_chi_state;
    private String str_tui_state;
    private String str_chi_reason;
    private String str_tui_reason;
    private String str_c_z;
    private String bssIds;
    private Broadcast broadcast;
    private boolean isNetworkValid;

    private double moneyCount = 0;
    private int reqCount = 0;
    private String toWorktype;

    private AnimationDrawable animationDrawable;
    private ShakeListener mShakeListener = null;
    private boolean isVibrator;  //是否正在播放音频
    private Vibrator vibrator;     //振动器对象
    private AlertDialog adBuilder = null;
    private boolean dialogFlag =true;
    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_punch_card);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PunchCardViewModel.class);
        return R.layout.activity_punch_card;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        AssetManager mgr = getAssets();// 得到AssetManager
        Typeface tf = Typeface.createFromAsset(mgr, "fonts/simkai.ttf");// 根据路径得到Typeface
        tvGood.setTypeface(tf);// 设置字体
        tvTitle.setText("打卡");
        tvAdd.setText("红包排行");
        nowData = TimeUtil
                .clearSecond(TimeUtil.getSQLDateTimeString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        animationDrawable = (AnimationDrawable) ivShake.getDrawable();
        animationDrawable.start();

        if (ACache.get(getApplicationContext()).getAsObject(MoaApp.USER_DATA) != null) {
            userEntity = (LoginUserEntity) ACache.get(getApplicationContext()).getAsObject(MoaApp.USER_DATA);
            userId = userEntity.getUserId() + "";
        }

//        if (Connected.isConnected(this)) {
//            getInfo();
//        } else {
//            Toast.makeText(this, "请连接网络", Toast.LENGTH_SHORT).show();
//        }
        getBssID();

//        if (ACache.get(getApplicationContext()).getAsString(MoaApp.BSSIDS) != null) {
//            bssIds = ACache.get(getApplicationContext()).getAsString(MoaApp.BSSIDS);
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
//            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//            broadcast = new Broadcast();
//            registerReceiver(broadcast, filter);
//        } else {
//            getBssID();
//        }


    }

    public void showMoney(double count, double sum) {
        adBuilder = new AlertDialog.Builder(PunchCardActivity.this).setTitle("中奖提示")
                .setMessage(count != 0 ? "恭喜你摇出" + count + "元,今日总计" + sum + "元" : "本次未中奖,请继续努力").
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        rlyShake.setVisibility(View.GONE);

        if (Connected.isConnected(this)) {
            getInfo();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mShakeListener != null) {
            mShakeListener.stop();

        }

    }


    class Broadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // 判断wifi是打开还是关闭
            // if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) { //
            // 此处无实际作用，只是看开关是否开启
            wifi = new WifiUtil(PunchCardActivity.this);
            strIMEI = wifi.getIMEI();
            strBSSID = wifi.getBSSID();
            updatePunchCardView();

        }
    }

    public void showBssid(String bssid) {
        if (TextUtils.isEmpty(bssid)) {
            bssid = "请连接wifi并在通知栏中打开定位服务!";
        }else {
            bssid = bssid+"此Id未加入列表,请通知工作人员加入!";
        }
        if(adBuilder==null||(adBuilder!=null&&!adBuilder.isShowing())) {
            adBuilder = new AlertDialog.Builder(PunchCardActivity.this).setTitle("提示")
                    .setMessage(bssid).
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

    private void updatePunchCardView() {
        // showBssid(strBSSID);

        if (isWifi(this) && strBSSID != null && checkWiFiBssId(strBSSID)) {
            ivDaka.setEnabled(true);
            ivDaka.setBackgroundResource(R.drawable.select_daka);

        } else {
            ivDaka.setBackgroundResource(R.drawable.daka);
            ivDaka.setEnabled(false);

        }
        if (reqCount > 0) {
            tvWifi.setText("摇一摇抽取今日大奖");
            tvCount.setText("剩余抽奖次数 " + reqCount + "次");
            ivShake.setVisibility(View.VISIBLE);
            if (mShakeListener != null) {
                mShakeListener.start();
            }
        } else {
            tvWifi.setText("今日抽奖次数已达到上限");
            tvCount.setText("今日红包总计:" + moneyCount + "元");
            ivShake.setVisibility(View.GONE);
            if (mShakeListener != null) {
                mShakeListener.stop();
            }
        }
    }

    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    private boolean checkWiFiBssId(String bssId) {
        if (!TextUtils.isEmpty(bssId)) {
            if (bssId.startsWith("02:00:00")) {
                showBssid("");
                return false;
            } else if (bssIds.contains(bssId.substring(0, bssId.length() - 3))) {
                return true;
            }else {
                showBssid(bssId);
                return false;
            }
            // return bssId.startsWith("02:00:00") || bssIds.contains(bssId.substring(0, bssId.length() - 3));
        }
        return false;
    }


    @OnClick({R.id.iv_left, R.id.iv_daka, R.id.tv_add, R.id.rly_shake,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_daka:
                daKa();
                break;
            case R.id.tv_add:
                startActivity(new Intent(PunchCardActivity.this, BonusListActivity.class).putExtra("type","Month"));
                break;
            case R.id.rly_shake:
                if (reqCount <=0) {
                    getTodayMoneyList();
                }

                break;
        }
    }

    private void getTodayMoneyList() {
        mViewModel.getTodayMoneyList(userId).observe(this, todayMoneyList -> {
            if (todayMoneyList != null && todayMoneyList.isSuccess()) {
                showMoneyList(todayMoneyList.getResult());
            } else {
                Toast.makeText(PunchCardActivity.this, todayMoneyList.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void showMoneyList(List<TodayMoneyEntity> todayMoneyEntities) {
        String[] items = new String[todayMoneyEntities.size()];
        for (int i = 0; i < todayMoneyEntities.size(); i++) {
            items[i] = "第" + (i + 1) + "次     " + todayMoneyEntities.get(i).getWinAmount() + "元";
        }

        AlertDialog alertDialog3 = new AlertDialog.Builder(this)
                .setTitle("今日红包详情")
                .setItems(items, new DialogInterface.OnClickListener() {//添加列表
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
//                .setAdapter(new ArrayAdapter<String>(this, R.layout.layout_dialog_item, R.id.tv_name, items), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog3.show();

    }


    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (ad.isShowing()) {
                    if (getWindow()
                            .getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
                    } else {
                        Toast.makeText(PunchCardActivity.this, "请说明原因", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }
            return false;
        }
    };

    private void dialog() {
        View v = LayoutInflater.from(PunchCardActivity.this).inflate(R.layout.layout_daka_dialog, null);

        final EditText et_why = (EditText) v.findViewById(R.id.et_why);
        final RadioGroup rg_ = (RadioGroup) v.findViewById(R.id.rg_);
        final RadioButton rb_chi_tui = (RadioButton) v.findViewById(R.id.rb_chi_tui);
        final RadioButton rb_waichu = (RadioButton) v.findViewById(R.id.rb_waichu);
        final RadioButton rb_huodong = (RadioButton) v.findViewById(R.id.rb_huodong);
        if (str_c_z.equals("c")) {
            rb_chi_tui.setText("迟到");
        } else {
            rb_chi_tui.setText("早退");
        }
        et_why.addTextChangedListener(new MaxLengthWatcher(PunchCardActivity.this, 30, et_why));
        rg_.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                str_code = group.indexOfChild(group.findViewById(checkedId)) + 1;
            }
        });
        ad = new AlertDialog.Builder(PunchCardActivity.this).setTitle("请说明原因").setOnKeyListener(keylistener)
                .setView(v).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        try {
                            field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
                            field.setAccessible(true);
                            field.set(dialog, false);
                            // str_code = spinner.getSelectedItemPosition() + 1 + "";
                            if (str_code == 1) {
                                field.set(dialog, true);
                                reason(et_why.getText().toString(), str_code);
                            } else {
                                if (!TextUtils.isEmpty(et_why.getText().toString())) {
                                    field.set(dialog, true);
                                    reason(et_why.getText().toString(), str_code);
                                } else {
                                    Toast.makeText(PunchCardActivity.this, "请说明理由", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (NoSuchFieldException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IllegalArgumentException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }).create();
        ad.setCanceledOnTouchOutside(false);
        ad.show();
    }

    private void daKa() {
        mViewModel.daKa(userId, strIMEI).observe(this, dakaData -> {
            if (dakaData != null && dakaData.isSuccess()) {
                setDakaData(dakaData.getResult());
            } else {
                Toast.makeText(PunchCardActivity.this, dakaData.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setDakaData(PunchCardEntity punchCardEntity) {
        if (!punchCardEntity.getToworktype().equals("4")) {
            str_c_z = "c";

        }
        if (!punchCardEntity.getOffworktype().equals("4")) {
            str_c_z = "z";
        }
        setData(punchCardEntity);
        if (str_c_z.equals("c")) {
            if (!punchCardEntity.getToworktype().equals("0")
                    && !punchCardEntity.getToworktype().equals("4")) {
                dialog();
            }
        }
        if (str_c_z.equals("z")) {
            if (!punchCardEntity.getOffworktype().equals("0")
                    && !punchCardEntity.getOffworktype().equals("4")) {
                dialog();
            }
        }
    }

    private void getBssID() {
        mViewModel.getBssid().observe(this, bssidData -> {
            if (bssidData != null && bssidData.isSuccess()) {
                bssIds = bssidData.getResult().getApBssIds();
                ACache.get(PunchCardActivity.this).put(MoaApp.BSSIDS, bssIds);
                IntentFilter filter = new IntentFilter();
                filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
                filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                broadcast = new Broadcast();
                registerReceiver(broadcast, filter);
            } else {
                Toast.makeText(PunchCardActivity.this, bssidData.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }


    private void getInfo() {
        mViewModel.getInfo(userId, nowData).observe(this, punchCardData -> {
            if (punchCardData != null && punchCardData.isSuccess()) {
                setData(punchCardData.getResult());
            } else {
                Toast.makeText(PunchCardActivity.this, punchCardData.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setData(PunchCardEntity punchCardEntity) {
        tvZaoTime.setText(TimeUtil.getTime(punchCardEntity.getToworktime()));
        tvZaoState.setText(GetCode.chiOrTuiState(punchCardEntity.getToworktype(), true));
        reqCount = punchCardEntity.getRemainder();
        moneyCount = punchCardEntity.getSumAmount();
        toWorktype = punchCardEntity.getToworktype();
        if (!punchCardEntity.getToworktype().equals("9")) {
            if (punchCardEntity.getToworktype().equals("1")
                    || punchCardEntity.getToworktype().equals("4")) {
                tvZaoState.setBackgroundResource(R.drawable.shape_wan);
                tvZaoTime.setTextColor(getResources().getColor(R.color.punch_card_red));

                rlyShake.setVisibility(View.GONE);
                if (mShakeListener != null) {
                    mShakeListener.stop();
                }

            } else {
                tvZaoState.setBackgroundResource(R.drawable.shape_zao);
                tvZaoTime.setTextColor(getResources().getColor(R.color.punch_card_green));
            }
        }
        str_chi_state = GetCode.chiOrTuiState(punchCardEntity.getToworktype(), true);
        str_chi_reason = punchCardEntity.getToworkremark();

        tvWanTime.setText(TimeUtil.getTime(punchCardEntity.getOffworktime()));
        tvWanState.setText(
                GetCode.chiOrTuiState(punchCardEntity.getOffworktype(), false));
        if (!punchCardEntity.getOffworktype().equals("9")) {
            if (punchCardEntity.getOffworktype().equals("1")
                    || punchCardEntity.getOffworktype().equals("4")) {
                tvWanState.setBackgroundResource(R.drawable.shape_wan);
                tvWanTime.setTextColor(getResources().getColor(R.color.punch_card_red));
            } else {
                tvWanState.setBackgroundResource(R.drawable.shape_zao);
                tvWanTime.setTextColor(getResources().getColor(R.color.punch_card_green));
            }
        }
        str_tui_state = GetCode.chiOrTuiState(punchCardEntity.getOffworktype(),
                false);
        str_tui_reason = punchCardEntity.getOffworkremark();

        if (reqCount > 0 && !punchCardEntity.getToworktype().equals("1")) {
            tvWifi.setText("摇一摇抽取今日大奖");
            tvCount.setText("剩余抽奖次数 " + reqCount + "次");
            rlyShake.setVisibility(View.VISIBLE);
            ivShake.setVisibility(View.VISIBLE);
            mShakeListener = new ShakeListener(this);
            mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
                @Override
                public void onShake() {
                    if (!TimeUtil.isFastDoubleClick(1000) && (adBuilder == null || (adBuilder != null && !adBuilder.isShowing()))) {
                        if (mShakeListener != null) {
                            mShakeListener.stop();
                        }
                        mViewModel.getDrawLottery(userId).observe(PunchCardActivity.this, moneyData -> {
                            if (moneyData != null && moneyData.isSuccess()) {
                                DrawLotteryEntity drawLotteryEntity = moneyData.getResult();
                                reqCount = (int) drawLotteryEntity.getRemainder();
                                moneyCount = (double) drawLotteryEntity.getSumAmount();
                                double money = drawLotteryEntity.getWinAmount();
                                if(dialogFlag) {
                                    showMoney(money, moneyCount);
                                    vibratorPhone();
                                }

                                if (reqCount > 0 ) {
                                    tvWifi.setText("摇一摇抽取今日大奖");
                                    tvCount.setText("剩余抽奖次数 " + reqCount + "次");
                                    ivShake.setVisibility(View.VISIBLE);
                                    if (mShakeListener != null) {
                                        mShakeListener.start();
                                    }
                                } else {
                                    tvWifi.setText("今日抽奖次数已达到上限");
                                    tvCount.setText("今日红包总计:" + moneyCount + "元");
                                    ivShake.setVisibility(View.GONE);
                                    if (mShakeListener != null) {
                                        mShakeListener.stop();
                                    }
                                    dialogFlag=false;
                                }

                            } else {
                                Toast.makeText(PunchCardActivity.this, moneyData.getMessage(), Toast.LENGTH_SHORT).show();
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
        } else if (TextUtils.isEmpty(punchCardEntity.getToworktype()) || !punchCardEntity.getToworktype().equals("0")) {
            rlyShake.setVisibility(View.GONE);
            ivShake.setVisibility(View.GONE);
            if (mShakeListener != null) {
                mShakeListener.stop();
            }
        } else {
            rlyShake.setVisibility(View.VISIBLE);
            ivShake.setVisibility(View.GONE);
            tvWifi.setText("今日抽奖次数已达到上限");
            tvCount.setText("今日红包总计:" + moneyCount + "元");
            if (mShakeListener != null) {
                mShakeListener.stop();
            }
            dialogFlag=false;
        }
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

    private void reason(String reason, int str_code) {

        mViewModel.submitReason(userId, nowData, reason, str_code, str_c_z).observe(this, punchCardData -> {
            if (punchCardData != null && punchCardData.isSuccess()) {
                setData(punchCardData.getResult());
            } else {
                Toast.makeText(PunchCardActivity.this, punchCardData.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
