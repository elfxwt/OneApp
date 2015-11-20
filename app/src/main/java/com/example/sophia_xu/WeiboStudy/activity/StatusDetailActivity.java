package com.example.sophia_xu.WeiboStudy.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sophia_xu.Utils.TitleBuilder;
import com.example.sophia_xu.WeiboStudy.base.BaseActivity;
import com.example.sophia_xu.WeiboStudy.widget.WrapHeightGridView;
import com.example.sophia_xu.oneapp.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by sophia2 on 2015/11/11.
 */
public class StatusDetailActivity extends BaseActivity implements AbsListView.OnClickListener,RadioGroup.OnCheckedChangeListener {


    // 跳转到写评论页面code
    private static final int REQUEST_CODE_WRITE_COMMENT = 2333;

    // listView headerView - 微博信息
    private View status_detail_info;
    private ImageView iv_avatar;
    private TextView tv_subhead;
    private TextView tv_caption;
    private FrameLayout include_status_image;
    private WrapHeightGridView gv_images;
    private ImageView iv_image;
    private TextView tv_content;
    private View include_retweeted_status;
    private TextView tv_retweeted_content;
    private FrameLayout fl_retweeted_imageview;
    private GridView gv_retweeted_images;
    private ImageView iv_retweeted_image;
    // shadow_tab - 顶部悬浮的菜单栏
    private View shadow_status_detail_tab;
    private RadioGroup shadow_rg_status_detail;
    private RadioButton shadow_rb_retweets;
    private RadioButton shadow_rb_comments;
    private RadioButton shadow_rb_likes;
    // listView headerView - 添加至列表中作为header的菜单栏
    private View status_detail_tab;
    private RadioGroup rg_status_detail;
    private RadioButton rb_retweets;
    private RadioButton rb_comments;
    private RadioButton rb_likes;
    // listView - 下拉刷新控件
    private PullToRefreshListView plv_status_detail;
    // footView - 加载更多
    private View footView;
    // bottom_control - 底部互动栏,包括转发/评论/点赞
    private LinearLayout ll_bottom_control;
    private LinearLayout ll_share_bottom;
    private TextView tv_share_bottom;
    private LinearLayout ll_comment_bottom;
    private TextView tv_comment_bottom;
    private LinearLayout ll_like_bottom;
    private TextView tv_like_bottom;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail);

        initview();

    }

    private void initview() {
        // the top title
        initTitle();
        // the weibo detail as the headview of pullTorefresh
        initDetailHead();
        // the middle tab
        initTab();
        //listView of comments
        initListView();
        // the bottom controller
        initControlBar();
    }

    private void initControlBar() {

    }

    private void initListView() {
        plv_status_detail = (PullToRefreshListView) findViewById(R.id.id_detail_lv_home);


    }

    private void initTab() {
        shadow_status_detail_tab = findViewById(R.id.status_detail_tab);
        shadow_rg_status_detail = (RadioGroup) shadow_status_detail_tab
                .findViewById(R.id.rg_status_detail);
        shadow_rb_retweets = (RadioButton) shadow_status_detail_tab
                .findViewById(R.id.rb_retweets);
        shadow_rb_comments = (RadioButton) shadow_status_detail_tab
                .findViewById(R.id.rb_comments);
        shadow_rb_likes = (RadioButton) shadow_status_detail_tab
                .findViewById(R.id.rb_likes);
        shadow_rg_status_detail.setOnCheckedChangeListener(this);
        // header
        status_detail_tab = View.inflate(this, R.layout.status_detail_tab, null);
        rg_status_detail = (RadioGroup) status_detail_tab
                .findViewById(R.id.rg_status_detail);
        rb_retweets = (RadioButton) status_detail_tab
                .findViewById(R.id.rb_retweets);
        rb_comments = (RadioButton) status_detail_tab
                .findViewById(R.id.rb_comments);
        rb_likes = (RadioButton) status_detail_tab
                .findViewById(R.id.rb_likes);
        rg_status_detail.setOnCheckedChangeListener(this);

    }

    private void initDetailHead() {
        status_detail_info = View.inflate(this,R.layout.item_status,null);
        status_detail_info.setBackgroundResource(R.color.white); // 点击的背景色？
        status_detail_info.findViewById(R.id.include_bottom_controll).setVisibility(View.GONE);
        iv_avatar = (ImageView) status_detail_info.findViewById(R.id.id_im_photo);
        tv_subhead = (TextView) status_detail_info.findViewById(R.id.id_tv_subhead);
        tv_caption = (TextView) status_detail_info.findViewById(R.id.id_tv_caption);
        include_status_image = (FrameLayout) status_detail_info.findViewById(R.id.include_status_image);
        gv_images = (WrapHeightGridView) status_detail_info.findViewById(R.id.id_gv_images);
        iv_image = (ImageView) status_detail_info.findViewById(R.id.id_iv_image);
        tv_content = (TextView) status_detail_info.findViewById(R.id.id_tv_content);
        include_retweeted_status = status_detail_info.findViewById(R.id.include_retweeted_status);
        tv_retweeted_content = (TextView) status_detail_info.findViewById(R.id.id_tv_retweeted_content);
        fl_retweeted_imageview = (FrameLayout) include_retweeted_status.findViewById(R.id.id_include_status_image);
        gv_retweeted_images = (GridView) fl_retweeted_imageview.findViewById(R.id.id_gv_images);
        iv_retweeted_image = (ImageView) fl_retweeted_imageview.findViewById(R.id.id_iv_image);
        iv_image.setOnClickListener(this);
    }

    private void initTitle() {
        new TitleBuilder(this)
                .setMidTitle("微博正文")
                .setLeftIM(R.drawable.navigationbar_back_sel)
                .setLeftOnClickListner(this);

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}
