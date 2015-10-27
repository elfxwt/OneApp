package com.example.sophia_xu.PhotoWall.ThreeCachePhotoWall;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.example.sophia_xu.PhotoWall.ImageUtils;
import com.example.sophia_xu.PhotoWall.PhotoWallAdapter;
import com.example.sophia_xu.oneapp.R;

/**
 * Created by sophia2 on 2015/10/20.
 */
public class MainActivity extends Activity{
    private GridView mGridView;
    private ImageAdpater mAdapter;

    private int mImageThumbSize;
    private int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_gridvew_three);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mGridView= (GridView) findViewById(R.id.id_gv_photowall);
        mAdapter = new ImageAdpater(this, ImageUtils.imageThumbURrls,mGridView);
        mGridView.setAdapter(mAdapter);
        mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {   // why caclulate the width that?
                        final int numColumns = (int) Math.floor(mGridView.getWidth()/(mImageThumbSize+mImageThumbSpacing));
                        Log.d("sophia","caclulate the numColums is " + numColumns);
                        if(numColumns > 0){
                            int colummWidth = (mGridView.getWidth() / numColumns) - mImageThumbSpacing;
                            Log.d("sophia","caculate the width is " + colummWidth);
                            mAdapter.setItemHeight(colummWidth);
                            mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }

                    }
                }
        );

    }


    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.fluchCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAdapter != null){
            mAdapter.cancelAllTasks(); // 防止activity的内存泄露
           mAdapter = null;
        }
        // if is this useful?
        if(mGridView != null){
            mGridView.setAdapter(null);
            mGridView = null;
        }

    }

}
