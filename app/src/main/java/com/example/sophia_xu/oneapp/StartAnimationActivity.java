package com.example.sophia_xu.oneapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;


public class StartAnimationActivity extends Activity {

    private ViewPager viewpager;
    private ViewGroup viewpoints;
    private ViewGroup viewpages;
    private ArrayList<View> pageviews;
    private ImageView[] pointsImage;
    private ImageView pointImage;
    private Button startBtn;
    private StartAnimationPageChangeListner pageChangeListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = getLayoutInflater();

        viewpages = (ViewGroup)inflater.inflate(R.layout.activity_start_animation,null);

        pageviews = new ArrayList<View>();
        pageviews.add(inflater.inflate(R.layout.welcomepage0,null));
        pageviews.add(inflater.inflate(R.layout.welcomepage1,null));
        pageviews.add(inflater.inflate(R.layout.welcomepage2,null));

        pointsImage = new ImageView[pageviews.size()];


        viewpager = (ViewPager)viewpages.findViewById(R.id.viewpagers);  // 还是选择了动态加载，因为要准备好viewpoints
        viewpoints = (ViewGroup)viewpages.findViewById(R.id.viewpoints);

        for(int i = 0;i < pageviews.size();++i){
            pointImage = new ImageView(this);
            pointImage.setLayoutParams(new ViewGroup.LayoutParams(20,20)); // 这里还是没有概念
            pointImage.setPadding(5,0,5,0);
            pointsImage[i] = pointImage;
            if(0 == i){
                pointsImage[i].setImageResource(R.drawable.page_indicator_focused);
            }
            else{
                pointsImage[i].setImageResource(R.drawable.page_indicator_unfocused);
            }

            viewpoints.addView(pointsImage[i]);
            // 需不需要做一些 image释放之类的操作

        }
        setContentView(viewpages);
        pageChangeListner = new StartAnimationPageChangeListner();
        viewpager.setAdapter(new StartAnimationPageAdapter());
        viewpager.addOnPageChangeListener(pageChangeListner); // setonpagelistner已经被deprecated





    }

    @Override
    protected void onStop() {
        super.onStop();
        viewpager.removeOnPageChangeListener(pageChangeListner);
    }

    class StartAnimationPageAdapter  extends PagerAdapter{

        @Override
        public int getCount() {
            return pageviews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageviews.get(position));
            if(2 == position){
                startBtn = (Button)pageviews.get(position).findViewById(R.id.startBtn);
                startBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(StartAnimationActivity.this,AnimationDoorActivity.class);
                        StartAnimationActivity.this.startActivity(i);
                        StartAnimationActivity.this.finish();
                    }
                });
            }

            return pageviews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
             container.removeView(pageviews.get(position));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_animation, menu);
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


    class StartAnimationPageChangeListner implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            for(int i = 0;i < pointsImage.length;i++){
                if(position != i){
                    pointsImage[i].setImageResource(R.drawable.page_indicator_unfocused);
                }else{
                    pointsImage[i].setImageResource(R.drawable.page_indicator_focused);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
