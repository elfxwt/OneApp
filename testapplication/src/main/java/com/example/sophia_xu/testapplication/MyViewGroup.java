package com.example.sophia_xu.testapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Sophia_Xu on 2015/8/19.
 */
public class MyViewGroup extends LinearLayout {

    private static float width;
    private static float heigth;

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        width = getWidth();
        heigth = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
