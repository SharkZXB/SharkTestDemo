package com.sharkz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/17  20:38
 * 描    述 定义路由使用到的 注解
 * 修订历史：
 * ================================================
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Path {
    String[] value();
}
