package com.sharkz.handler;

import android.util.Log;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  22:10
 * 描    述 自定义消息循环器
 * 修订历史：
 * ================================================
 */
public class ZLooper {

    /**
     * 当前线程保存数据对象
     */
    static final ThreadLocal<ZLooper> sThreadLocal = new ThreadLocal<>();

    /**
     * 消息队列
     */
    public ZMessageQueue myQueue;


    /**
     * 在构造函数里面初始化了 messageQueue
     */
    public ZLooper() {
        myQueue = new ZMessageQueue();
    }

    /**
     * 创建（准备）Looper
     */
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new ZLooper());
    }

    /**
     * 获取 当前 Looper
     */
    public static ZLooper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 启动 looper 注意这个有个死循环
     */
    public static void loop() {
        // 从全局ThreadLocalMap中获取唯一looper对象
        ZLooper myLooper = myLooper();
        ZMessageQueue myQueue = myLooper.myQueue;

        for (; ; ) {
            ZMessage myMessage = myQueue.next();
            Log.i("TAG", "loop: " + myMessage);
            if (myMessage != null) {
                myMessage.target.dispatchMessage(myMessage);
            }
        }
    }

}
