package com.sharkz.imageloader;

import java.util.LinkedHashMap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  14:20
 * 描    述 手写内存缓存框架 LruCache
 * 修订历史：
 * ================================================
 */
public class ZLruCache <K,V> extends LinkedHashMap<K,V> {

    /**
     * 最大缓存容量
     */
    private int maxSize;

    public ZLruCache(int maxSize){
        // 这里开启了访问顺序排序
        super(16,0.75f,true);
        this.maxSize=maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        //return super.removeEldestEntry(eldest);
        // 自己实现 删除策略
        // 当当前容量大于设定的最大缓存容量的时候就开启删除策略
        return size()>this.maxSize;
    }

}
