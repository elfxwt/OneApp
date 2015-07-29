package com.example.sophia_xu.robotChat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sophia_xu.oneapp.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Sophia_Xu on 2015/7/16.
 */
public class ChatMessageAdapter extends BaseAdapter{

    List<ChatMessage> mDatas;
    LayoutInflater layoutInflater;
    Context context;
    private int typeKinds = 2;

    public ChatMessageAdapter(Context context,List<ChatMessage> datas){
        this.context = context;
        this.layoutInflater =LayoutInflater.from(context);
        this.mDatas = datas;

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
    public int getViewTypeCount() {
        return typeKinds;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if(chatMessage.getType() == ChatMessage.Type.INCOMING)
            return 0;
        return 1;

    }

    /*  普通的item 布局只需要四个方法，
    但是我们这里的item布局有两种,所以上面两个方法是额外的
    思考微信那种布局，有文字，图片，分享之类的不同item布局，有标示标记然后传入，再inflate不同的item layout*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage msg = mDatas.get(position);
        if(msg == null)
        {
            Log.d("sophia","msg is null?");
            return null;
        }
        ViewHolder viewHolder = null;
        if(convertView == null){
            /*inflate 最后一个参数，true false的值*/
            switch(getItemViewType(position)){
                case 0:
                    convertView = layoutInflater.inflate(R.layout.robotchat_robot_item,parent,false);
                    viewHolder = new ViewHolder();
                    viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_robot_msg_date);
                    viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_robot_msg);
                    convertView.setTag(viewHolder);
                    break;
                case 1:
                    convertView = layoutInflater.inflate(R.layout.robotchat_person_item,parent,false);
                    viewHolder = new ViewHolder();
                    viewHolder.mDate = (TextView) convertView.findViewById(R.id.id_person_msg_date);
                    viewHolder.mMsg = (TextView) convertView.findViewById(R.id.id_person_msg);
                    convertView.setTag(viewHolder);
                    break;
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDate.setText(format.format(msg.getDate()));
        viewHolder.mMsg.setText(msg.getMsg());
        return convertView;
    }

    // 只讲可变的地方 变成 viewholder
    private final class ViewHolder{
        TextView mDate;
        TextView mMsg;
    }
}
