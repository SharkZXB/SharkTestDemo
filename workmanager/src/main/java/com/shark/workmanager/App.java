package com.shark.workmanager;

import android.app.Application;

import com.didichuxing.doraemonkit.DoraemonKit;

/**
 * author : 三丰
 * date   : 2020/12/23
 * desc   :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DoraemonKit.install(this);
    }
}
