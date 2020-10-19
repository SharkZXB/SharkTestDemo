package com.sharkz.imageloader.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import com.jakewharton.disklrucache.DiskLruCache;
import com.sharkz.imageloader.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  13:37
 * 描    述 磁盘缓存
 * 修订历史：
 * ================================================
 */
public class ImageDiskLruCache {

    private DiskLruCache mDiskLruCache;
    private  boolean mIsDiskLruCacheCreated;                        // 是否初始化磁盘缓存对象
    private static final long DISK_CACHE_SIZE = 1024*1024*50;       // 磁盘缓存大小
    public static final int DISK_CACHE_INDEX = 0;                  // 缓存下标
    private static ImageDiskLruCache diskLruCache;

    // =============================================================================================

    public static ImageDiskLruCache getInstance(){
        if (diskLruCache==null){
            synchronized (ImageDiskLruCache.class){
                if (diskLruCache==null){
                    diskLruCache = new ImageDiskLruCache();
                }
            }
        }
        return diskLruCache;
    }

    /**
     * 私有构造方法
     */
    private ImageDiskLruCache(){
        File diskCacheDir = getDiskCacheDir(ImageLoader.getContext(), "bitmap");
        if(!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }
        // sd卡的可用空间大于本地缓存的大小
        if(getUsableSpace(diskCacheDir)>DISK_CACHE_SIZE){
            try {
                //实例化diskLruCache
                mDiskLruCache =DiskLruCache.open(diskCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated =true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            mIsDiskLruCacheCreated =false;
        }
    }


    // =============================================================================================


    /**
     * 加载 Bitmap
     * 先从缓存中加载
     * 如果没有那就网络下载
     *
     * @return bitmap
     */
    public synchronized Bitmap loadBitmap(String url, int reqWidth, int reqHeight){
        Bitmap bitmap = null;
        bitmap=loadBitmapFromDiskCache(url, reqWidth, reqHeight); // 从缓存中加载
        if (bitmap==null){
            // 网络请求
            try {
                downloadImageToDiskCache(url); // 通过URL将图片保存早本地
                bitmap=loadBitmapFromDiskCache(url, reqWidth, reqHeight); // 从缓存中加载
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    /**
     * 从本地缓存加载bitmap
     *
     * @param url           图片地址
     * @param reqWidth      图片宽度
     * @param reqHeight     图片高度
     * @return              经过处理的Bitmap
     */
    public Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight)  {
        if(mDiskLruCache ==null){
            return null;
        }
        Bitmap bitmap=null;
        String key = MD5Utils.hashKeyFromUrl(url);
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(key);
            if(snapshot!=null){
                FileInputStream fileInputStream =(FileInputStream)snapshot.getInputStream(DISK_CACHE_INDEX);
                if(reqWidth<=0||reqHeight<=0){
                    // 不压缩图片
                    bitmap = BitmapFactory.decodeFileDescriptor(fileInputStream.getFD());
                }else{
                    //按需求分辨率压缩图片
                    bitmap =BitmapUtils.getSmallBitmap(fileInputStream.getFD(),reqWidth,reqHeight);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 下载图片放入本地缓存
     *
     * @param urlString 下载图片的链接
     * @throws IOException
     */
    private void downloadImageToDiskCache(String urlString) throws IOException {
        if(mDiskLruCache == null){
            return ;
        }
        String key =MD5Utils.hashKeyFromUrl(urlString);
        DiskLruCache.Editor editor =mDiskLruCache.edit(key);
        if(editor != null){
            //打开本地缓存的输入流
            OutputStream outputStream =editor.newOutputStream(DISK_CACHE_INDEX);
            //将从网络下载并写入输出流中
            if(NetRequest.downloadUrlToStream(urlString,outputStream)){
                // 提交数据，并是释放连接
                editor.commit();
            }else{
                // 释放连接
                editor.abort();
            }
            mDiskLruCache.flush();
        }
    }


    /**
     * 获取本地缓存的File目录
     *
     * @param context       上下文
     * @param uniqueName    保存文件的文件夹名字
     */
    private File getDiskCacheDir(Context context, String uniqueName){
        //判断是否含有sd卡
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if(externalStorageAvailable){
            cachePath =context.getExternalCacheDir().getPath();
        }else{
            //获取app自带的缓存目录
            cachePath =context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator+uniqueName);
    }

    /**
     * 获取该目录可用空间大小
     *
     * @param path 路径
     */
    private long getUsableSpace(File path){
       // if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.GINGERBREAD){
       //     return path.getUsableSpace();
       //  }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize()*(long) stats.getAvailableBlocks();
    }

    /**
     * 返回磁盘缓存对象
     */
    public DiskLruCache getDiskLruCache() {
        return mDiskLruCache;
    }

    /**
     * 当前是否已经初始化了
     */
    public boolean ismIsDiskLruCacheCreated() {
        return mIsDiskLruCacheCreated;
    }



}
