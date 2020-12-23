package com.sharkz.largeimage.sdk.load;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  15:13
 * 描    述
 * 修订历史：
 * ================================================
 */
public class DrawData {

    //绘制到View上的区域
    public Rect srcRect = new Rect();
    //图片的区域
    public Rect imageRect = new Rect();
    public Bitmap bitmap;
}
