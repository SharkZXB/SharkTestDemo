package com.shark.workmanager.taskchainstream;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.OverwritingInputMerger;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.shark.workmanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务链数据流
 */
public class TaskCharinStreamActivity extends AppCompatActivity {

    public static void startUp(Context context) {
        context.startActivity(new Intent(context, TaskCharinStreamActivity.class));
    }

    private Button mButtonThen;
    private Button mButtonCombine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conbine_worker);
        initView();
        initEvent();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkManager.getInstance().cancelAllWork();
    }

    private void initView() {
        mButtonThen = findViewById(R.id.button_stream_then);
        mButtonCombine = findViewById(R.id.button_stream_combine);
    }

    private void initEvent() {
        mButtonThen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startThenWorker();
            }
        });
        mButtonCombine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCombineWorker();
            }
        });
    }

    private void initData() {

    }

    /**
     * 顺序任务的数据流
     * A,B,C三个任务。A,输出10，B任务得到A任务的值×10，最后给到C任务。
     */
    private void startThenWorker() {
        // 任务
        OneTimeWorkRequest requestA = new OneTimeWorkRequest.Builder(StreamThenWorkerA.class).build();
        OneTimeWorkRequest requestB = new OneTimeWorkRequest.Builder(StreamThenWorkerB.class).build();
        //
        Data inputData = new Data.Builder().putInt("abc", 1).build();
        OneTimeWorkRequest requestC = new OneTimeWorkRequest.Builder(StreamThenWorkerC.class).setInputData(inputData).build();

        //
        WorkManager.getInstance().beginWith(requestA).then(requestB).then(requestC).enqueue();
    }

    /**
     * 在C任务中获取到A,B任务的输出
     */
    private void startCombineWorker() {
        OneTimeWorkRequest requestA = new OneTimeWorkRequest.Builder(StreamCombineWorkerA.class).build();
        OneTimeWorkRequest requestB = new OneTimeWorkRequest.Builder(StreamCombineWorkerB.class).build();

        //A任务链
        WorkContinuation continuationA = WorkManager.getInstance().beginWith(requestA);
        //B任务链
        WorkContinuation continuationB = WorkManager.getInstance().beginWith(requestB);
        // 集合
        List<WorkContinuation> workContinuationList = new ArrayList<>();
        workContinuationList.add(continuationA);
        workContinuationList.add(continuationB);

        // 设置合并规则OverwritingInputMerger  这个合并 相当于将上游的所有任务 输出 都获取到 打包输出给下游的任务
        OneTimeWorkRequest requestC = new OneTimeWorkRequest.Builder(StreamCombineWorkerC.class).build();//.setInputMerger(OverwritingInputMerger.class).build();

        //合并上面两个任务链，在接入requestE任务，入队执行
        WorkContinuation continuation = WorkContinuation.combine(workContinuationList).then(requestC);
        continuation.enqueue();
    }

}
