package com.sharkz.largeimage.sdk.factory;

import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.IOException;
import java.io.InputStream;

import static android.R.attr.path;

/**
 * 输入流
 */
public class InputStreamBitmapDecoderFactory implements BitmapDecoderFactory {

    /**
     * 数据流
     */
    private InputStream inputStream;

    /**
     * 解析输入流类型的Bitmap
     *
     * @param inputStream 输入流
     */
    public InputStreamBitmapDecoderFactory(InputStream inputStream) {
        super();
        this.inputStream = inputStream;
    }


    // =============================================================================================


    @Override
    public BitmapRegionDecoder made() throws IOException {
        // TODO 利用系统的方法创建一个解析器
        return BitmapRegionDecoder.newInstance(inputStream, false);
    }

    @Override
    public int[] getImageInfo() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, new Rect(), options);
        return new int[]{options.outWidth, options.outHeight};
    }
}