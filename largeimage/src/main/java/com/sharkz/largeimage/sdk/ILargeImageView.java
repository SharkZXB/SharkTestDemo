package com.sharkz.largeimage.sdk;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;

import com.sharkz.largeimage.sdk.factory.BitmapDecoderFactory;


/**
 * Created by LuckyJayce on 2016/11/24.
 * <p>
 * 定义加载大图的功能
 */
public interface ILargeImageView {

    /**
     * 获取图片的宽度
     */
    int getImageWidth();

    /**
     * 获取图片的高度
     */
    int getImageHeight();

    /**
     * 是否加载
     */
    boolean hasLoad();

    /**
     * 图片加载监听器
     */
    void setOnImageLoadListener(BlockImageLoader.OnImageLoadListener onImageLoadListener);

    /**
     * 设置加载局部图片的解析器
     *
     * @param factory
     */
    void setImage(BitmapDecoderFactory factory);

    /**
     * @param factory
     * @param defaultDrawable
     */
    void setImage(BitmapDecoderFactory factory, Drawable defaultDrawable);

    /**
     * 直接设置 Bitmap
     *
     * @param bm
     */
    void setImage(Bitmap bm);

    void setImage(Drawable drawable);

    void setImage(@DrawableRes int resId);

    void setImageDrawable(Drawable drawable);

    /**
     * 设置默认的缩放比例
     *
     * @param scale
     */
    void setScale(float scale);

    /**
     * 获取当前的缩放比例
     *
     * @return
     */
    float getScale();

    /**
     * 获取图片加载监听器
     */
    BlockImageLoader.OnImageLoadListener getOnImageLoadListener();
}
