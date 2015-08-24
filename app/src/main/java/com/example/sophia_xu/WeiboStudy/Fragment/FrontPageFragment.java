package com.example.sophia_xu.WeiboStudy.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sophia_xu.Utils.Logger;
import com.example.sophia_xu.Utils.TitleBuilder;
import com.example.sophia_xu.Utils.ToastUtils;
import com.example.sophia_xu.WeiboStudy.adapter.StatusAdapter;
import com.example.sophia_xu.WeiboStudy.api.BoreWeiboApi;
import com.example.sophia_xu.WeiboStudy.api.SimpleRequestListner;
import com.example.sophia_xu.WeiboStudy.base.BaseFragment;
import com.example.sophia_xu.WeiboStudy.entity.StatusTimeLineResponse;
import com.example.sophia_xu.oneapp.R;
import com.google.gson.Gson;

/**
 * Created by Sophia_Xu on 2015/8/4.
 */
public class FrontPageFragment extends BaseFragment {

    private View view;
    private ListView lv_home;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.show("sophia","oncreateView");
        initView();
        loadData(1 );



     return view;
    }

    private void loadData(int page) {
        Logger.show("sophia","loadData start");

        BoreWeiboApi api = new BoreWeiboApi(activity);
        api.statusesHome_timeline(page,new SimpleRequestListner(activity,null){
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
//                Logger.show("sophia","onComplete "+response);

               StatusTimeLineResponse statusTimeLineResponse =  new Gson().fromJson(response, StatusTimeLineResponse.class);
//                Logger.show("sophia","statusTimeLine" + statusTimeLineResponse.getTotal_number());
                lv_home.setAdapter(new StatusAdapter(activity,statusTimeLineResponse.getStatuses()));
            }
        });

    }

    private void initView() {
        Logger.show("sophia","initView");
        view = View.inflate(activity, R.layout.frag_home,null);

        new TitleBuilder(view)
                .setMidTitle("HOME")
                .setLeftTX("LEFT")
                .setLeftOnClickListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(view.getContext(),"home page", Toast.LENGTH_SHORT);
                        Logger.show("sophia","onclick logger");
                        Log.d("sophia","onclick log");
                    }
                });

        lv_home = (ListView) view.findViewById(R.id.id_lv_home);
    }
}
