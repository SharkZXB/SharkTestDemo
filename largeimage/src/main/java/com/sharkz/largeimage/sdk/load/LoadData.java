package com.sharkz.largeimage.sdk.load;

import android.graphics.BitmapRegionDecoder;

import com.sharkz.largeimage.sdk.factory.BitmapDecoderFactory;

import java.util.Map;


/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:49
 * 描    述 加载数据
 * 修订历史：
 * ================================================
 */
public class LoadData {

    public int currentScale;

    /**
     * decodingOptions.inSampleSize = currentScale 假设smallDataMap的scale = 2，那么currentScaleDataMap = 1
     * currentScale 为2时图片解码的宽高缩放了2倍  -> （相对模糊）
     * currentScale 为1时图片为原图
     * 还有一个特性是： 图片块（非边缘的图片块），small,current的bitmap的width以及height是一样的
     */
    //手势缩小后需要用到的缓存map，BlockData的bitmap显示占图片比例的区域大，（图片块相对模糊）1个图片块显示4个currentScale图片块的内容
    public Map<Position, BlockData> smallDataMap;

    /**
     * 当前用到的map
     */
    public Map<Position, BlockData> currentScaleDataMap;

    /**
     * 完整图片的缩略图，用于一开始展示，避免一开始没有加载好图片块，导致空白
     */
    public volatile BlockData thumbnailBlockData;

    /**
     * 完整图片的缩略图
     */
    public volatile int thumbnailScale;

    public BitmapDecoderFactory mFactory;
    public BitmapRegionDecoder mDecoder;
    public int imageHeight;
    public int imageWidth;
    public LoadImageInfoTask task;

    public LoadData(BitmapDecoderFactory factory) {
        mFactory = factory;
    }
}
