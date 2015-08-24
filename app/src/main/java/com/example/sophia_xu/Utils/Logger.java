package com.example.sophia_xu.Utils;

import android.util.Log;

import com.example.sophia_xu.WeiboStudy.CommonConstants;

/**
 * Created by Sophia_Xu on 2015/8/3.
 */
public class Logger {  // 对log 信息做统一的处理，有个开关，isshowLog ,直接设值就可以统一管理所有的log 信息，调用show 方法，统一管理打印或不打印

    public static void show(String TAG,String msg){
        if(!CommonConstants.isShowLog)
            return;
        show(TAG,msg, Log.ERROR);
    }

    private static void show(String TAG, String msg, int level) {
        if(!CommonConstants.isShowLog)
            return;
        switch (level){
            case Log.VERBOSE:
                Log.v(TAG,msg);
                break;
            case Log.DEBUG:
                Log.d(TAG,msg);
                break;
            case Log.INFO:
                Log.i(TAG,msg);
                break;
            case Log.ERROR:
                Log.e(TAG,msg);
                break;
            default:
                Log.i(TAG,msg);
                break;

        }
    }

}
