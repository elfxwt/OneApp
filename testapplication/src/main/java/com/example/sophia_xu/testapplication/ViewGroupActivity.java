package com.example.sophia_xu.testapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class ViewGroupActivity extends Activity {

    private MyViewGroup myViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        myViewGroup = (MyViewGroup) findViewById(R.id.id_myViewGroup);
    }


    @Override
    protected void onResume() {
        super.onResume();
        for(int i = 0;i < myViewGroup.getChildCount();i++){
            View childview = myViewGroup.getChildAt(i);
            if(i == 3){
                ViewGroup rlayout = (ViewGroup) childview;
                Log.d("sophia","the first view is viewgroup?" + rlayout.getChildCount());
            }


            Log.d("sophia", "the index " + i + " view is " + childview.getWidth() + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_group, menu);
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
