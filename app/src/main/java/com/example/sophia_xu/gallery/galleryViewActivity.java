package com.example.sophia_xu.gallery;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.sophia_xu.oneapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class galleryViewActivity extends Activity {

    private MyHorizontalScrollView myHorizontalScrollView;
    private HorizontallScrollViewAdapter mAdapter;
    private ImageView mShowImag;
    private List<Integer> mDatas = new ArrayList<Integer>(Arrays.asList(R.drawable.gallery_0,R.drawable.gallery_1,R.drawable.gallery_2,R.drawable.gallery_3,
            R.drawable.gallery_4,R.drawable.gallery_5,R.drawable.gallery_6,R.drawable.gallery_7,R.drawable.gallery_8))    ;
    private List<String> mTexts = new ArrayList<String>(
        Arrays.asList("watchface1","watchface2","watchface3","watchface4","watchface5","watchface6","watchface7","watchface8","watchface9")); //
//    private String [] mTexts = new String[]{"nn","nw"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_gallery_view);

        mShowImag = (ImageView) findViewById(R.id.id_content);
        mShowImag.setImageResource(R.drawable.gallery_0);

        myHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_horizontalScrollView);
        mAdapter = new HorizontallScrollViewAdapter(this,mDatas,mTexts);

        myHorizontalScrollView.setCurrentImageChangeListen(new MyHorizontalScrollView.CurrentImageChangeListener() {
            @Override
            public void onCurrentImagChanged(int positon, View viewIndicator) {
                mShowImag.setImageResource(mDatas.get(positon));
                viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });

        myHorizontalScrollView.setOnItemClickListner(new MyHorizontalScrollView.OnItemClickListener() {
            @Override
            public void onClick(View view, int pos) {
                Log.d("sophia","onitemClickListner");
                mShowImag.setImageResource(mDatas.get(pos));
                view.setBackgroundColor(Color.parseColor("#AA024DA4"));
            }
        });

        myHorizontalScrollView.initDatas(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
