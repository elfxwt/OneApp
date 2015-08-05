package com.example.sophia_xu.gallery;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sophia_Xu on 2015/7/24.
 */
public class MyHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {


    public interface CurrentImageChangeListener{
        void onCurrentImagChanged(int positon,View viewIndicator);
    }


    public interface OnItemClickListener{
        void onClick(View view,int pos);
    }

    private CurrentImageChangeListener mListener;
    private OnItemClickListener mOnclickListener;
    private final static String TAG = "myHorizontalScrollView";

    private LinearLayout mContainer;

    private int mChildWidth;
    private int mChildHeight;
    private int mCurrentIndex;
    private int mFirstIndex;
    private View mFirstView;

    private HorizontallScrollViewAdapter mAdapter;
    private int mCountOneScreen;
    private int mScreenWidth;

    /*
    * 将view 和它对应的index放在hashmap存储下来的目的是为了在click的时候能够快速定位到相应的图片
    * */
    private Map<View,Integer> mViewPos = new HashMap<>();


    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mContainer = (LinearLayout) getChildAt(0);
    }

    protected  void loadNextImg(){
        if(mCurrentIndex == mAdapter.getCount() - 1){
            return;
        }
        scrollTo(0,0);
        mViewPos.remove(mContainer.getChildAt(0));
        mContainer.removeViewAt(0);

        View view = mAdapter.getView(++mCurrentIndex,null,mContainer);
        view.setOnClickListener(this);
        mContainer.addView(view);
        mViewPos.put(view,mCurrentIndex);

        mFirstIndex++;
        if(mListener != null){
            notifyCurrentImgChanged();
        }
    }


    protected void loadPreImg(){
        if(mFirstIndex == 0){
            return;
        }
        int index = mCurrentIndex - mCountOneScreen;
        if(index >=0){
            int oldViewPos = mContainer.getChildCount() - 1;
            mViewPos.remove(mContainer.getChildAt(oldViewPos));
            mContainer.removeViewAt(oldViewPos);

            View view = mAdapter.getView(index,null,mContainer);
            mViewPos.put(view,index);
            mContainer.addView(view,0); // the first position
            view.setOnClickListener(this);

            scrollTo(mChildWidth,0);
            mCurrentIndex--;
            mFirstIndex--;

            if(mListener != null)
                notifyCurrentImgChanged();
        }
    }

    private void notifyCurrentImgChanged() {
        for(int i = 0;i < mContainer.getChildCount();i++){  // ?
            mContainer.getChildAt(i).setBackgroundColor(Color.YELLOW);
        }
        /*
        * 每次都是都第一个进行的，mContainer里面的第一个view，让其作为展示图片，所以设置了滚动监听
        * 其实往往不是相册下的需求，可能不需要滚动监听
        * */
        mListener.onCurrentImagChanged(mFirstIndex,mContainer.getChildAt(0));



    }


    public  void initDatas(HorizontallScrollViewAdapter mAdapter){
        this.mAdapter = mAdapter;
        mContainer = (LinearLayout) getChildAt(0);

        final View view = mAdapter.getView(0,null,mContainer);
        mContainer.addView(view);

        if(mChildWidth == 0 && mChildHeight == 0){
            int w = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
            view.measure(w,h); // 这里还是奇怪的？
            mChildHeight = view.getMeasuredHeight();
            mChildWidth = view.getMeasuredWidth();
            Log.e("sophia",mChildHeight + "," + mChildWidth);
            mCountOneScreen = mScreenWidth / mChildWidth+2;// 这里为什么加2
            Log.d("sophia",String.valueOf(mCountOneScreen));
        }

        initFirstScreenChildren(mCountOneScreen);
    }

    private void initFirstScreenChildren(int mCountOneScreen) {
        mContainer = (LinearLayout) getChildAt(0);
        mContainer.removeAllViews();
        mViewPos.clear();

        for(int i = 0;i < mCountOneScreen;i++){
            View view = mAdapter.getView(i,null,mContainer);
            view.setOnClickListener(this);
            mContainer.addView(view);
            mViewPos.put(view,i);
            mCurrentIndex = i;
        }

        if(mListener != null){  // ? why this?
            notifyCurrentImgChanged();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                int scrollX = getScrollX();
                if(scrollX >= mChildWidth)
                    loadNextImg();
                if(scrollX == 0) // ? why is zero  当滑到最前方的时候，子视图和当前可见的框范围
                    loadPreImg();
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        if(mOnclickListener != null){
            for(int i = 0;i < mContainer.getChildCount();i++){
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
            mOnclickListener.onClick(v,mViewPos.get(v));
        }

    }

    public void setOnItemClickListner(OnItemClickListener mOnclickListener){
        this.mOnclickListener = mOnclickListener;
    }

    public void setCurrentImageChangeListen(CurrentImageChangeListener mListener){
        this.mListener = mListener;
    }
}
