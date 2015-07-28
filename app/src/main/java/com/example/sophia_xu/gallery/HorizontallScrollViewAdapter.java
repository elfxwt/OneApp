package com.example.sophia_xu.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sophia_xu.oneapp.R;

import java.util.List;

/**
 * Created by Sophia_Xu on 2015/7/24.
 */
public class HorizontallScrollViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Integer> mDatas;
    private List<String> mTexts;
    private LayoutInflater mInflater;

    public HorizontallScrollViewAdapter(Context context,List<Integer> datas,List<String> texts){
        this.mContext = context;
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
        this.mTexts = texts;
    }
    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.gallery_item,parent,false);
            viewHolder.mImag = (ImageView) convertView.findViewById(R.id.id_item_image);
            viewHolder.mText = (TextView) convertView.findViewById(R.id.id_item_text);
            convertView.setTag(viewHolder);


        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImag.setImageResource(mDatas.get(position));
        viewHolder.mText.setText(mTexts.get(position));


        return convertView;
    }


    private class ViewHolder{
        ImageView mImag;
        TextView mText;
    }
}
