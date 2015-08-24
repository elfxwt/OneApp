package com.example.sophia_xu.testapplication;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button btn;
    public final static String Tag = "text Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) this.findViewById(R.id.id_btn);

        int paddingRight = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0).getPaddingRight();
        int childCount = ((ViewGroup)((ViewGroup)findViewById(android.R.id.content)).getChildAt(0)).getChildCount();
        Log.d("sophia","oncreat R.id.content paddingRight " + paddingRight + "child count " + childCount);
        DisplayMetrics metrics = new DisplayMetrics();

        final float scale = getResources().getDisplayMetrics().density;
        final float density = getResources().getDisplayMetrics().densityDpi;

        Log.d("sophia","display scale" + scale + "density " + density);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("sophia","onClick");
                Intent i = new Intent(MainActivity.this,ViewGroupActivity.class);
                startActivity(i);
            }
        });

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("sophia","onTouch");

                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        float x = event.getX();
                        float y = event.getY();
//                        Log.d("sophia","onTouch  action down x is " +x + " y is "+y );
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Log.d("sophia","onTouch  action move");
                        break;
                    case MotionEvent.ACTION_UP:
//                        Log.d("sophia","onTouch  action up");
                        break;
                }
                return false; // default value is return false, o
            }


        });



    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("sophia","MainActivity onResume");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d("sophia","activity onTouchEvent");

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
