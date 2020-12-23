package com.example.taskaffinity;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author : 三丰
 * date   : 2020/11/28
 * desc   :
 */
public class MyApplication extends Application {

    private static final String TAG_APP = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();


        Log.d(TAG_APP, "MyApplication#onCreate" + Thread.currentThread().getName());


        // Activity 生命周期监听
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            //已过滤无用代码，只在onresume 读取相关数据
            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG_APP, "onActivityResumed+" + activity.getClass().getSimpleName() + "####taskid = " + activity.getTaskId());
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });

    }
}
