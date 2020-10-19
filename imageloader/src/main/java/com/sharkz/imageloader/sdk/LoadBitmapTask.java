package com.sharkz.imageloader.sdk;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  14:25
 * 描    述
 * 修订历史：
 * ================================================
 */
public class LoadBitmapTask implements Runnable {

    private static final int MESSAGE_POST_RESULT = 1;
    private boolean mIsDiskLruCacheCreated = false;
    private String url;
    private int reqWidth;
    private int reqHeight;
    private Handler mMainHandler;
    private ImageView imageView;
    private BitmapCallback callback;


    // =============================================================================================


    /**
     * 用于Handler处理的构造函数
     *
     * @param handler     处理器
     * @param imageView   显示图片的控件
     * @param url         图片地址
     * @param reqWidth    图片宽度
     * @param reqHeight   图片高度
     */
    public LoadBitmapTask(Handler handler, ImageView imageView, String url, int reqWidth, int reqHeight) {
        this.mMainHandler =handler;
        this.url=url;
        this.reqHeight =reqHeight;
        this.reqWidth =reqWidth;
        this.imageView =imageView;
    }

    /**
     * 用于回调bitmap的重载构造函数
     *
     * @param callback   回调
     * @param url        图片地址
     * @param reqWidth   图片宽度
     * @param reqHeight  图片高度
     */
    public LoadBitmapTask(BitmapCallback callback, String url, int reqWidth, int reqHeight) {
        this.callback =callback;
        this.url=url;
        this.reqHeight =reqHeight;
        this.reqWidth =reqWidth;
    }


    // =============================================================================================


    @Override
    public void run() {
        // 从本地或者网络获取bitmap
        final Bitmap bitmap =loadBitmap(url, reqWidth, reqHeight);
        // 直接显示图片到控件
        if(mMainHandler!=null){
            TaskResult loaderResult = new TaskResult(imageView,url,bitmap);
            mMainHandler.obtainMessage(MESSAGE_POST_RESULT,loaderResult).sendToTarget();
        }
        // bitmap回调
        if(callback !=null){
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(bitmap);
                }
            });
        }
    }


    // =============================================================================================


    /**
     * 从本地或者网络去获取bitmap
     *
     * @param url url
     * @param reqWidth 需求宽度
     * @param reqHeight 需求高度
     * @return bitmap
     */
    private Bitmap loadBitmap(String url, int reqWidth, int reqHeight){
        Bitmap bitmap = null;
        // 从磁盘里面加载
        bitmap= ImageDiskLruCache.getInstance().loadBitmap(url, reqWidth, reqHeight);

        //添加到内存缓存中
        if (bitmap!=null){
            ImageLruCache.getInstance().addBitmapToMemoryCache(MD5Utils.hashKeyFromUrl(url), bitmap);
        }

        // 保险一下
        if(bitmap==null &&!mIsDiskLruCacheCreated){
            // Log.i(TAG, "sd卡满了，直接从网络获取");
            //如果sd卡已满，无法使用本地缓存，则通过网络下载bitmap，一般不会调用这一步
            bitmap = NetRequest.downloadBitmapFromUrl(url);
        }
        return bitmap;
    }


}
