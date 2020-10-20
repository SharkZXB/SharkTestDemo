package com.sharkz.download;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sharkz.download.sdk2.adapter.MyAdapter;
import com.sharkz.download.sdk2.bean.FileBean;
import com.sharkz.download.sdk2.service.DownloadService;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应 SDK2 的 Demo
 */
public class MainActivity2 extends AppCompatActivity {

    public static final String FILE_URL = "http://down.360safe.com/yunpan/360wangpan_setup_6.5.6.1288.exe";
    private BroadcastReceiver receiver;
    private static final String TAG = "MainActivity";

    private ListView listView;
    private List<FileBean> mData;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        requestPermission();
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        mData = new ArrayList<>();
        FileBean bean = new FileBean(0,FILE_URL,"imooc.apk",100,0);
        FileBean bean1 = new FileBean(1,FILE_URL,"activator.exe",100,0);
        FileBean bean2 = new FileBean(2,FILE_URL,"iTunes64Setup",100,0);
        FileBean bean3 = new FileBean(3,FILE_URL,"BaiduPlayerNetSetup_100.exe",100,0);
        mData.add(bean);
        mData.add(bean1);
        mData.add(bean2);
        mData.add(bean3);
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0x111);
        }
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        mAdapter = new MyAdapter(MainActivity2.this,mData);
        listView.setAdapter(mAdapter);
    }
    private void initEvent() {


        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.DOWNLOAD_UPDATE);
        filter.addAction(DownloadService.DOWNLOAD_FINISHED);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(DownloadService.DOWNLOAD_UPDATE.equals(intent.getAction())) {
                    int finish = intent.getIntExtra("loaded",0);
                    int id = intent.getIntExtra("id",0);
                    int length = intent.getIntExtra("length",0);
                    mData.get(id).setLength(length);
                    mAdapter.updateProgress(id,finish);
                } else if(DownloadService.DOWNLOAD_FINISHED.equals(intent.getAction())) {
                    FileBean fileBean = (FileBean) intent.getSerializableExtra("fileInfo");
                    mAdapter.updateProgress(fileBean.getId(),0);
                    Toast.makeText(MainActivity2.this,fileBean.getFileName() + "下载完成",Toast.LENGTH_SHORT).show();
                }
            }
        };

        registerReceiver(receiver,filter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0x111) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(MainActivity2.this,"You denied the permission",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        Intent intent = new Intent(MainActivity2.this,DownloadService.class);
        stopService(intent);
    }
}