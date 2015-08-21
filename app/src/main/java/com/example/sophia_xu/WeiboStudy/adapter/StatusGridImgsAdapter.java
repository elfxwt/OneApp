package com.example.sophia_xu.WeiboStudy.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.sophia_xu.WeiboStudy.entity.PicUrls;
import com.example.sophia_xu.oneapp.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sophia_Xu on 2015/8/20.
 */
public class StatusGridImgsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<PicUrls> mDatas;
    private ImageLoader imageLoader;

    public StatusGridImgsAdapter(Context mContext,ArrayList<PicUrls> mdatas){
        this.mContext = mContext;
        this.mDatas = mdatas;
        imageLoader=ImageLoader.getInstance();
    }


    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public PicUrls getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder ; // why final
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_grid_image,null);
            holder.imgview = (ImageView) convertView.findViewById(R.id.id_iv_image);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();

        GridView gv = (GridView) parent;
        int horizontalSpacing = gv.getHorizontalSpacing();
        int numcolumns = gv.getNumColumns();
        int itemWidth =(gv.getWidth() - (numcolumns -1)* horizontalSpacing- gv.getPaddingLeft()
                    - gv.getPaddingRight() ) / numcolumns;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(itemWidth,itemWidth);
        holder.imgview.setLayoutParams(params);

        imageLoader.displayImage(getItem(position).getThumbnail_pic(),holder.imgview);
        return convertView;
    }

    private class ViewHolder{
        ImageView imgview;
    }
}
