package com.example.sophia_xu.WeiboStudy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.sophia_xu.Utils.Logger;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by sophia2 on 2015/11/11.
 */
public class Pull2RefreshListView extends PullToRefreshListView {
    public Pull2RefreshListView(Context context) {
        super(context);
    }

    public Pull2RefreshListView(Context context, Mode mode, AnimationStyle style) {
        super(context, mode, style);
    }

    public Pull2RefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public Pull2RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    protected ListView createListView(Context context,AttributeSet attrs){
        Logger.show("pull2refreshListview","create custom pullrefreshlistview");
        ListView listView = super.createListView(context,attrs);
        return listView;

    }


}
