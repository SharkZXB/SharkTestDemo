package com.sharkz.simplebutterknife;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sharkz.simplebutterknife.lib.BindView;
import com.sharkz.simplebutterknife.lib.ButterKnife;
import com.sharkz.simplebutterknife.lib.ContentView;
import com.sharkz.simplebutterknife.lib.OnClick;
import com.sharkz.simplebutterknife.lib.OnLongClick;

/**
 * 手写ButterKnife
 * 涉及到的知识点 注解 反射
 *
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @OnClick({R.id.button})  //可注入多个view
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                textView.setText("我是手写ButterKnife框架");
                break;
            default:
                Log.e("MainActivity", "view not found");
                break;
        }
    }

    @OnLongClick(R.id.button)
    public void onLongClick(View view) {
        Toast.makeText(this, "长按", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // TODO 注入 当前 Activity
        // 这里是重点 拿到当前 this 之后 利用反射
        ButterKnife.inject(this);
    }
}
