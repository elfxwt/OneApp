package com.example.sophia_xu.robotChat;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sophia_xu.oneapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RobotChatMainActivity extends Activity {



    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mDatas;
    private int offset = 80;


    private EditText mInputMsg;
    private Button mSendBtn;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ChatMessage message = (ChatMessage) msg.obj;
            mDatas.add(message);
            mAdapter.notifyDataSetChanged();
            mMsgs.smoothScrollByOffset(offset);

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robotchat_main);

        initView();
        initDatas();
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String msg = mInputMsg.getText().toString();  // 这里要是final 类型的？因为在匿名内部类中不能

                if(TextUtils.isEmpty(msg)){
                    Toast.makeText(RobotChatMainActivity.this,"the msg is null",Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage sendMsg = new ChatMessage();
                sendMsg.setMsg(msg);
                sendMsg.setType(ChatMessage.Type.OUTCOMING);
                sendMsg.setDate(new Date());
                mDatas.add(sendMsg);
                mAdapter.notifyDataSetChanged();
                mInputMsg.setText("");
                mMsgs.smoothScrollByOffset(40);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       ChatMessage replyMsg = HttpUtils.sendMsg(msg);
                        Message msg = Message.obtain();
                        msg.obj = replyMsg;
                        handler.sendMessage(msg);

                    }
                }).start();
            }
        });


    }

    private void initDatas() {
        mDatas = new ArrayList<ChatMessage>();
        mDatas.add(new ChatMessage(ChatMessage.Type.INCOMING,new Date(),"Hello,this is Robot"));
        mDatas.add(new ChatMessage(ChatMessage.Type.OUTCOMING,new Date(),"hello"));
        mAdapter = new ChatMessageAdapter(this,mDatas);
        mMsgs.setAdapter(mAdapter);



    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.id_chat_listview);
        mInputMsg = (EditText) findViewById(R.id.id_edit_msg);
        mSendBtn = (Button) findViewById(R.id.id_send_msg);

    }


    public void testHttpUtils(){
        String res = HttpUtils.doGet("给我讲一笑话");
        Log.d("sophia robot chat",res);
        String res2 = HttpUtils.doGet("给我讲一笑话");
        Log.d("sophia robot chat",res2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_robot_chat_main, menu);
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
