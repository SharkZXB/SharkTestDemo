package com.sharkz.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.sharkz.imageloader.sdk.BitmapCallback;
import com.sharkz.imageloader.sdk.ImageDiskLruCache;
import com.sharkz.imageloader.sdk.ImageLruCache;
import com.sharkz.imageloader.sdk.ImageThreadPoolExecutor;
import com.sharkz.imageloader.sdk.LoadBitmapTask;
import com.sharkz.imageloader.sdk.TaskHandler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  13:17
 * 描    述 图片加载器   https://github.com/wenhuaijun/EasyImageLoader
 * 修订历史：
 * ================================================
 */
public class ImageLoader {

    private static ImageLoader instance = null;                           // 单列
    private static Context context;                                     // 全局的上下文

    private static ImageLruCache imageLrucache;                         // 图片内存缓存类
    private static ImageDiskLruCache imageDiskLrucache;                 // 图片磁盘缓存类
    private static ThreadPoolExecutor THREAD_POOL_EXECUTOR = null;      // 创建一个静态的线程池对象
    private static TaskHandler mMainHandler;                            // 创建一个更新ImageView的UI的Handler


    // =============================================================================================


    /**
     * 这个需要在 Application 中初始化
     *
     * @param context 上下文
     */
    public static void initContext(Context context) {
        ImageLoader.context = context;
    }

    /**
     * SDK 初始化 传入的上下文
     *
     * @return
     */
    public static Context getContext() {
        if (ImageLoader.context == null) {
            throw new NullPointerException("没有初始化你就调用 不空指针才怪 --> ImageLoader.initContext()");
        }
        return ImageLoader.context;
    }

    /**
     * 获取单列
     *
     * @return
     */
    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    /**
     * 私有的构造方法，防止在外部实例化该ImageLoader 初始化的时候准备原料
     */
    private ImageLoader() {
        THREAD_POOL_EXECUTOR = ImageThreadPoolExecutor.getInstance();
        imageLrucache = ImageLruCache.getInstance();
        imageDiskLrucache = ImageDiskLruCache.getInstance();
        mMainHandler = TaskHandler.getInstance();
    }


    // =============================================================================================


    /**
     * 显示图片
     *
     * @param url       网络地址
     * @param imageView 显示图片的控件
     */
    public void displayBitmap(final String url, final ImageView imageView) {
        displayBitmap(url, imageView, 0, 0);
    }

    /**
     * 显示图片
     *
     * @param url       网络地址
     * @param imageView 显示图片的控件
     * @param reqWidth  宽度
     * @param reqHeight 高度
     */
    public void displayBitmap(final String url, final ImageView imageView, final int reqWidth, final int reqHeight) {
        // 设置加载loadding图片
        imageView.setImageResource(R.drawable.ic_loading);
        // 防止加载图片的时候数据错乱
        // imageView.setTag(TAG_KEY_URI, uri);
        imageView.setTag(url);

        // 从内存缓存中获取bitmap
        Bitmap bitmap = imageLrucache.loadBitmapFromMemCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        // 从磁盘中加载缓存
        bitmap = imageDiskLrucache.loadBitmapFromDiskCache(url, 0, 0);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        // 当前图片没有缓存
        LoadBitmapTask loadBitmapTask = new LoadBitmapTask(mMainHandler, imageView, url, reqWidth, reqHeight);
        //使用线程池去执行Runnable对象
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }


    /**
     * @param url
     * @param callback
     */
    public void getBitmap(final String url, BitmapCallback callback) {
        getBitmap(url, callback, 0, 0);
    }

    /**
     * @param url       图片链接
     * @param callback  bitmap回调接口
     * @param reqWidth  需求宽度
     * @param reqHeight 需求高度
     */
    public void getBitmap(final String url, final BitmapCallback callback, int reqWidth, int reqHeight) {
        //从内存缓存中获取bitmap
        final Bitmap bitmap = imageLrucache.loadBitmapFromMemCache(url);
        if (bitmap != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(bitmap);
                }
            });
            return;
        }
        LoadBitmapTask loadBitmapTask = new LoadBitmapTask(callback, url, reqWidth, reqHeight);
        //使用线程池去执行Runnable对象
        THREAD_POOL_EXECUTOR.execute(loadBitmapTask);
    }


}
