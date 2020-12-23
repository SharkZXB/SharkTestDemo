package com.example.mmkv;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText mEditText;
    TextView showText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SPUtils.init(getBaseContext());

        // 初始化
        KVUtils.getInstance().init(getApplicationContext());
        // 秘钥
        String cryptKey = "s1d#f%sdfsdfs";
        // 是否开启加密解密
        KVUtils.getInstance().setEncrypt(true, cryptKey);


        mEditText = findViewById(R.id.show1);
        showText = findViewById(R.id.show2);
        findViewById(R.id.test1).setOnClickListener(v -> {
            SPUtils.clear();
            long time = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                SPUtils.putBoolean("test" + i, true);
                SPUtils.putString("test_str_" + i, "测试内容" + i);
                Log.i("1循环", "" + SPUtils.getBoolean("test" + i));
                Log.i("1循环", "" + SPUtils.getString("test_str_" + i));
            }
            showText.setText(String.format(Locale.getDefault(), "SPUtils运行时间：%d毫秒", System.currentTimeMillis() - time));
        });
        findViewById(R.id.test2).setOnClickListener(v -> {
            // 清空数据
            KVUtils.getInstance().clear();

            long time = System.currentTimeMillis();
            for (int i = 0; i < 1000; i++) {
                KVUtils.getInstance().putBoolean("test" + i, true);
                KVUtils.getInstance().putString("test_str_" + i, "测试内容" + i);
                Log.i("2循环", "" + KVUtils.getInstance().getBoolean("test" + i));
                Log.i("2循环", "" + KVUtils.getInstance().getString("test_str_" + i));
            }
            showText.setText(String.format(Locale.getDefault(), "KVUtils运行时间：%d毫秒", System.currentTimeMillis() - time));
        });
        findViewById(R.id.button2).setOnClickListener(v -> {
            String value = KVUtils.getInstance().getString("my_key");
            showText.setText("" + value);
        });
        findViewById(R.id.button3).setOnClickListener(v -> KVUtils.getInstance().clear());

        // 保存数据
        findViewById(R.id.button1).setOnClickListener(v -> {
            String value = mEditText.getText().toString();
            KVUtils.getInstance().putString("my_key", value);
        });
    }
}