package com.example.sophia_xu.Utils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sophia_xu.oneapp.R;

import org.w3c.dom.Text;

/**
 * Created by Sophia_Xu on 2015/8/5.
 */
public class TitleBuilder {

    private View titleBarRv;
    private TextView tvMid;
    private TextView tvLeft;
    private ImageView ivLeft;
    private TextView tvRight;
    private ImageView ivRight;

    public TitleBuilder(Activity context){
        titleBarRv = context.findViewById(R.id.id_titlebar_rv);
        tvLeft = (TextView) context.findViewById(R.id.id_title_left_text);
        ivLeft = (ImageView) context.findViewById(R.id.id_title_left_image);
        tvRight = (TextView) context.findViewById(R.id.id_title_right_text);
        ivRight = (ImageView) context.findViewById(R.id.id_title_right_image);
        tvMid = (TextView) context.findViewById(R.id.id_title_mid);

    }

    public TitleBuilder(View context){
        titleBarRv = context.findViewById(R.id.id_titlebar_rv);
        tvLeft = (TextView) context.findViewById(R.id.id_title_left_text);
        ivLeft = (ImageView) context.findViewById(R.id.id_title_left_image);
        tvRight = (TextView) context.findViewById(R.id.id_title_right_text);
        ivRight = (ImageView) context.findViewById(R.id.id_title_right_image);
        tvMid = (TextView) context.findViewById(R.id.id_title_mid);

    }

    public TitleBuilder setRvResouce(int id){
        titleBarRv.setBackgroundResource(id);
        return this;
    }

    public TitleBuilder setMidTitle(String text){
        tvMid.setVisibility(TextUtils.isEmpty(text)? View.GONE:View.VISIBLE);
        tvMid.setText(text);
        return this;

    }

    public TitleBuilder setLeftTX(String text){
        tvLeft.setVisibility(TextUtils.isEmpty(text)? View.GONE:View.VISIBLE);
        tvLeft.setText(text);
        return this;
    }

    public TitleBuilder setLeftIM(int id){
        ivLeft.setVisibility(id > 0? View.VISIBLE:View.GONE);
        ivLeft.setImageResource(id);
        return this;
    }

    public TitleBuilder setLeftOnClickListner(View.OnClickListener listner){
        if(tvLeft.getVisibility() == View.VISIBLE){
            tvLeft.setOnClickListener(listner);
        }else if(ivLeft.getVisibility() == View.VISIBLE)
            ivLeft.setOnClickListener(listner);
        return this;
    }

    public TitleBuilder setRightTX(String text){
        tvRight.setVisibility(TextUtils.isEmpty(text)? View.GONE:View.VISIBLE);
        tvRight.setText(text);
        return this;
    }

    public TitleBuilder setRightIM(int id){
        ivRight.setVisibility(id > 0? View.VISIBLE:View.GONE);
        ivRight.setImageResource(id);
        return this;
    }

    public TitleBuilder setRightOnClickListner(View.OnClickListener listner){
        if(tvRight.getVisibility() == View.VISIBLE){
            tvRight.setOnClickListener(listner);
        }else if(ivRight.getVisibility() == View.VISIBLE)
            ivRight.setOnClickListener(listner);
        return this;
    }


}
