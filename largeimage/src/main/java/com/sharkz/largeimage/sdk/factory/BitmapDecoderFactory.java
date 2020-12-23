package com.sharkz.largeimage.sdk.factory;

import android.graphics.BitmapRegionDecoder;

import java.io.IOException;

/**
 * Bitmap 解码器工厂
 */
public interface BitmapDecoderFactory {

    /**
     * 创建一个 BitmapRegionDecoder 这个东西可以局部解析Bitmap
     *
     * @return
     * @throws IOException
     */
    BitmapRegionDecoder made() throws IOException;

    /**
     * 获取图片的详情 数组里面存贮的是 图片的宽高
     *
     * @return
     */
    int[] getImageInfo();
}