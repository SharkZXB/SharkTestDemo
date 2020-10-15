package com.sharkz.hashmap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/15  14:53
 * 描    述 定义接口
 * 修订历史：
 * ================================================
 */
public interface Map<K, V> {

    /**
     * 保存数据
     *
     * @param k
     * @param v
     * @return
     */
    V put(K k, V v);

    /**
     * 获取数据
     *
     * @param k
     * @return
     */
    V get(K k);
}
