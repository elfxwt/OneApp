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
import com.example.sophia_xu.WeiboStudy.entity.Status;
import com.example.sophia_xu.WeiboStudy.entity.StatusTimeLineResponse;
import com.example.sophia_xu.oneapp.R;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sophia_Xu on 2015/8/4.
 */
public class FrontPageFragment extends BaseFragment {

    private View view;
    private PullToRefreshListView ptr_lv_home;
    private StatusAdapter mAdapter;
    private List<Status> statuses = new ArrayList<Status>();
    private int curPage = 1;
    private View footview;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.show("sophia","oncreateView");
        initView();
        loadData(1 );



     return view;
    }

    private void loadData(final int page) {
        Logger.show("sophia","loadData start");

        BoreWeiboApi api = new BoreWeiboApi(activity);
        api.statusesHome_timeline(page,new SimpleRequestListner(activity,null){
            @Override
            public void onComplete(String response) {
                super.onComplete(response);
//                Logger.show("sophia","onComplete "+response);

                if(page == 1){
                    statuses.clear(); // 这里保证一个数据集，一个adapter?
                }

                curPage = page;
                addData(new Gson().fromJson(response,StatusTimeLineResponse.class));

                // 一开始的时候就只是返回一页数据，现在返回很多页数据,所以就封装了addData方法
//               StatusTimeLineResponse statusTimeLineResponse =  new Gson().fromJson(response, StatusTimeLineResponse.class);
//                Logger.show("sophia","statusTimeLine" + statusTimeLineResponse.getTotal_number());
//                ptr_lv_home.setAdapter(new StatusAdapter(activity,statusTimeLineResponse.getStatuses()));


            }

            @Override
            public void onAllDone() { // simpleRespons封装的好处，一次添加即可
                super.onAllDone();
                ptr_lv_home.onRefreshComplete(); // 完成刷新
            }
        });

    }

    private void addData(StatusTimeLineResponse resBean) {
        for(Status status:resBean.getStatuses()){
            if(!statuses.contains(status)){
                statuses.add(status); // 不要重复添加
            }
        }
        mAdapter.notifyDataSetChanged();

        if(curPage < resBean.getTotal_number()){
            addFootView(ptr_lv_home,footview);
        }else{
            removeFootView(ptr_lv_home,footview);
        }

    }

    private void addFootView(PullToRefreshListView plv, View footview) { // 可能会被调用多次
        ListView lv = plv.getRefreshableView();
        if(lv.getFooterViewsCount() == 1){ // 是不是默认就一个？
            lv.addFooterView(footview);
        }
    }

    private void removeFootView(PullToRefreshListView plv,View footview){
        ListView lv = plv.getRefreshableView();
        if(lv.getFooterViewsCount() > 1){
            lv.removeFooterView(footview);
        }
    }
    private void initView() {
        Logger.show("sophia","initView");
        view = View.inflate(activity, R.layout.frag_home,null);


        new TitleBuilder(view).setMidTitle("首页");
//                .setMidTitle("HOME")
//                .setLeftTX("LEFT")
//                .setLeftOnClickListner(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showToast(view.getContext(),"home page", Toast.LENGTH_SHORT);
//                        Logger.show("sophia","onclick logger");
//                        Log.d("sophia","onclick log");
//                    }
//                });

        ptr_lv_home = (PullToRefreshListView) view.findViewById(R.id.id_lv_home);
        mAdapter = new StatusAdapter(activity,statuses);
        ptr_lv_home.setAdapter(mAdapter); // 保证适配器对象是一个
        ptr_lv_home.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                loadData(1);
            }
        });
        // 列表滚动到最后一列时 进行自动更新
        ptr_lv_home.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                loadData(curPage + 1);
            }

        });
        footview = View.inflate(activity,R.layout.footview_loading,null);

    }
}
