package com.sharkz.imageloader.sdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  13:28
 * 描    述 MD5工具类
 * 修订历史：
 * ================================================
 */
public class MD5Utils {

    /**
     * 将url通过MD5转化为唯一的字符串，用于标识
     */
    public static String hashKeyFromUrl(String url){
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    /**
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if(hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
