package com.example.sophia_xu.WeiboStudy.base;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.example.sophia_xu.Utils.Logger;
import com.example.sophia_xu.Utils.ToastUtils;
import com.example.sophia_xu.WeiboStudy.CommonConstants;

/**
 * Created by Sophia_Xu on 2015/8/3.
 */
public class BaseActivity extends Activity {

    protected String TAG;
    protected BaseApplication application;
    protected SharedPreferences sp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT); // 这里强制设置成垂直，可以加判断

        application = (BaseApplication)getApplication();
        sp = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);

    }


    protected void intent2Activity(Class<? extends Activity> tarActivity){  // 这种写法~
        Intent i = new Intent(this,tarActivity);
        startActivity(i);
    }

    protected void showToast(String msg){
        ToastUtils.showToast(this,msg,Toast.LENGTH_SHORT);
    }

    protected void showLog(String msg){
        Logger.show(TAG,msg);
    }
}
