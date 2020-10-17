package com.sharkz.annotation_compiler;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/17  20:43
 * 描    述
 * 修订历史：
 * ================================================
 */
public class RouterConstants {

    public static final String OPTION_MODULE_NAME = "moduleName";
    public static final String OPTION_OPEN_LOG = "openLog";

    public static final String ACTIVITY_FULL_NAME = "android.app.Activity";
    public static final String FRAGMENT_X_FULL_NAME = "androidx.fragment.app.Fragment";

    public static final String IROUTE = "IRoute";
    public static final String IROUTE_FULL_NAME ="com.example.lib_core." + IROUTE;
    public static final String CLASS_JAVA_DOC = "Generated by Router. Do not edit it!\n";
    public static final String METHOD_PUT_ACTIVITY = "putActivity";

    public static final String BASE_PACKAGE_NAME = "com.example.arouter_demo";
    public static final String APT_PACKAGE_NAME = BASE_PACKAGE_NAME+".apt";
}
