package com.example.sophia_xu.WeiboStudy.base;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Sophia_Xu on 2015/8/3.
 */
public class BaseApplication extends Application{

    public int maxFileCount = 3;

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader(this);
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileCount(maxFileCount)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .build(); // 这里的diskCacheFileCount?

        ImageLoader.getInstance().init(config);
    }
}
