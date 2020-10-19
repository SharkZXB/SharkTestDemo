package com.sharkz.download.sdk1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  18:59
 * 描    述
 * 修订历史：
 * ================================================
 */
public class MyDBHelper extends SQLiteOpenHelper {

    /**
     * 数据库的名字
     */
    private static final String DB_NAME = "download.db";

    /**
     * 创建表
     */
    private static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,start long,end long,finished long)";

    /**
     *
     */
    private static final String SQL_DROP = "drop table if exists thread_info";

    /**
     * 当前数据库的版本
     */
    private static final int VERSION = 1;


    private static MyDBHelper myDBHelper;

    private MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static MyDBHelper getInstance(Context context) {
        if (myDBHelper == null) {
            myDBHelper = new MyDBHelper(context);
        }
        return myDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
