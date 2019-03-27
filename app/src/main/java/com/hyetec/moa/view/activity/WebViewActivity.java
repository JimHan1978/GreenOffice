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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;
import com.hyetec.moa.model.entity.UserEntity;
import com.hyetec.moa.viewmodel.WebViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class WebViewActivity extends BaseActivity<WebViewModel> {


    @BindView(R.id.wv_item)
    WebView wv_item;
    private String url;

    @Override
    public int initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        //创建ViewModel
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(WebViewModel.class);
        return R.layout.activity_webview;
    }


    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
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

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        String json = getFromAssets("data/report.json");

        UserEntity user = (UserEntity) ACache.get(this).getAsObject(MoaApp.USER_DATA);
        int sex=user.getSex();
        String joindate=user.getJoindate();

        wv_item.loadUrl("file:///android_asset/month-bill.html");

        wv_item.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {//当页面加载完成
                super.onPageFinished(view, url);

                mViewModel.getData("2019-02").observe(WebViewActivity.this, billData -> {
                    if(billData!=null && billData.isSuccess()){
                        Gson gson = new Gson();

                        wv_item.evaluateJavascript("javascript:setData('"+gson.toJson(billData.getResult())+"',"+sex+",'"+joindate+"')", new ValueCallback<String>() {
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }


}
