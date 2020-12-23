package com.example.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewDataBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // 创建一个数据对象
        UserBean userBean = new UserBean ("张三", 25);
        // 设置新数据
        viewDataBinding.setVariable(BR.user,userBean);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    try {
                        Thread.sleep(1000);

                        // 修改数据源
                        userBean.setName(userBean.getName()+i);
                        // 更新数据
                        viewDataBinding.setVariable(BR.user,userBean);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}