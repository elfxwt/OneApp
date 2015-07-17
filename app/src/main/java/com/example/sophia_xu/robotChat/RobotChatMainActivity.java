package com.example.sophia_xu.robotChat;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sophia_xu.oneapp.R;

import java.util.List;

public class RobotChatMainActivity extends Activity {



    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mDatas;


    private EditText mInputMsg;
    private Button mSendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robotchat_main);

        initView();
        initDatas();



        new Thread(new Runnable() {
            @Override
            public void run() {
                testHttpUtils();

            }
        });



    }

    private void initDatas() {


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
