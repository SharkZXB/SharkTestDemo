package com.sharkz.hashmap;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/15  15:00
 * 描    述
 * 修订历史：
 * ================================================
 */
public class SharkHashMap {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.put("" + i, i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("\t key:" + i + "\t value:" + map.get("" + i));
        }
    }
}
