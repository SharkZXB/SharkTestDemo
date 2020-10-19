package com.sharkz.handler;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  22:07
 * 描    述 自定义消息队列 这里使用的阻塞队列
 * 修订历史：
 * ================================================
 */
public class ZMessageQueue {


    // 阻塞队列
    ArrayBlockingQueue<ZMessage> myMessages;

    /**
     * 构造函数
     */
    public ZMessageQueue() {
        myMessages = new ArrayBlockingQueue<>(50);
    }

    /**
     * 将消息添加到队列
     */
    public void enqueueMessage(ZMessage message) {
        try {
            myMessages.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从消息队列中取出
     */
    public ZMessage next() {
        try {
            return myMessages.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
