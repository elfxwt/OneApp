package com.example.sophia_xu.oneapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;



public class AnimationDoorActivity extends Activity {

    private ImageView img_left,img_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_door);
        img_left = (ImageView)findViewById(R.id.doorpage_left);
        img_right = (ImageView)findViewById(R.id.doorpage_right);

        AnimationSet animLeft = new AnimationSet(true);
        TranslateAnimation transLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
        transLeft.setDuration(2000);
        animLeft.addAnimation(transLeft);
        animLeft.setFillAfter(true);
        img_left.startAnimation(transLeft);
        transLeft.startNow();

        AnimationSet animRight = new AnimationSet(true);
        TranslateAnimation transRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,1f,
                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f);
        transRight.setDuration(2000);
        animRight.addAnimation(transRight);
        animRight.setFillAfter(true);
        img_right.startAnimation(transRight);
        transRight.startNow();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(AnimationDoorActivity.this,MainActivity.class);
                startActivity(i);
                AnimationDoorActivity.this.finish();
            }
        },1000);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_animation_door, menu);
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
