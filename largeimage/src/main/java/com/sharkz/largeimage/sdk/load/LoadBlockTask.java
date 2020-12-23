package com.sharkz.largeimage.sdk.load;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;

import com.sharkz.largeimage.sdk.task.Task;

import static com.sharkz.largeimage.sdk.load.BlockImageLoader.BASE_BLOCKSIZE;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.LOAD_TYPE_BLOCK;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.TAG;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.DEBUG;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.acquireBitmap;
import static com.sharkz.largeimage.sdk.load.BlockImageLoader.bitmapPool;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  15:03
 * 描    述 加载任务
 * 修订历史：
 * ================================================
 */
public class LoadBlockTask extends Task {

    private int scale;
    private BlockData blockData;
    private Position position;
    private int imageWidth;
    private int imageHeight;
    private BitmapRegionDecoder decoder;
    private OnLoadStateChangeListener onLoadStateChangeListener;
    private OnImageLoadListener onImageLoadListener;
    private volatile Rect clipImageRect;
    private volatile Bitmap bitmap;
    private volatile Throwable throwable;

    LoadBlockTask(Position position, BlockData blockData, int scale, int imageWidth, int imageHeight, BitmapRegionDecoder decoder, OnImageLoadListener onImageLoadListener, OnLoadStateChangeListener onLoadStateChangeListener) {
        this.blockData = blockData;
        this.scale = scale;
        this.position = position;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.decoder = decoder;
        this.onImageLoadListener = onImageLoadListener;
        this.onLoadStateChangeListener = onLoadStateChangeListener;
        if (DEBUG) {
            Log.d(TAG, "start LoadBlockTask position:" + position + " currentScale:" + scale);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (onLoadStateChangeListener != null) {
            onLoadStateChangeListener.onLoadStart(LOAD_TYPE_BLOCK, position);
        }
    }

    @Override
    protected void doInBackground() {
        if (DEBUG) {
            Log.d(TAG, "doInBackground：" + Thread.currentThread() + " " + Thread.currentThread().getId());
        }
        int imageBlockSize = BASE_BLOCKSIZE * scale;
        int left = imageBlockSize * position.col;
        int right = left + imageBlockSize;
        int top = imageBlockSize * position.row;
        int bottom = top + imageBlockSize;
        if (right > imageWidth) {
            right = imageWidth;
        }
        if (bottom > imageHeight) {
            bottom = imageHeight;
        }
        clipImageRect = new Rect(left, top, right, bottom);
        try {
            BitmapFactory.Options decodingOptions = new BitmapFactory.Options();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                Bitmap bitmap = acquireBitmap();
                decodingOptions.inBitmap = bitmap;
                decodingOptions.inMutable = true;
            }
            decodingOptions.inSampleSize = scale;
            // 加载clipRect的区域的图片块
            bitmap = decoder.decodeRegion(clipImageRect, decodingOptions);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            throwable = e;
        } catch (Exception e) {
            if (DEBUG)
                Log.d(TAG, position.toString() + " " + clipImageRect.toShortString());
            throwable = e;
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (bitmap != null) {
            bitmapPool.release(bitmap);
            bitmap = null;
        }
        decoder = null;
        blockData = null;
        onImageLoadListener = null;
        onLoadStateChangeListener = null;
        position = null;
        if (DEBUG) {
            Log.d(TAG, "onCancelled LoadBlockTask position:" + position + " currentScale:" + scale + " bit:");
        }
    }

    @Override
    protected void onPostExecute() {
        super.onPostExecute();
        if (DEBUG) {
            Log.d(TAG, "finish LoadBlockTask position:" + position + " currentScale:" + scale + " bitmap: " + (bitmap == null ? "" : bitmap.getWidth() + " bitH:" + bitmap.getHeight()));
        }
        blockData.task = null;
        if (bitmap != null) {
            blockData.bitmap = bitmap;
            blockData.realImageRect.set(0, 0, clipImageRect.width() / scale, clipImageRect.height() / scale);
            if (onImageLoadListener != null) {
                onImageLoadListener.onBlockImageLoadFinished();
            }
        }
        if (onLoadStateChangeListener != null) {
            onLoadStateChangeListener.onLoadFinished(LOAD_TYPE_BLOCK, position, throwable == null, throwable);
        }
        decoder = null;
        blockData = null;
        onImageLoadListener = null;
        onLoadStateChangeListener = null;
        position = null;
    }
}
