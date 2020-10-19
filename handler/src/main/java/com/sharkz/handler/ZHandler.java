package com.sharkz.handler;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  22:14
 * 描    述
 * 修订历史：
 * ================================================
 */
public class ZHandler {


    private ZLooper myLooper;
    private ZMessageQueue myQueue;

    /**
     * 构造函数
     */
    public ZHandler() {
        myLooper = ZLooper.myLooper();
        if (myLooper == null) {
            throw new RuntimeException(
                    "Can't create handler inside thread " + Thread.currentThread()
                            + " that has not called Looper.prepare()");
        }
        myQueue = myLooper.myQueue;
    }

    /**
     * 消息处理 Handler 实现 重写当前
     */
    public void handleMessage(ZMessage msg) {

    }

    /**
     * 发送消息
     */
    public void sendMessage(ZMessage message) {
        message.target = this;
        // 将消息放入到消息队列中
        myQueue.enqueueMessage(message);
    }

    /**
     * 消息分发 处理message的时候调用当前
     */
    public void dispatchMessage(ZMessage myMessage) {
        handleMessage(myMessage);
    }

}
