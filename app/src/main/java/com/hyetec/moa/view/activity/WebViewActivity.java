/**
 * @Title: OnlineCourseDetails.java
 * @Package com.hyetec.greentraining.activity
 * @Description:
 * @author zzn
 * @date 2016年6月22日 下午3:04:25
 * @version V1.0
 */
package com.hyetec.moa.view.activity;


import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.BillEntity;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.viewmodel.WebViewModel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebViewActivity extends BaseActivity<WebViewModel> {

    @BindView(R.id.tv_title)
    TextView mTitleView;
    @BindView(R.id.wv_item)
    WebView wv_item;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    private String url;
    private String billDate;

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WebViewModel.class);
        return R.layout.activity_webview;
    }


    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        WebSettings webSettings = wv_item.getSettings();
        if (getIntent().getStringExtra("date") != null) {
            billDate = getIntent().getStringExtra("date");
        } else {
            billDate = getTime();
        }
        ivLeft.setVisibility(View.VISIBLE);

        mTitleView.setText(getYearAndMonth() + "月度报告");
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        String json = getFromAssets("data/report.json");

        UserEntity user = (UserEntity) ACache.get(this).getAsObject(MoaApp.USER_DATA);
        int sex = user.getSex();
        String joindate = user.getJoindate();

        wv_item.loadUrl("file:///android_asset/month-bill.html");

        wv_item.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//当页面加载完成
                super.onPageFinished(view, url);

                mViewModel.getData(billDate).observe(WebViewActivity.this, billData -> {
                    if (billData != null && billData.isSuccess()) {
                        Gson gson = new Gson();

                        wv_item.evaluateJavascript("javascript:setData('" + gson.toJson(getBase64Str(billData.getResult())) + "'," + sex + ",'" + joindate + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                value.toString();

                            }
                        });
                    }
                });
            }

        });


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        wv_item.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(WebViewActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });


    }

    private String getYearAndMonth() {
        String yearAndMonth = billDate;
        yearAndMonth = yearAndMonth.replace("-0", "年");
        yearAndMonth = yearAndMonth.replace("-", "年");
        yearAndMonth += "月";
        return yearAndMonth;

    }
    private BillEntity getBase64Str( BillEntity billEntity){

        BillEntity billEntity1=new BillEntity();

        if(billEntity.getDetail()!=null){
            BillEntity.DetailBean detailBean=billEntity.getDetail();
            BillEntity.DetailBean newDetailBean=new BillEntity.DetailBean();
            if(!TextUtils.isEmpty(detailBean.getBeatBonusIncomePercent())){
                newDetailBean.setBeatBonusIncomePercent(new String(Base64.decode(detailBean.getBeatBonusIncomePercent().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setBeatBonusIncomePercent("");
            }
            if(!TextUtils.isEmpty(detailBean.getBeatQtjlsrPercent())){
                newDetailBean.setBeatQtjlsrPercent(new String(Base64.decode(detailBean.getBeatQtjlsrPercent().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setBeatQtjlsrPercent("");
            }
            if(!TextUtils.isEmpty(detailBean.getBeatXjflsrPercent())){
                newDetailBean.setBeatXjflsrPercent(new String(Base64.decode(detailBean.getBeatXjflsrPercent().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setBeatXjflsrPercent("");
            }
            if(!TextUtils.isEmpty(detailBean.getDate())){
                newDetailBean.setDate(new String(Base64.decode(detailBean.getDate().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setDate("");
            }
            if(!TextUtils.isEmpty(detailBean.getTxbt())){
                newDetailBean.setTxbt(new String(Base64.decode(detailBean.getTxbt().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setTxbt("");
            }
            if(!TextUtils.isEmpty(detailBean.getBysrhj())){
                newDetailBean.setBysrhj(new String(Base64.decode(detailBean.getBysrhj().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setBysrhj("");
            }
            if(!TextUtils.isEmpty(detailBean.getSjkk())){
                newDetailBean.setSjkk(new String(Base64.decode(detailBean.getSjkk().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setSjkk("");
            }
            if(!TextUtils.isEmpty(detailBean.getBjkk())){
                newDetailBean.setBjkk(new String(Base64.decode(detailBean.getBjkk().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setBjkk("");
            }
            if(!TextUtils.isEmpty(detailBean.getUserno())){
                newDetailBean.setUserno(new String(Base64.decode(detailBean.getUserno().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setUserno("");
            }
            if(!TextUtils.isEmpty(detailBean.getGs())){
                newDetailBean.setGs(new String(Base64.decode(detailBean.getGs().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setGs("");
            }
            if(!TextUtils.isEmpty(detailBean.getQtjlsr())){
                newDetailBean.setQtjlsr(new String(Base64.decode(detailBean.getQtjlsr().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setQtjlsr("");
            }
            if(!TextUtils.isEmpty(detailBean.getJjsr())){
                newDetailBean.setJjsr(new String(Base64.decode(detailBean.getJjsr().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setJjsr("");
            }
            if(!TextUtils.isEmpty(detailBean.getCdkk())){
                newDetailBean.setCdkk(new String(Base64.decode(detailBean.getCdkk().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setCdkk("");
            }
            if(!TextUtils.isEmpty(detailBean.getDnbt())){
                newDetailBean.setDnbt(new String(Base64.decode(detailBean.getDnbt().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setDnbt("");
            }
            if(!TextUtils.isEmpty(detailBean.getSqgzhj())){
                newDetailBean.setSqgzhj(new String(Base64.decode(detailBean.getSqgzhj().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setSqgzhj("");
            }
            if(!TextUtils.isEmpty(detailBean.getGjjlmksr())){
                newDetailBean.setGjjlmksr(new String(Base64.decode(detailBean.getGjjlmksr().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setGjjlmksr("");
            }
            if(!TextUtils.isEmpty(detailBean.getJxjc())){
                newDetailBean.setJxjc(new String(Base64.decode(detailBean.getJxjc().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setJxjc("");
            }
            if(!TextUtils.isEmpty(detailBean.getName())){
                newDetailBean.setName(new String(Base64.decode(detailBean.getName().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setName("");
            }
            if(!TextUtils.isEmpty(detailBean.getDksbgjj())){
                newDetailBean.setDksbgjj(new String(Base64.decode(detailBean.getDksbgjj().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setDksbgjj("");
            }
            if(!TextUtils.isEmpty(detailBean.getGdbtksr())){
                newDetailBean.setGdbtksr(new String(Base64.decode(detailBean.getGdbtksr().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setGdbtksr("");
            }
            if(!TextUtils.isEmpty(detailBean.getXjflsr())){
                newDetailBean.setXjflsr(new String(Base64.decode(detailBean.getXjflsr().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setXjflsr("");
            }
            if(!TextUtils.isEmpty(detailBean.getZhgzksr())){
                newDetailBean.setZhgzksr(new String(Base64.decode(detailBean.getZhgzksr().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setZhgzksr("");
            }
            if(!TextUtils.isEmpty(detailBean.getEmail())){
                newDetailBean.setEmail(new String(Base64.decode(detailBean.getEmail().getBytes(), Base64.DEFAULT)));
            }else {
                newDetailBean.setEmail("");
            }
            billEntity1.setDetail(newDetailBean);
        }

        List<BillEntity.HistoryDataBean> historyDataBeans=billEntity.getHistoryData();
        List<BillEntity.HistoryDataBean> newHistoryDataBeans=new ArrayList<>();
        for(int i=0;i<historyDataBeans.size();i++){
            BillEntity.HistoryDataBean historyDataBean=historyDataBeans.get(i);
            if(!TextUtils.isEmpty(historyDataBean.getBysrhj())) {
                historyDataBean.setBysrhj(new String(Base64.decode(historyDataBean.getBysrhj().getBytes(), Base64.DEFAULT)));
            }else {
                historyDataBean.setBysrhj("");
            }

            if(!TextUtils.isEmpty(historyDataBean.getYm())) {
                historyDataBean.setYm(new String(Base64.decode(historyDataBean.getYm().getBytes(), Base64.DEFAULT)));
            }else {
                historyDataBean.setYm("");
            }
            newHistoryDataBeans.add(historyDataBean);
        }
        billEntity1.setHistoryData(newHistoryDataBeans);
        return billEntity1;

    }
    private String getTime() {

        String time = "";
        Calendar c = Calendar.getInstance();//
        int mYear = c.get(Calendar.YEAR); // 获取当前年份
        int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份

        if (mMonth == 1) {
            time = (mYear - 1) + "-12";
        } else if (mMonth < 11) {
            time = mYear + "-0" + (mMonth - 1);
        } else {
            time = mYear + "-" + (mMonth - 1);
        }
        return time;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
    }
}
