package com.sharkz.imageloader.sdk;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  13:23
 * 描    述 图片内存缓存处理对象
 * 修订历史：
 * ================================================
 */
public class ImageLruCache extends LruCache<String, Bitmap> {


    private static ImageLruCache imageLruCache;

    /**
     *  获取分配给该应用的最大内存
     */
    public static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    /**
     * lruChache能获取的缓存大小为整个应用内存的八分之一
     */
    public static int cacheSize = maxMemory / 8;


    // =============================================================================================


    public static ImageLruCache getInstance() {
        if (imageLruCache == null) {
            synchronized (ImageLruCache.class) {
                if (imageLruCache == null) {
                    imageLruCache = new ImageLruCache();
                }
            }
        }
        return imageLruCache;
    }


    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private ImageLruCache(int maxSize) {
        super(maxSize);
    }

    private ImageLruCache() {
        super(cacheSize);
    }


    // =============================================================================================


    @Override
    protected int sizeOf(String key, Bitmap value) {
        //return value.getRowBytes()*value.getHeight()/
        return value.getByteCount() / 1024;
    }


    // =============================================================================================


    /**
     * 添加bitmap到内存缓存中
     *
     * @param key    key
     * @param bitmap bitmap
     */
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            this.put(key, bitmap);
        }
    }

    /**
     * 从内存缓存中获取bitmap
     *
     * @param key key
     * @return bitmap
     */
    private Bitmap getBitmapFromMemCache(String key) {
        return this.get(key);
    }

    /**
     * 通过url获取内存缓存
     *
     * @param url 图片网络地址
     */
    public Bitmap loadBitmapFromMemCache(String url) {
        final String key = MD5Utils.hashKeyFromUrl(url);
        Bitmap bitmap = getBitmapFromMemCache(key);
        return bitmap;
    }

    /**
     * 删除一个具体的缓存
     */
    public void removeCacheWithKey(String key) {
        this.remove(key);
    }

    /**
     * 清空内存缓存
     */
    public void clearLruCache2() {
        this.evictAll();
    }

}
