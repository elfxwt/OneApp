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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sophia_Xu on 2015/9/8.
 *
 * 在这里尝试 了内存的lrucache ,以及bitmapfactory.option的option参数
 * 但效果并不佳，可能跟实际参数有关。
 * 另外遇到了 在两次进行 inputstream的时候会遇到的问题，学习。
 *
 *
 *
 */
public class PhotoWallAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private LruCache<String,Bitmap> mCacheDatas;
    private String []imgUrls;

    private GridView mPhotoWall;
    private Set<BitmapWorkerTask> taskCollection;

    private int mFirstVisibleItem;
    private int mVisibleItemCount;

    private boolean isFirstEnter = true;
    private final static int BITMAP_REQ_WIDTH = 270;
    private final static int BITMAP_REQ_HEIGHT =  270;

    public PhotoWallAdapter(Context context,String []datas,GridView photowall) {
        imgUrls = datas;
        mPhotoWall = photowall;
        mContext = context;
        taskCollection = new HashSet<BitmapWorkerTask>();
        int maxMemory = (int)Runtime.getRuntime().maxMemory();
        Logger.show("maxmemory",maxMemory / 1024+"");
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
        ViewHolder viewHolder = null;
        if(convertView ==null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.pw_gridview_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_iv_gvItem);
            convertView.setTag(viewHolder);
        }else
           viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.imageView.setTag(url);
        setImageView(url,viewHolder.imageView);

        return convertView; // return view not return null
    }

    private void setImageView(String url, ImageView imageView) {
        Bitmap bitmap = getBitmapfromMemoryCache(url);
        if(bitmap !=null)
            imageView.setImageBitmap(bitmap);
        else
            imageView.setImageResource(R.drawable.ic_launcher);

    }

   static class ViewHolder{
        public ImageView imageView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Logger.show("sophia","onscrollstatechanged");
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

        Logger.show("sophia","onscroll" + "firstVisibleItem = " + mFirstVisibleItem + " mvisibleitemcount=" + mVisibleItemCount);

        if(isFirstEnter && visibleItemCount > 0){
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
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                is = con.getInputStream();
//                bitmap = BitmapFactory.decodeStream(is);
                // 这里还可以加上对图片的 压缩处理
                bitmap = getResizeBitmapFromStream(is);
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

         private Bitmap getResizeBitmapFromStream(InputStream is) {
             BitmapFactory.Options opt = new BitmapFactory.Options();
             Bitmap resultBitmap = null;
             BufferedInputStream bufferedIs = new BufferedInputStream(is);// is will not be supported
             opt.inJustDecodeBounds = true;
             HttpURLConnection secondCon = null;

             InputStream secondIs = null;
             try {
                 BitmapFactory.decodeStream(is, null, opt);

//                 opt.inSampleSize = caclulateInSampleSize(opt, BITMAP_REQ_WIDTH, BITMAP_REQ_HEIGHT);  // 设置了insamplesize 让图片更加模糊。。
                 Logger.show("sophia","insamplesize is " + opt.inSampleSize);
                 // to resize the bitmap from the density
                 int targetDensity = mContext.getResources().getDisplayMetrics().densityDpi;
                 Logger.show("sophia","targetDensity is " + targetDensity);
                 opt.inScaled = true;
                 opt.inDensity = targetDensity * (caclulateInSampleSize(opt, BITMAP_REQ_WIDTH, BITMAP_REQ_HEIGHT));
                 opt.inTargetDensity = targetDensity;
                 opt.inJustDecodeBounds = false;

//                 is.reset();
//                 resultBitmap = BitmapFactory.decodeStream(bufferedIs, null, opt);
                 if(is.markSupported()){
                     is.reset();
                     resultBitmap = BitmapFactory.decodeStream(is, null, opt);
                 }else
                 {
                     Logger.show("sophia","inputstream is not marksupported");
                     URL url = new URL(imgUrl);
                     secondCon = (HttpURLConnection) url.openConnection();
                     secondCon.setConnectTimeout(5 * 1000);
                     secondCon.setReadTimeout(10 * 1000);
                     secondIs = secondCon.getInputStream();
                     resultBitmap = BitmapFactory.decodeStream(secondIs,null,opt);
                 }
//


             }catch (Exception e){
                 e.printStackTrace();
             }finally {
                 if(secondIs!=null)
                     try {
                         secondIs.close();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 if(secondCon !=null)
                     secondCon.disconnect();
             }
             return resultBitmap;



         }

         //this way can be resize the bitmap in any size and that will make the bitmap changeshape
         // there is another way to resize bitmap in the same ratio
         private int caclulateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
            final int height = options.outHeight;
             final int width = options.outWidth;
                int finalRatio = 1;
             if(height > reqHeight || width > reqWidth){
                 final int heightRatio = Math.round((float)height/(float)reqHeight);
                 final int widthRatio = Math.round((float)width/(float)reqWidth);
                 finalRatio = heightRatio < widthRatio?widthRatio:heightRatio;
             }
             return finalRatio;



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
