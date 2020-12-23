package com.sharkz.largeimage.module;

import android.app.Application;

import leakcanary.LeakCanary;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/22  13:52
 * 描    述
 * 修订历史：
 * ================================================
 */
public class AppContext extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 内存泄漏初始化
    }
}
