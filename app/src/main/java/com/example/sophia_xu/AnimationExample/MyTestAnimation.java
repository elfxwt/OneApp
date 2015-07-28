package com.example.sophia_xu.AnimationExample;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

/**
 * Created by Sophia_Xu on 2015/7/20.
 */
public class MyTestAnimation extends Animation{

    private int halfwidth;
    private int halfheight;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        setDuration(1500);
        setFillAfter(true);

        halfwidth = width / 2;
        halfheight = height / 2;
        setInterpolator(new LinearInterpolator());
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        final Matrix matrix= t.getMatrix();
        matrix.preScale(interpolatedTime,interpolatedTime);
        matrix.preRotate(interpolatedTime * 360);
        matrix.preTranslate(-halfwidth,-halfheight);
        matrix.postTranslate(halfwidth,halfheight); // change the animation start ?

    }
}
