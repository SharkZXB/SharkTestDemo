package com.sharkz.largeimage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sharkz.largeimage.module.LineImagesActivity;
import com.sharkz.largeimage.module.ListImageActivity;
import com.sharkz.largeimage.module.NetworkDemoActivity;
import com.sharkz.largeimage.module.SingleDemoActivity;
import com.sharkz.largeimage.module.ViewPagerDemoActivity;

/**
 * 加载大图
 */
public class MainActivity extends AppCompatActivity {

    private View networkDemoButton;
    private View viewPagerDemoButton;
    private View listButton;
    private View singleDemoVButton;
    private View singleDemoHButton;
    private View singleDemoNButton;
    private View clearCacheButton;
    private View linearLayoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singleDemoVButton = findViewById(R.id.main_singleDemoV_button);
        singleDemoHButton = findViewById(R.id.main_singleDemoH_button);
        singleDemoNButton = findViewById(R.id.main_singleDemoN_button);
        viewPagerDemoButton = findViewById(R.id.main_viewPagerDemo_button);
        networkDemoButton = findViewById(R.id.main_networkDemo_button);
        linearLayoutButton = findViewById(R.id.main_linearLayout_button);
        listButton = findViewById(R.id.main_list_button);
        clearCacheButton = findViewById(R.id.main_clear_cache_button);

        singleDemoVButton.setOnClickListener(onClickListener);
        singleDemoHButton.setOnClickListener(onClickListener);
        singleDemoNButton.setOnClickListener(onClickListener);
        viewPagerDemoButton.setOnClickListener(onClickListener);
        networkDemoButton.setOnClickListener(onClickListener);
        linearLayoutButton.setOnClickListener(onClickListener);
        listButton.setOnClickListener(onClickListener);
        clearCacheButton.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // 普通图片
            if (v == singleDemoNButton) {
                Intent intent = new Intent(getApplicationContext(), SingleDemoActivity.class);
                intent.putExtra("file_name", "111.jpg");
                startActivity(intent);

                // 纵向长图
            } else if (v == singleDemoVButton) {
                Intent intent = new Intent(getApplicationContext(), SingleDemoActivity.class);
                intent.putExtra("file_name", "ccc.jpg");
                startActivity(intent);

                // 横向长图
            } else if (v == singleDemoHButton) {
                Intent intent = new Intent(getApplicationContext(),SingleDemoActivity.class);
                intent.putExtra("file_name","aaa.jpg");
                startActivity(intent);

                // ViewPager里的大图片
            } else if (v == viewPagerDemoButton) {
                 startActivity(new Intent(getApplicationContext(), ViewPagerDemoActivity.class));

                 // 加载网络的大图片
            } else if (v == networkDemoButton) {
                 startActivity(new Intent(getApplicationContext(), NetworkDemoActivity.class));

                // 网络长图列表
            } else if (v == listButton) {
                 startActivity(new Intent(getApplicationContext(), ListImageActivity.class));

                // 本地长图线性布局
            } else if (v == linearLayoutButton) {
                startActivity(new Intent(getApplicationContext(), LineImagesActivity.class));
            } else if (v == clearCacheButton) {
                Toast.makeText(getApplicationContext(), "开始清除缓存", Toast.LENGTH_SHORT).show();
//                Glide.get(getApplicationContext()).clearMemory();
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        Glide.get(getApplicationContext()).clearDiskCache();
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(), "清除缓存成功", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }.start();
            }
        }
    };
}