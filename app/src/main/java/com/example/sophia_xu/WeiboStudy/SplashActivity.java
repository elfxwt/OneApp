package com.example.sophia_xu.WeiboStudy;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sophia_xu.WeiboStudy.base.BaseActivity;
import com.example.sophia_xu.oneapp.R;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;


public class SplashActivity extends BaseActivity {


    private static final int TO_AUTH_MAINACTIVITY = 1;
    private static final int TO_CONTENT_FIRSTACTIVITY = 2;
    private static final long SPLASH_DURING_TIME = 1000;

    private Oauth2AccessToken mAccessToken;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

                case TO_AUTH_MAINACTIVITY:
                    intent2Activity(weiboMainActivity.class);
                    finish();
                    break;
                case TO_CONTENT_FIRSTACTIVITY:
                    intent2Activity(weiboFirstActivity.class);
                    finish();
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        Message msg = new Message();
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        if(mAccessToken.isSessionValid()){
//            msg.what = TO_CONTENT_FIRSTACTIVITY;
//            handler.sendMessageDelayed(TO_CONTENT_FIRSTACTIVITY,SPLASH_DURING_TIME);
              handler.sendEmptyMessageDelayed(TO_CONTENT_FIRSTACTIVITY,SPLASH_DURING_TIME);
        }else{
//            msg.what = TO_AUTH_MAINACTIVITY;
//            handler.sendMessageDelayed(TO_CONTENT_FIRSTACTIVITY,SPLASH_DURING_TIME);
            handler.sendEmptyMessageDelayed(TO_AUTH_MAINACTIVITY,SPLASH_DURING_TIME);

        }



    }



}
