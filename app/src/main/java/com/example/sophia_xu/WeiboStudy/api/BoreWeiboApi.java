package com.example.sophia_xu.WeiboStudy.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.sophia_xu.WeiboStudy.AccessTokenKeeper;
import com.example.sophia_xu.WeiboStudy.constants.URLs;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboParameters;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.WeiboAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Sophia_Xu on 2015/8/10.
 */
public class BoreWeiboApi  extends WeiboAPI{
    /**
     * 构造函数，使用各个API接口提供的服务前必须先获取Oauth2AccessToken
     *
     * @param oauth2AccessToken
     */

    private Handler mainLooperHandler = new Handler(Looper.getMainLooper()); // 主线程轮询器

    public BoreWeiboApi(Oauth2AccessToken oauth2AccessToken) {
        super(oauth2AccessToken);
    }

    public BoreWeiboApi(Context context){
        this(AccessTokenKeeper.readAccessToken(context));
    }

    @Override
    protected void request(String url, WeiboParameters params, String httpMethod, RequestListener listener) {
        super.request(url, params, httpMethod, listener);
    }

    public void requestInMainLooper(String url, WeiboParameters params, String httpMethod, final RequestListener listener){
        request(url, params, httpMethod, new RequestListener() {
            @Override
            public void onComplete(final String response) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete(response);
                    }
                });

            }

            @Override
            public void onComplete4binary(final ByteArrayOutputStream responseOS) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onComplete4binary(responseOS);
                    }
                });

            }

            @Override
            public void onIOException(final IOException e) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onIOException(e);
                    }
                });

            }

            @Override
            public void onError(final WeiboException e) {
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(e);
                    }
                });

            }
        });
    }


    public void statusesHome_timeline(long page,RequestListener listener){
        WeiboParameters parameters = new WeiboParameters();
        parameters.add("page",page);
        requestInMainLooper(URLs.statuseHome_timeline,parameters,HTTPMETHOD_GET,listener); // 这里的url出问题了
    }
}
