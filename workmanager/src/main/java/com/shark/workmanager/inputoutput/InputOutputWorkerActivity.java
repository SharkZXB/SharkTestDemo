package com.shark.workmanager.inputoutput;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.shark.workmanager.R;

/**
 * 启动一个带参数的任务，并且获取任务返回值
 */
public class InputOutputWorkerActivity extends AppCompatActivity {

    public static void startUp(Context context) {
        context.startActivity(new Intent(context, InputOutputWorkerActivity.class));
    }

    private Button mButtonStart;
    private TextView mTextOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_worker);
        initView();
        initEvent();
        initData();
    }

    private void initView() {
        mButtonStart = findViewById(R.id.button_input);
        mTextOut = findViewById(R.id.text_out);
    }

    private void initEvent() {
        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorker();
            }
        });
    }

    private void initData() {

    }

    private void startWorker() {
        // 定义一个OneTimeWorkRequest，并且关联InputOutputWorker。设置输入参数
        Data inputData = new Data.Builder().putString("key_name", "江西高安").build();

        // 封装一下
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(InputOutputWorker.class).setInputData(inputData).build();

        // 任务入队，WorkManager调度执行
        WorkManager.getInstance().enqueue(request);

        // 获取到LiveData然后监听数据变化
        WorkManager.getInstance().getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(@Nullable WorkInfo workStatus) {
                if (workStatus == null) {
                    return;
                }
                if (workStatus.getState() == WorkInfo.State.ENQUEUED) {
                    mTextOut.setText("任务入队");
                }
                if (workStatus.getState() == WorkInfo.State.RUNNING) {
                    mTextOut.setText("任务正在执行");
                }
                if (workStatus.getState().isFinished()) {
                    Data data = workStatus.getOutputData();
                    mTextOut.setText("任务完成" + "-结果：" + data.getString("key_name"));
                }
            }
        });
    }
}
