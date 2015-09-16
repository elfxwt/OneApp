package com.example.sophia_xu.PhotoWall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sophia_xu.Utils.Logger;
import com.example.sophia_xu.oneapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

/**
 * Created by Sophia_Xu on 2015/9/8.
 */
public class PhotoWallAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
    private Context mContext;
    private LruCache<String,Bitmap> mCacheDatas;
    private String []imgUrls;

    private GridView mPhotoWall;
    private Set<BitmapWorkerTask> taskCollection;

    private int mFirstVisibleItem;
    private int mVisibleItemCount;

    private boolean isFirstEnter = true;

    public PhotoWallAdapter(Context context,String []datas) {
        imgUrls = datas;
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        Logger.show("maxmemory",maxMemory+"");
        int cacheSize = maxMemory / 8;
        mCacheDatas = new LruCache<String,Bitmap>(cacheSize){
            protected int sizeof(String key,Bitmap bitmap){
                return bitmap.getByteCount();
            }
        };
        mPhotoWall.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return imgUrls.length;
    }

    @Override
    public String getItem(int position) {
        return imgUrls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = getItem(position);
        View view = null;
        ViewHolder viewHolder = null;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            view = View.inflate(mContext, R.layout.pw_gridview_item,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.id_iv_gvItem);
            convertView.setTag(viewHolder);
        }else
           viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.imageView.setTag(url);
        setImageView(url,viewHolder.imageView);

        return null;
    }

    private void setImageView(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapfromMemoryCache(url);
        if(bitmap !=null)
            imageView.setImageBitmap(bitmap);
        else
            imageView.setImageResource(R.drawable.ic_launcher);

    }

    class ViewHolder{
        public ImageView imageView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            loadBitmap(mFirstVisibleItem,mVisibleItemCount);
        }else
            cancleAllTasks();

    }

    // 这个 scroll 调用的时机是什么？一进入就会调用是么？
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;

        if(isFirstEnter && visibleItemCount == 0){
            loadBitmap(mFirstVisibleItem,mVisibleItemCount);
            isFirstEnter = false;
        }

    }


   public Bitmap getBitmapfromMemoryCache(String key){
       return mCacheDatas.get(key);
   }

    public void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapfromMemoryCache(key) == null)
            mCacheDatas.put(key,bitmap);
    }


    private void loadBitmap(int firstVisibleItem,int visibleItemCount){
        String imgUrl = null;
        try{
            for(int i = firstVisibleItem;i < visibleItemCount;i++){
                imgUrl = imgUrls[i];
                Bitmap bitmap = getBitmapfromMemoryCache(imgUrl);
                if(bitmap == null){
                    BitmapWorkerTask workerTask = new BitmapWorkerTask();
                    taskCollection.add(workerTask);
                    workerTask.execute(imgUrl);

                }else{
                    ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imgUrl);
                    if(imageView !=null)
                        imageView.setImageBitmap(bitmap);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void cancleAllTasks(){
        if(taskCollection != null){
            for(BitmapWorkerTask task:taskCollection){
                task.cancel(false);
            }

        }
    }

/*

之所以要把此类放到内部类里，是因为要操作同一个容器 lruCache, 其实可以通过构造函数传递过来的。
 */

     class BitmapWorkerTask extends AsyncTask<String,Void,Bitmap> {


        private String imgUrl;

        @Override
        protected Bitmap doInBackground(String[] params) {
            imgUrl = params[0];
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            InputStream is = null;
            try {
                URL url = new URL(imgUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5*1000);
                con.setReadTimeout(10 * 1000);
                is = con.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(is!=null)
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                if(con !=null)
                    con.disconnect();
            }
            if(bitmap != null){
                addBitmapToMemoryCache((String)params[0],bitmap);
            }

            return bitmap;
        }



         protected void onPostExecute(Bitmap bitmap) {
             super.onPostExecute(bitmap);

             ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imgUrl);
             if(imageView != null && bitmap != null)
                imageView.setImageBitmap(bitmap);
             taskCollection.remove(this);
         }



     }
}
