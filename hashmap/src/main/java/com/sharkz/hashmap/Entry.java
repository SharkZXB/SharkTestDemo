package com.sharkz.hashmap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/15  14:54
 * 描    述 链表的节点
 * 修订历史：
 * ================================================
 */
public class Entry<K, V> {

    K k;
    V v;
    Entry<K, V> next;

    /**
     * 在next指向下一个节点
     */
    public Entry(K k, V v, Entry<K, V> next) {
        this.k = k;
        this.v = v;
        this.next = next;
    }

    public K getKey() {
        return k;
    }

    public V getValue() {
        return v;
    }


}
