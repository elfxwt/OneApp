package com.example.sophia_xu.contactLoader;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.SimpleCursorAdapter;

import com.example.sophia_xu.oneapp.R;

/**
 * Created by sophia2 on 2015/9/24.
 */
public class ContactListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter mCursorAdapter ;
    static final Uri baseUri =ContactsContract.Contacts.CONTENT_URI;

    static final String[] COLUMNS_SHOW = new String[]{ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.CONTACT_STATUS};
    static final int[] LAYOUT_ITEM = new int[]{R.id.id_entry_name,R.id.id_entry_status};


    public ContactListFragment(){super();};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText("当前没有联系人"); // listfragment’s feature
        mCursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.contact_item,null,COLUMNS_SHOW,LAYOUT_ITEM,0);
        setListAdapter(mCursorAdapter);
        getLoaderManager().initLoader(1,null,this);

    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),baseUri, null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }


}
