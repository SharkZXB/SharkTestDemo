package com.sharkz.largeimage.sdk.load;

import android.graphics.BitmapRegionDecoder;
import android.util.Log;

import com.sharkz.largeimage.sdk.factory.BitmapDecoderFactory;
import com.sharkz.largeimage.sdk.task.Task;

import static com.sharkz.largeimage.sdk.load.BlockImageLoader.LOAD_TYPE_INFO;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.TAG;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.DEBUG;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:53
 * 描    述 获取图片的详情
 * 修订历史：
 * ================================================
 */
public class LoadImageInfoTask extends Task {

    private BitmapDecoderFactory mFactory;
    private LoadData imageInfo;
    private OnLoadStateChangeListener onLoadStateChangeListener;
    private OnImageLoadListener onImageLoadListener;
    private volatile BitmapRegionDecoder decoder;
    private volatile int imageWidth;
    private volatile int imageHeight;
    private volatile Exception e;

    LoadImageInfoTask(LoadData loadData, OnImageLoadListener onImageLoadListener, OnLoadStateChangeListener onLoadStateChangeListener) {
        this.imageInfo = loadData;
        mFactory = imageInfo.mFactory;
        this.onImageLoadListener = onImageLoadListener;
        this.onLoadStateChangeListener = onLoadStateChangeListener;
        if (DEBUG) {
            Log.d(TAG, "start LoadImageInfoTask:imageW:" + imageWidth + " imageH:" + imageHeight);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (onLoadStateChangeListener != null) {
            onLoadStateChangeListener.onLoadStart(LOAD_TYPE_INFO, null);
        }
    }

    @Override
    protected void doInBackground() {
        try {
            decoder = mFactory.made();
            imageWidth = decoder.getWidth();
            imageHeight = decoder.getHeight();
            if (DEBUG) {
                Log.d(TAG, "LoadImageInfoTask doInBackground");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.e = e;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        onLoadStateChangeListener = null;
        onImageLoadListener = null;
        mFactory = null;
        imageInfo = null;
        if (DEBUG) {
            Log.d(TAG, "LoadImageInfoTask: onCancelled");
        }
    }

    @Override
    protected void onPostExecute() {
        super.onPostExecute();
        if (DEBUG) {
            Log.d(TAG, "onPostExecute LoadImageInfoTask:" + e + " imageW:" + imageWidth + " imageH:" + imageHeight + " e:" + e);
        }
        imageInfo.task = null;
        if (e == null) {
            imageInfo.imageWidth = imageWidth;
            imageInfo.imageHeight = imageHeight;
            imageInfo.mDecoder = decoder;
            onImageLoadListener.onLoadImageSize(imageWidth, imageHeight);
        } else {
            onImageLoadListener.onLoadFail(e);
        }
        if (onLoadStateChangeListener != null) {
            onLoadStateChangeListener.onLoadFinished(LOAD_TYPE_INFO, null, e == null, e);
        }
        onLoadStateChangeListener = null;
        onImageLoadListener = null;
        mFactory = null;
        imageInfo = null;
    }
}
