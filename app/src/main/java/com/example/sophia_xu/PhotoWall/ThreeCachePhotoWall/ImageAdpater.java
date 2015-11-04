package com.example.sophia_xu.PhotoWall.ThreeCachePhotoWall;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sophia_xu.Utils.Logger;
import com.example.sophia_xu.oneapp.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import libcore.io.DiskLruCache;

/**
 * Created by sophia2 on 2015/10/20.
 */
public class ImageAdpater extends BaseAdapter{

    private Context mContext;
    private LruCache<String,Bitmap> mCacheDatas;
    private String []imgUrls;

    private GridView mPhotoWall;
    private Set<BitmapWorkerTask> taskCollection;
    private DiskLruCache mDiskLruCache;

    private static final String TAG = "ThreeCacheImageAdapter";
    private int mItemHeight = 0;

    public ImageAdpater(Context context,String[] urls,GridView gridView){
        this.mContext = context;
        this.imgUrls = urls;
        mPhotoWall = gridView;
        taskCollection = new HashSet<BitmapWorkerTask>();

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory /8;
        mCacheDatas = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

        File cachDir = getDiskCacheDir(context,"thumb");
        if(!cachDir.exists()){
            cachDir.mkdirs();
        }

        try {
            mDiskLruCache = DiskLruCache.open(cachDir,getAppVersion(mContext),1,10*1020*1024); // init a 10M diskLru
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getDiskCacheDir(Context context,String uniqueName){
        String cachePath;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())||   // 还要查一下这个内置外置sd卡的东西吧，这个是判断
                !Environment.isExternalStorageRemovable()){
            cachePath = context.getExternalCacheDir().getPath();
        }else{
            cachePath = context.getCacheDir().getPath();
        }
        Log.d("sophia","cachePath is " + cachePath);
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return  info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;

    }

    @Override
    public int getCount() {
        return imgUrls.length;
    } // if return 0 ,then getview is not called

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
        Log.d(TAG,"get view");
        final String url = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.pw_gridview_item_three,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_iv_gvItem);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        if(viewHolder.imageView.getLayoutParams().height != mItemHeight){
            viewHolder.imageView.getLayoutParams().height = mItemHeight;
        }

        viewHolder.imageView.setTag(url);
        viewHolder.imageView.setImageResource(R.drawable.ic_launcher);
        loadBitmaps(viewHolder.imageView,url);


        return convertView;
    }

    static class ViewHolder{
        public ImageView imageView;
    }


    public void loadBitmaps(ImageView imageView,String imageUrl){

        Log.d(TAG,"enter to loadBitmaps");
        Bitmap bitmap = getBitmapfromMemoryCache(imageUrl);
        if(bitmap == null){
            BitmapWorkerTask task = new BitmapWorkerTask();
            taskCollection.add(task);
            task.execute(imageUrl);

        }else {
            if(imageView != null)
                imageView.setImageBitmap(bitmap);
        }
    }

    // to keep the height and width the same value
    public void setItemHeight(int height){
        Log.d("sophia","adapter setItemHeight");
        if(height == mItemHeight ){
            return;
        }
        mItemHeight = height;
        notifyDataSetChanged();
    }


    /*
      use md5 algorithm to encrypt key
     */

    public String hashKeyForDisk(String key){
        String cacheKey;
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            cacheKey = bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(key.hashCode());
        }

        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i =0;i < bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            Logger.show(TAG,"toHexString is "+hex);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }


    public Bitmap getBitmapfromMemoryCache(String key){
        return mCacheDatas.get(key);
    }

    public void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if(getBitmapfromMemoryCache(key) == null)
            mCacheDatas.put(key,bitmap);
    }


    public void cancelAllTasks(){
        if(taskCollection != null){
            for(BitmapWorkerTask task:taskCollection){
                task.cancel(false);
            }
        }
    }

    public void fluchCache(){
        if(mDiskLruCache != null){
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    class BitmapWorkerTask extends AsyncTask<String,Void,Bitmap>{
        private String imgUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d(TAG,"enter to AsyncTask to do things inbackground");
            imgUrl = params[0];
            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapshot = null;
            final String key = hashKeyForDisk(imgUrl);
            try {
                snapshot = mDiskLruCache.get(key);
                if(snapshot == null) {
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if(downloadUrlToStream(imgUrl,outputStream))
                            editor.commit();
                        else
                            editor.abort();

                    }
                    snapshot = mDiskLruCache.get(key);
                }
                if(snapshot != null){
                    fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }

                Bitmap bitmap = null;
                if(fileDescriptor != null){
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                }
                if(bitmap != null){
                    addBitmapToMemoryCache(imgUrl,bitmap);
                }
                return bitmap;


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // if I can think the imageview will be null if it is unvisible
            ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imgUrl);
            if(imageView != null &&bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
            taskCollection.remove(this);
        }

        private boolean downloadUrlToStream(String url,OutputStream outputStream){

            HttpURLConnection connection = null;
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            try {
                final URL netUrl = new URL(url);
                connection = (HttpURLConnection) netUrl.openConnection();
                in = new BufferedInputStream(connection.getInputStream(),8*1024);
                out = new BufferedOutputStream(outputStream,8 * 1024);
                int b;
                while((b = in.read())!= -1){
                    out.write(b);
                }
                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection != null)
                    connection.disconnect();

                    try {
                        if(out != null) {
                            out.close();
                        }
                        if(in != null)
                            in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }

            return false;

        }
    }
}
