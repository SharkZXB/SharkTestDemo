package com.sharkz.download.sdk1;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  18:56
 * 描    述
 * 修订历史：
 * ================================================
 */
public class DownLoadService extends Service {

    public static final String FILE_INFO = "fileinfo";


    /**
     * action
     */
    private static final int MSG_INIT = 0;                          //初始化
    public static final String ACTION_START = "ACTION_START";       //开始下载
    public static final String ACTION_PAUSE = "ACTION_PAUSE";       //暂停下载
    public static final String ACTION_FINISHED = "ACTION_FINISHED"; //结束下载
    public static final String ACTION_UPDATE = "ACTION_UPDATE";     //更新UI

    /**
     * 下载路径 这里保存在SD卡里面
     */
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";

    /**
     * 执行下载的那个任务 里面有线程 网络请求 数据保存
     */
    private DownTask mDownloadTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获得Activity传来的参数
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(FILE_INFO);
            new InitThread(fileInfo).start();
        } else if (ACTION_PAUSE.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra(FILE_INFO);
            if (mDownloadTask != null) {
                mDownloadTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 资源回收关闭
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_INIT) {
                FileInfo fileinfo = (FileInfo) msg.obj;
                // TODO 启动下载任务
                mDownloadTask = new DownTask(DownLoadService.this, fileinfo);
                mDownloadTask.startDownTask();
            }
        }
    };


    /**
     * 初始化子线程 这一步的作用是 获取下载目标的信息
     */
    private class InitThread extends Thread {

        private FileInfo tFileInfo;

        public InitThread(FileInfo tFileInfo) {
            this.tFileInfo = tFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(tFileInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int length = -1;

                //获取目标文件长度
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    length = conn.getContentLength();
                }

                if (length < 0) {
                    return;
                }

                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, tFileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");

                //设置本地文件长度
                raf.setLength(length);
                tFileInfo.setLength(length);

                // 发消息
                mHandler.obtainMessage(MSG_INIT, tFileInfo).sendToTarget();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null && raf != null) {
                        raf.close();
                        conn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
