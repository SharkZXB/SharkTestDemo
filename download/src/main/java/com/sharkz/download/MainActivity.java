package com.sharkz.download;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 断点续传 Demo
 */
public class MainActivity extends AppCompatActivity {


    private Handler handler;
    private File file;
    private RandomAccessFile raf;
    private long downLoadSize;
    private long totalSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler= new Handler();

    }
}