package com.example.sophia_xu.WeiboStudy.api;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.example.sophia_xu.Utils.Logger;
import com.example.sophia_xu.Utils.ToastUtils;
import com.example.sophia_xu.WeiboStudy.weiboFirstActivity;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Sophia_Xu on 2015/8/11.
 */
public class SimpleRequestListner implements RequestListener {

    private Context context;
    private Dialog progressDialog;


    public  SimpleRequestListner(Context context,Dialog progressDialog){
        this.context = context;
        this.progressDialog = progressDialog;
    }

    @Override
    public void onComplete(String response) {
        onAllDone();
        Logger.show("Requst onComplete",response);

    }

    @Override
    public void onComplete4binary(ByteArrayOutputStream responseOS) {
        onAllDone();
        Logger.show("Requst onComplet4binary",responseOS.size() + "");

    }

    @Override
    public void onIOException(IOException e) {
        onAllDone();
        ToastUtils.showToast(context,e.getMessage(), Toast.LENGTH_SHORT);

        Logger.show("Request onIoException",e.toString());

    }

    @Override
    public void onError(WeiboException e) {
        onAllDone();
        ToastUtils.showToast(context,e.getMessage(), Toast.LENGTH_SHORT);

        Logger.show("Request onIoException",e.toString());


    }

    public void onAllDone(){  // 下拉刷新 ？
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
