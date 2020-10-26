package com.sharkz.largeimage.module;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.sharkz.largeimage.R;
import com.sharkz.largeimage.glide.OkHttpProgressGlideModule;
import com.sharkz.largeimage.glide.ProgressTarget;
import com.sharkz.largeimage.sdk.LargeImageView;
import com.sharkz.largeimage.sdk.factory.FileBitmapDecoderFactory;

import java.io.File;

public class NetworkDemoActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    // private RingProgressBar ringProgressBar;
    private View clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_demo);
        largeImageView = (LargeImageView) findViewById(R.id.networkDemo_photoView);
       // ringProgressBar = (RingProgressBar) findViewById(R.id.networkDemo_ringProgressBar);
        clearButton = findViewById(R.id.networkDemo_button);

        final Glide glide = Glide.get(this);
        OkHttpProgressGlideModule a = new OkHttpProgressGlideModule();
        a.registerComponents(this, glide);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "开始清除缓存", Toast.LENGTH_SHORT).show();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Glide.get(getApplicationContext()).clearDiskCache();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "清除缓存成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }.start();
            }
        });

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1603363118853&di=4dea4d46b43ee694cf3e0a2a4f9de91d&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170222%2Fe778a7e925dd43ff9a61ab7cd0f31643_th.jpeg";
        Glide.with(this).load(url).downloadOnly(new ProgressTarget<String, File>(url, null) {
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
//                ringProgressBar.setVisibility(View.VISIBLE);
//                ringProgressBar.setProgress(0);
            }

            @Override
            public void onProgress(long bytesRead, long expectedLength) {
                int p = 0;
                if (expectedLength >= 0) {
                    p = (int) (100 * bytesRead / expectedLength);
                }
//                ringProgressBar.setProgress(p);
            }

            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> animation) {
                super.onResourceReady(resource, animation);
//                ringProgressBar.setVisibility(View.GONE);
                largeImageView.setImage(new FileBitmapDecoderFactory(resource));
            }

            @Override
            public void getSize(SizeReadyCallback cb) {
                cb.onSizeReady(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            }
        });
    }
}
