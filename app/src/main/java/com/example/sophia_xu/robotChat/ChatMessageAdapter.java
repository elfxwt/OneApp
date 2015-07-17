package com.example.sophia_xu.robotChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Sophia_Xu on 2015/7/16.
 */
public class ChatMessageAdapter extends BaseAdapter{

    List<ChatMessage> datas;
    LayoutInflater layoutInflater;
    Context context;

    public ChatMessageAdapter(Context context,List<ChatMessage> mDatas){
        layoutInflater =LayoutInflater.from(context);
        datas = mDatas;

    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }







    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
