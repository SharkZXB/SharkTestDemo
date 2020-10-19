package com.sharkz.imageloader.sdk;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  14:09
 * 描    述 异步任务执行结果封装类
 * 修订历史：
 * ================================================
 */
public class TaskResult {

    private ImageView imageView;
    private String url;
    private Bitmap bitmap;

    public TaskResult(ImageView imageView, String url , Bitmap bitmap){
        this.imageView =imageView;
        this.url = url;
        this.bitmap =bitmap;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public void setUri(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
