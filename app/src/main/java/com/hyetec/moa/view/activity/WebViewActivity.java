/**
 * @Title: OnlineCourseDetails.java
 * @Package com.hyetec.greentraining.activity
 * @Description:
 * @author zzn
 * @date 2016年6月22日 下午3:04:25
 * @version V1.0
 */
package com.hyetec.moa.view.activity;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hyetec.hmdp.core.base.BaseActivity;
import com.hyetec.moa.R;
import com.hyetec.moa.viewmodel.WebViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;


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

    @Override
    public void initData(Bundle savedInstanceState) {
        WebSettings settings = wv_item.getSettings();

        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        settings.setSupportZoom(true);
        // 设置出现缩放工具
        settings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        settings.setUseWideViewPort(true);
        //自适应屏幕
        //settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);

        settings.setLoadWithOverviewMode(true);
        //url=getIntent().getStringExtra("url");
        wv_item.loadUrl("file:///android_asset/month-bill.html");
        setListener();
    }


    /**
     * @Title: setListener
     * @Description:
     * @param
     * @return void
     */
    private void setListener() {
        //TODO Auto-generated method stub
        wv_item.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url); //在当前的webview中跳转到新的url

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
