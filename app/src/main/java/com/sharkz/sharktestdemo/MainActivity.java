package com.sharkz.sharktestdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="SHARK_THREAD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate: 开始执行"+System.currentTimeMillis());
        main();
        Log.i(TAG, "onCreate: 执行完毕"+System.currentTimeMillis());

    }

    private static class TanJiaJunCallable implements Callable<String> {

        @Override
        public String call() {
            try {
                Log.i(TAG, "call: 开始睡眠");
                Thread.sleep(1000);
                Log.i(TAG, "call: 结束睡眠");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.i(TAG, "call: 当前线程的ID ="+Thread.currentThread().getId());
            return "SharkZ";
        }

    }

    public static void main() {

        Log.i(TAG, "main: 主线程的ID ="+Thread.currentThread().getId());

        // 创建FutureTask对象
        FutureTask<String> futureTask = new FutureTask<>(new TanJiaJunCallable());
        // 创建线程
        Thread thread = new Thread(futureTask);
        thread.start();
        try {
            // 等待任务执行完毕，并且得到返回值
            String result = futureTask.get();
            //System.out.println(result);
            Log.i(TAG, "main: 结果输出 result="+result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}