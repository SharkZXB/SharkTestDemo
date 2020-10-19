package com.sharkz.handler;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  22:09
 * 描    述 自定义消息对象
 * 修订历史：
 * ================================================
 */
public class ZMessage {

    /**
     * 发送消息的那个 Handler
     */
    public ZHandler target;

    /**
     * 传递的数据
     */
    public Object obj;

}
