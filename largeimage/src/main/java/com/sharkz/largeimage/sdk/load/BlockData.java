package com.sharkz.largeimage.sdk.load;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.sharkz.largeimage.sdk.task.Task;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:51
 * 描    述 bitmap 块数据
 * 修订历史：
 * ================================================
 */
public class BlockData {

    public BlockData() {

    }

    public BlockData(Position position) {
        this.position = position;
    }

    public Bitmap bitmap;

    //图片的区域
    public Rect realImageRect = new Rect();

    public Task task;
    public Position position;
}
