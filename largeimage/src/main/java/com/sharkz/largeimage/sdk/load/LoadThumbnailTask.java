package com.sharkz.largeimage.sdk.load;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.util.Log;

import com.sharkz.largeimage.sdk.task.Task;

import static com.sharkz.largeimage.sdk.load.BlockImageLoader.LOAD_TYPE_THUMBNAIL;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.TAG;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.DEBUG;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:48
 * 描    述 获取图片的缩略图
 * 修订历史：
 * ================================================
 */
public class LoadThumbnailTask extends Task {

    private int scale;
    private int imageWidth;
    private int imageHeight;
    private BitmapRegionDecoder decoder;
    private LoadData loadData;
    private OnLoadStateChangeListener onLoadStateChangeListener;
    private OnImageLoadListener onImageLoadListener;
    private volatile Bitmap bitmap;
    private volatile Throwable throwable;

    LoadThumbnailTask(LoadData loadData, BitmapRegionDecoder decoder, int thumbnailScale, int imageWidth, int imageHeight, OnImageLoadListener onImageLoadListener, OnLoadStateChangeListener onLoadStateChangeListener) {
        this.loadData = loadData;
        this.scale = thumbnailScale;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.decoder = decoder;
        this.onImageLoadListener = onImageLoadListener;
        this.onLoadStateChangeListener = onLoadStateChangeListener;
        if (DEBUG)
            Log.d(TAG, "LoadThumbnailTask LoadThumbnailTask thumbnailScale:" + thumbnailScale);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (onLoadStateChangeListener != null) {
            onLoadStateChangeListener.onLoadStart(LOAD_TYPE_THUMBNAIL, null);
        }
    }

    @Override
    protected void doInBackground() {
        BitmapFactory.Options decodingOptions = new BitmapFactory.Options();
        decodingOptions.inSampleSize = scale;
        try {
            bitmap = decoder.decodeRegion(new Rect(0, 0, imageWidth, imageHeight),
                    decodingOptions);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            throwable = e;
        } catch (Exception e) {
            e.printStackTrace();
            throwable = e;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        onImageLoadListener = null;
        onLoadStateChangeListener = null;
        loadData = null;
        decoder = null;
        if (DEBUG) {
            Log.d(TAG, "onCancelled LoadThumbnailTask thumbnailScale:" + scale);
        }
    }

    @Override
    protected void onPostExecute() {
        super.onPostExecute();
        if (DEBUG) {
            Log.d(TAG, "LoadThumbnailTask bitmap:" + bitmap + " currentScale:" + scale + " bitW:" + (bitmap == null ? "" : bitmap.getWidth() + " bitH:" + bitmap.getHeight()));
        }
        loadData.thumbnailBlockData.task = null;
        if (bitmap != null) {
            if (loadData.thumbnailBlockData == null) {
                loadData.thumbnailBlockData = new BlockData();
            }
            loadData.thumbnailBlockData.bitmap = bitmap;
            if (onImageLoadListener != null) {
                onImageLoadListener.onBlockImageLoadFinished();
            }
        }
        if (onLoadStateChangeListener != null) {
            onLoadStateChangeListener.onLoadFinished(LOAD_TYPE_THUMBNAIL, null, throwable == null, throwable);
        }
        onImageLoadListener = null;
        onLoadStateChangeListener = null;
        loadData = null;
        decoder = null;
    }
}
