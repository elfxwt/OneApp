package com.example.sophia_xu.WeiboStudy.Fragment;

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
public class MessageFragment extends BaseFragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(activity, R.layout.frag_message,null);

        new TitleBuilder(view)
                .setMidTitle("MESSAGE")
                .setRightIM(R.drawable.ic_launcher)
                .setRightOnClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(activity,"message", Toast.LENGTH_SHORT);
                    }
                });
        return view;
    }
}
