package com.sharkz.largeimage.sdk.load;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:46
 * 描    述 bitmap 块 加载回调
 * 修订历史：
 * ================================================
 */
public interface OnImageLoadListener {

    void onBlockImageLoadFinished();

    void onLoadImageSize(int imageWidth, int imageHeight);

    void onLoadFail(Exception e);
}
