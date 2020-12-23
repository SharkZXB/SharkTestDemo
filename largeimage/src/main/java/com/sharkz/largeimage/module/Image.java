package com.sharkz.largeimage.module;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/22  13:49
 * 描    述
 * 修订历史：
 * ================================================
 */
public class Image {
    public int height;
    public int width;
    public String url;

    public Image(String url, int width, int height) {
        this.height = height;
        this.width = width;
        this.url = url;
    }
}
