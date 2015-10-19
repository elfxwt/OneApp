package com.example.sophia_xu.contactLoader;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.SimpleCursorAdapter;

import com.example.sophia_xu.Utils.Logger;
import com.example.sophia_xu.oneapp.R;

/**
 * Created by sophia2 on 2015/9/24.
 */
public class ContactMainActivity extends FragmentActivity {  // 没有extents fragmentActivity!!!!!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_main);
    }



    @Override
    protected void onResume() {
        super.onResume();
        Logger.show("sophia", "contactMainActivity is onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.show("sophia","contactMainActivity is onStop");
    }
}
