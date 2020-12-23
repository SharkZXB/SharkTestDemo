package com.sharkz.largeimage.sdk.largeview;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  16:50
 * 描    述 Hook临界值
 * 修订历史：
 * ================================================
 */
public interface CriticalScaleValueHook {

    /**
     * 返回最小的缩放倍数
     * scale为1的话表示，显示的图片和View一样宽
     *
     * @param largeImageView
     * @param imageWidth
     * @param imageHeight
     * @param suggestMinScale 默认建议的最小的缩放倍数
     * @return
     */
    float getMinScale(LargeImageView largeImageView, int imageWidth, int imageHeight, float suggestMinScale);

    /**
     * 返回最大的缩放倍数
     * scale为1的话表示，显示的图片和View一样宽
     *
     * @param largeImageView
     * @param imageWidth
     * @param imageHeight
     * @param suggestMaxScale 默认建议的最大的缩放倍数
     * @return
     */
    float getMaxScale(LargeImageView largeImageView, int imageWidth, int imageHeight, float suggestMaxScale);


}
