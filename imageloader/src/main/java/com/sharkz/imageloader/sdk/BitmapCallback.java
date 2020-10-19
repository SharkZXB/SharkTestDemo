package com.sharkz.imageloader.sdk;

import android.graphics.Bitmap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  14:26
 * 描    述 Bitmap 处理完成之后的回调
 * 修订历史：
 * ================================================
 */
public interface BitmapCallback {
    void onResponse(Bitmap bitmap);
}
