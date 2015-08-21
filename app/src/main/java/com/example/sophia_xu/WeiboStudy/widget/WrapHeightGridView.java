package com.example.sophia_xu.WeiboStudy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Sophia_Xu on 2015/8/17.
 */
public class WrapHeightGridView extends GridView {
    public WrapHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    // 为了让 gridview 自适应高度？
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
