package com.sharkz.zroutersimple;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/16  13:43
 * 描    述 一个非常简单的路由框架
 * 修订历史：
 * ================================================
 */
public class ZRouter {

    private static final ZRouter zRouter = new ZRouter();

    private ZRouter() {

    }

    /**
     * 获取单列
     *
     * @return
     */
    public static ZRouter getInstance() {
        return zRouter;
    }

    /**
     * 路由表 将所有的Activity都注册到这里
     */
    private Map<String, Class<? extends Activity>> routers = new HashMap<>();

    /**
     * 将Activity注册到路由表
     *
     * @param key key
     * @param cls class
     */
    public void register(String key, Class<? extends Activity> cls) {
        routers.put(key, cls);
    }

    /**
     * 界面跳转
     *
     * @param activity 发起界面跳转的 activity
     * @param key      通过key获取注册好的目标 activity class
     */
    public void startActivity(Activity activity, String key) {
        Class<? extends Activity> aClass = routers.get(key);
        if (aClass != null) {
            activity.startActivity(new Intent(activity, aClass));
        }
    }

}
