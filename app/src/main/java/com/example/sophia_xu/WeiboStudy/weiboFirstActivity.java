package com.example.sophia_xu.WeiboStudy;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.sophia_xu.WeiboStudy.Fragment.FragmentController;
import com.example.sophia_xu.WeiboStudy.base.BaseActivity;
import com.example.sophia_xu.oneapp.R;

public class weiboFirstActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener,View.OnClickListener {

    private FrameLayout frag_content;
    private RadioGroup radioGroup;
//    private RadioButton rb_home;
//    private RadioButton rb_search;
//    private RadioButton rb_user;
//    private RadioButton rb_message;
//
    private FragmentController fc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_first);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        fc = FragmentController.getInstance(this,R.id.id_fragment);
        fc.showFragment(0);
        initView();



    }

    private void initView() {

        radioGroup = (RadioGroup) findViewById(R.id.id_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);

//        frag_content = (FrameLayout) findViewById(R.id.id_fragment);
//        rb_home = (RadioButton) findViewById(R.id.id_btn_home);
//        rb_message = (RadioButton) findViewById(R.id.id_btn_message);
//        rb_search = (RadioButton) findViewById(R.id.id_btn_search);
//        rb_user = (RadioButton) findViewById(R.id.id_btn_user);


    }

    @Override
    public void onCheckedChanged(RadioGroup group,int checkedId) {

        switch (checkedId){
            case R.id.id_btn_home:

                fc.showFragment(0);
                break;
            case R.id.id_btn_message:
                fc.showFragment(1);
                break;
            case R.id.id_btn_search:
                fc.showFragment(2);
                break;
            case R.id.id_btn_user:
                fc.showFragment(3);
                break;
            default:
                break;

        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }


    }




}