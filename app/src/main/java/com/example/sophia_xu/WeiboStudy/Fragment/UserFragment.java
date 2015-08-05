package com.example.sophia_xu.WeiboStudy.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sophia_xu.WeiboStudy.base.BaseFragment;
import com.example.sophia_xu.oneapp.R;

/**
 * Created by Sophia_Xu on 2015/8/4.
 */
public class UserFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.frag_user,null);
        return view;
    }
}
