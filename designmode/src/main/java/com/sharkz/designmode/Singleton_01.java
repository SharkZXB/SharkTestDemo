package com.sharkz.designmode;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/11/22  15:22
 * 描    述
 * 修订历史：
 * ================================================
 */

/**
 * 懒汉式
 * 类加载到内存后，就是实例化一个单列，JVM保证线程安全
 * JVM 保证一个类只 加载一次到内存
 * 简单实用，推荐使用
 * 唯一缺点，不管用到与否，类加载的时候就实例化
 */
public class Singleton_01 {

    private static final Singleton_01 SINGLETON_01 = new Singleton_01();

    private Singleton_01() {

    }

    public static Singleton_01 getInstance() {
        return SINGLETON_01;
    }

    public void m() {
        System.out.println("m");
    }

    public static void main(String[] args) {
        System.out.println("123");
    }

}
