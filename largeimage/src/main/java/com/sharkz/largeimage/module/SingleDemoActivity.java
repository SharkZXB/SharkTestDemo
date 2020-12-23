package com.sharkz.largeimage.module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sharkz.largeimage.R;
import com.sharkz.largeimage.sdk.largeview.CriticalScaleValueHook;
import com.sharkz.largeimage.sdk.largeview.LargeImageView;
import com.sharkz.largeimage.sdk.factory.InputStreamBitmapDecoderFactory;
import com.sharkz.largeimage.sdk.largeview.OnDoubleClickListener;

import java.io.IOException;
import java.io.InputStream;

public class SingleDemoActivity extends AppCompatActivity {

    private LargeImageView largeImageView;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_demo);
        largeImageView = (LargeImageView) findViewById(R.id.imageView);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(onCheckedChangeListener);
        largeImageView.setOnClickListener(onClickListener);
        largeImageView.setOnLongClickListener(onLongClickListener);
        largeImageView.setOnDoubleClickListener(onDoubleClickListener);

        // TODO 下面的步骤是 传入显示的数据
        try {

            String fileName = getIntent().getStringExtra("file_name");
            //InputStream inputStream = getAssets().open("drawable/mvc.png");
            InputStream inputStream = getAssets().open(fileName);

            // 设置输入流
            largeImageView.setImage(new InputStreamBitmapDecoderFactory(inputStream), getResources().getDrawable(R.drawable.mvc));

            // 这一步的操作和 post 差不多 都是等布局加载出来了在操作View的
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //largeImageView.setScale(0.5f);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == largeImageView) {
                Toast.makeText(getApplicationContext(), "点击事件", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 长按事件
     */
    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            if (v == largeImageView) {
                Toast.makeText(getApplicationContext(), "长按事件", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    /**
     * 选中事件
     */
    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            largeImageView.setEnabled(!isChecked);
        }
    };

    private CriticalScaleValueHook criticalScaleValueHook = new CriticalScaleValueHook() {

        @Override
        public float getMinScale(LargeImageView largeImageView, int imageWidth, int imageHeight, float suggestMinScale) {
            return 1;
        }

        @Override
        public float getMaxScale(LargeImageView largeImageView, int imageWidth, int imageHeight, float suggestMaxScale) {
            return 4;
        }
    };

    /**
     * 双击事件
     */
    private OnDoubleClickListener onDoubleClickListener = new OnDoubleClickListener() {
        @Override
        public boolean onDoubleClick(LargeImageView view, MotionEvent event) {
            float fitScale = view.getFitScale();
            float maxScale = view.getMaxScale();
            float minScale = view.getMinScale();
            String message = "双击事件 minScale:" + minScale + " maxScale:" + maxScale + " fitScale:" + fitScale;
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            //返回true 拦截双击缩放的事件
            return false;
        }
    };
}
