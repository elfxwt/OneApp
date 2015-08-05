package com.example.sophia_xu.WeiboStudy.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sophia_xu.Utils.TitleBuilder;
import com.example.sophia_xu.Utils.ToastUtils;
import com.example.sophia_xu.WeiboStudy.base.BaseFragment;
import com.example.sophia_xu.oneapp.R;

/**
 * Created by Sophia_Xu on 2015/8/4.
 */
public class FrontPageFragment extends BaseFragment {
    private View view;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.frag_home,null);

        new TitleBuilder(view)
                .setMidTitle("HOME")
                .setLeftTX("LEFT")
                .setLeftOnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(view.getContext(),"home page", Toast.LENGTH_SHORT);
            }
        });
        return view;
    }
}
