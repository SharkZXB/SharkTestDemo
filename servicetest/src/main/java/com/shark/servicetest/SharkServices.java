package com.shark.servicetest;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;

/**
 * author : 三丰
 * date   : 2020/12/8
 * desc   :
 */
public class SharkServices extends Service {

    private static final String TAG = "SharkServices";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        // 在API16之后，可以使用build()来进行Notification的构建 Notification

        createErrorNotification();
//        createNotificationChannel();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // showDialog();
            }
        }, 5000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }


    private void createErrorNotification() {
        Notification notification = new Notification.Builder(this).build();
        startForeground(0, notification);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name = "getString(R.string.channel_name)";
//         用户可以看到的通知渠道的描述
        String description = "getString(R.string.channel_description)";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
        mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//         最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel);

        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("New Message").setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(CHANNEL_ID)
                .build();
        startForeground(1, notification);
    }


    AlertDialog mDialog;

    // 创建dialog 的地方
    private void createDialog() {
        View view = View.inflate(this, R.layout.dialog_layout, null);
        // 初始Dialog 里面的内容
//        dialogBuOK = (Button) view.findViewById(R.id.dialog_button_ok);
//        dialogBuOK.setOnClickListener(this);
//        dialogBuCancel = (Button) view.findViewById(R.id.dialog_button_cancel);
//        dialogBuCancel.setOnClickListener(this);
//        dialogContent = (TextView)view.findViewById(R.id.dialog_content);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        mDialog = builder.create();
        mDialog.getWindow().setType(
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

    }

    // 更新dialog 里面的内容
    private String updateDialogContent() {
        String content = null;
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String date = sdf.format(new java.util.Date());
        content = "123" + date;
        return content;
    }

    // 弹出dialog 的地方
    public void showDialog() {
        if (mDialog == null) {
            createDialog();
        }
        //dialogContent.setText(updateDialogContent());
        mDialog.dismiss();
        mDialog.show();
    }

}
