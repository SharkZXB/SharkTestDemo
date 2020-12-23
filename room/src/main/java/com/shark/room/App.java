package com.shark.room;

import android.app.Application;

import com.shark.room.database.AppDatabase;

/**
 * author : 三丰
 * date   : 2020/12/2
 * desc   : https://github.com/darren109/RoomDemo
 */
public class App extends Application {

    private static App app;
    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appDatabase = AppDatabase.getDatabase(this);
        // 创建数据库表
        appDatabase.getOpenHelper().getWritableDatabase();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appDatabase.close();
    }

    public static App getApp() {
        return app;
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
