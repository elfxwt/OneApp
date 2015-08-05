package com.example.sophia_xu.Utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;



/**
 * Created by Sophia_Xu on 2015/8/3.
 */
public class ToastUtils {

    private static Toast mToast;

    public static  void showToast(Context context,CharSequence text,int duration){
        if(mToast == null){
            mToast= Toast.makeText(context,text,duration);
        }else{
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }
}
