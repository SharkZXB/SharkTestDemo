package com.sharkz.zroutersimple;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/16  13:53
 * 描    述
 * 修订历史：
 * ================================================
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化的时候将所有的Activity都注册到路由表中
        ZRouter.getInstance().register("main_2", MainActivity2.class);
        ZRouter.getInstance().register("main_3", MainActivity3.class);
    }
}
