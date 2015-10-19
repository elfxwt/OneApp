package com.example.sophia_xu.PhotoWall;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.example.sophia_xu.oneapp.R;

/**
 * Created by Sophia_Xu on 2015/9/11.
 */
public class PhotoWallMainActivity extends Activity{

    private GridView mGridView;
    private PhotoWallAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pw_gridview);
        mGridView= (GridView) findViewById(R.id.id_gv_photowall);
        mAdapter = new PhotoWallAdapter(this,ImageUtils.imageThumbURrls,mGridView);
        mGridView.setAdapter(mAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cancleAllTasks(); // 防止activity的内存泄露

    }
}

