package com.sharkz.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/**
 * 手写 Handler
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "SHARK_HANDLER";
    private int count;
    private ZHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handlerMain();
    }

    public void handlerMain() {
        // 创建looper
        ZLooper.prepare();

        // 创建自己的Handler
        handler = new ZHandler() {
            @Override
            public void handleMessage(ZMessage msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: 处理消息" + msg.obj);
            }
        };

        // 子线程发送消息
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    ZMessage message = new ZMessage();
                    message.obj = "消息" + count;
                    count++;
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 开启looper注意这里开启了死循环（模拟ActivityThread main函数里的）
        ZLooper.loop();
    }

}