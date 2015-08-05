package com.example.sophia_xu.WeiboStudy.Fragment;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by Sophia_Xu on 2015/8/4.
 *
 * 统一管理四个fragment之间的切换
 *
 */
public class FragmentController {

    private static FragmentController fragmentController;
    private int containerId;
//    private FragmentActivity activity;
    private FragmentManager fm;

    private ArrayList<Fragment> fragments ;

    public static FragmentController getInstance(FragmentActivity activity,int containerId){
        if(fragmentController == null){
            fragmentController = new FragmentController(containerId,activity);
        }

        return fragmentController;

    }

    private  FragmentController(int containerId,FragmentActivity activity){
//        this.activity = activity;
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment(containerId);

    }

    private void initFragment(int containerId) {
        fragments = new ArrayList<Fragment>();
        fragments.add(new FrontPageFragment());
        fragments.add(new MessageFragment());
        fragments.add(new SearchFragment());
        fragments.add(new UserFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment:fragments){
            ft.add(containerId, fragment);

        }
        ft.commit();

    }


    public void showFragment(int position){
        hideAllFragments();

        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragments.get(position));
        ft.commit();

    }

    public void hideAllFragments(){
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment:fragments){
            if(fragment != null)     // 这里判空的处理，不要忘记了！！
                ft.hide(fragment);  // 这里用的是hide 因为我们想要保存fragment的状态
        }
        ft.commit();
    }

    public Fragment getFragment(int position){
        return fragments.get(position);
    }


}
