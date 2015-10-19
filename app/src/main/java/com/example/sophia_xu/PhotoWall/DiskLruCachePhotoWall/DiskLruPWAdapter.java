package com.example.sophia_xu.PhotoWall.DiskLruCachePhotoWall;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.example.sophia_xu.Utils.Logger;

/**
 * Created by sophia2 on 2015/9/30.
 */
public class DiskLruPWAdapter extends BaseAdapter implements AbsListView.OnScrollListener{

    private Context mContext;
    private LruCache<String,Bitmap> lruCache ;

    public DiskLruPWAdapter(Context context){
        this.mContext = context;
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        Logger.show("maxmemory", maxMemory / 1024 + "");
        int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
