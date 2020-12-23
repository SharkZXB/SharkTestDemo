package com.shark.workmanager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.shark.workmanager.cancel.CancelWorkerActivity;
import com.shark.workmanager.combine.CombineWorkerActivity;
import com.shark.workmanager.constraints.ConstraintsWorkerActivity;
import com.shark.workmanager.inputoutput.InputOutputWorkerActivity;
import com.shark.workmanager.periodic.PeriodicWorkerActivity;
import com.shark.workmanager.taskchainstream.TaskCharinStreamActivity;
import com.shark.workmanager.then.OrderWorkerActivity;
import com.shark.workmanager.unique.UniqueWorkerActivity;

/**
 * Demo  首页
 */
public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        initView();

    }

    private void initView() {

        // 启动一个带参数的任务，并且获取任务返回值
        findViewById(R.id.button_output).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputOutputWorkerActivity.startUp(mContext);
            }
        });

        // 执行定期任务
        findViewById(R.id.button_intervals).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeriodicWorkerActivity.startUp(mContext);
            }
        });

        // 约束任务
        findViewById(R.id.button_constraints).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintsWorkerActivity.startUp(mContext);
            }
        });

        // 取消任务
        findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelWorkerActivity.startUp(mContext);
            }
        });

        // 顺序执行任务
        findViewById(R.id.button_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderWorkerActivity.startUp(mContext);
            }
        });

        // 组合任务
        findViewById(R.id.button_combine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CombineWorkerActivity.startUp(mContext);
            }
        });

        // 任务链数据流
        findViewById(R.id.button_task_charin_stream).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskCharinStreamActivity.startUp(mContext);
            }
        });

        // 唯一工作队列
        findViewById(R.id.button_task_unique).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UniqueWorkerActivity.startUp(mContext);
            }
        });
    }

}
