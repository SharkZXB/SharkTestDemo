package com.shark.shortcut;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            register();
        }
    }

    /**
     * 动态创建
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void register() {
        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);
        List<ShortcutInfo> infos = new ArrayList<>();

        // 按下返回按钮跳转的activity
//        Intent intent1 = new Intent(this, MainActivity.class);
//        intent1.setAction(Intent.ACTION_VIEW);

        // 目标activity
        Intent intent2 = new Intent(this, PublishPostActivity.class);
        intent2.setAction("com.yuyh.xxx.BACK");

//        Intent[] intents = new Intent[2];
//        intents[0] = intent1;
//        intents[1] = intent2;

        ShortcutInfo info = new ShortcutInfo.Builder(this, "publish-2")
                .setShortLabel("我在哪")
                .setLongLabel("我是谁")
                .setIcon(Icon.createWithResource(this, R.mipmap.acc_circle))
               // .setIntents(intents)
                .setIntent(intent2)
                .build();
        infos.add(info);

        // 3D Touch
        mShortcutManager.setDynamicShortcuts(infos);

        // 快捷键
        PendingIntent successCallback = PendingIntent.getBroadcast(this, 0, mShortcutManager.createShortcutResultIntent(info), 0);
        mShortcutManager.requestPinShortcut(info, successCallback.getIntentSender());
    }


    /**
     * 动态添加三个
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getNewShortcutInfo() {

        // 系统提供的
        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);

        // 按下返回按钮跳转的activity
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setAction(Intent.ACTION_VIEW);
        // 目标activity
        Intent intent2 = new Intent(this, PublishPostActivity.class);
        intent2.setAction("com.shark.xxx.BACK");
        Intent[] intents = new Intent[2];
        intents[0] = intent1;
        intents[1] = intent2;

        // 一个具体的快捷对象
        ShortcutInfo shortcut = new ShortcutInfo.Builder(this, "id1") // id 标识是唯一的
                .setShortLabel("Web site")  // 桌面快捷方式显示的名字
                .setLongLabel("第一个")      // 长按弹窗显示的名字
                .setIcon(Icon.createWithResource(this, R.mipmap.acc_circle))  // 显示的Icon
                .setIntents(intents) // 意图设置
                .build(); // 构建

        ShortcutInfo shortcut2 = new ShortcutInfo.Builder(this, "id2")
                .setShortLabel("Web site")
                .setLongLabel("第二个")
                .setIcon(Icon.createWithResource(this, R.mipmap.acc_circle))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.csdn.com/")))
                .build();

        ShortcutInfo shortcut3 = new ShortcutInfo.Builder(this, "id3")
                .setShortLabel("Web site")
                .setLongLabel("第三个")
                .setIcon(Icon.createWithResource(this, R.mipmap.acc_circle))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/")))
                .build();

        // 创建长按快捷键
        mShortcutManager.setDynamicShortcuts(Arrays.asList(shortcut, shortcut2, shortcut3));

        // 创建桌面快捷键
        PendingIntent successCallback = PendingIntent.getBroadcast(this, 0, mShortcutManager.createShortcutResultIntent(shortcut), 0);
        mShortcutManager.requestPinShortcut(shortcut, successCallback.getIntentSender());
    }


    /**
     * 动态删除
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void delete() {

        ShortcutManager mShortcutManager = getSystemService(ShortcutManager.class);

        /********* 移除弹出列表图标 **********/
        // 所有动态创建图标
        List<ShortcutInfo> infos1 = mShortcutManager.getDynamicShortcuts();

        List<String> ids1 = new ArrayList<>();
        for (ShortcutInfo info : infos1) {
            ids1.add(info.getId());
        }

        // 禁用所有的快捷方式
        mShortcutManager.disableShortcuts(ids1, "已禁用");
        mShortcutManager.removeDynamicShortcuts(ids1);

        /********* 移除拖出来的桌面快捷图标 **********/
        // 放在桌面的图标
        List<ShortcutInfo> infos2 = mShortcutManager.getPinnedShortcuts();

        List<String> ids2 = new ArrayList<>();
        for (ShortcutInfo info : infos2) {
            ids2.add(info.getId());
        }

        mShortcutManager.disableShortcuts(ids2, "已禁用");
        mShortcutManager.removeAllDynamicShortcuts();
    }


}