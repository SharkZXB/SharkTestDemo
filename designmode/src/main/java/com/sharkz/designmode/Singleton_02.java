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
public class Singleton_02 {

    private static Singleton_02 INSTANCE;

    private Singleton_02() {
    }

    public static Singleton_02 getInstance() {
        if (INSTANCE == null) {
            try {
                
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }


}
