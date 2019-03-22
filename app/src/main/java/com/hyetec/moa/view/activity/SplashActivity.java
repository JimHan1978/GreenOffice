package com.hyetec.moa.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hyetec.hmdp.core.utils.ACache;
import com.hyetec.moa.R;
import com.hyetec.moa.app.MoaApp;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        String isLogin = ACache.get(this.getApplicationContext()).getAsString(MoaApp.IS_LOGIN);
        if(isLogin==null|| isLogin.equals("false")){
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        }else{
            startActivity(new Intent(this,WebViewActivity.class));
            finish();
        }
    }
}
