package com.example.sophia_xu.WeiboStudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sophia_xu.WeiboStudy.base.BaseActivity;
import com.example.sophia_xu.oneapp.R;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import java.text.SimpleDateFormat;

/**
 * Created by Sophia_Xu on 2015/8/3.
 */
public class weiboMainActivity extends BaseActivity{

    private Button authBtn = null;

    private AuthInfo mAuthInfo;

    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
    private SsoHandler mSsoHandler;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_auth);

        authBtn = (Button) findViewById(R.id.id_btn_auth);
        // 创建微博实例
        //mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
        // 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
        mAuthInfo = new AuthInfo(this, WeiboConstants.APP_KEY, WeiboConstants.REDIRECT_URL, WeiboConstants.SCOPE);
        mSsoHandler = new SsoHandler(weiboMainActivity.this, mAuthInfo);

        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                mSsoHandler.authorize(new AuthListener());
            }

        });
        
   }

    /*当sso授权 activity 退出时，该函数被调用*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(mSsoHandler != null){
            mSsoHandler.authorizeCallBack(requestCode,resultCode,data);
        }
    }

    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息 
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 显示 Token
//                updateTokenView(false);

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(weiboMainActivity.this, mAccessToken);
                Toast.makeText(weiboMainActivity.this,
                       "auth successful", Toast.LENGTH_SHORT).show();
                intent2Activity(weiboFirstActivity.class);

            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message ="auth_failed";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(weiboMainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(weiboMainActivity.this,
                    "cancel auth", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(weiboMainActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
