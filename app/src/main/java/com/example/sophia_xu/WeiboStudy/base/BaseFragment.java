package com.example.sophia_xu.WeiboStudy.base;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.sophia_xu.WeiboStudy.weiboFirstActivity;
import com.example.sophia_xu.oneapp.MainActivity;

import java.lang.reflect.Field;

/**
 * Created by Sophia_Xu on 2015/8/4.
 */
public class BaseFragment extends Fragment {
    protected weiboFirstActivity activity;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (weiboFirstActivity) getActivity();
    }

    public void intent2Activity(Class<? extends Activity> tarActivity){
        Intent i = new Intent(activity,tarActivity);
        startActivity(i);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Field childFragmentManager = null;
        try {
            childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this,null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
