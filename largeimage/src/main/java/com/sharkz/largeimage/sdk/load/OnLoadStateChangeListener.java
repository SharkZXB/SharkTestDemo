package com.sharkz.largeimage.sdk.load;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:47
 * 描    述 bitmap 加载状态监听回调
 * 修订历史：
 * ================================================
 */
public interface OnLoadStateChangeListener {

    void onLoadStart(int loadType, Object param);

    void onLoadFinished(int loadType, Object param, boolean success, Throwable throwable);
}
