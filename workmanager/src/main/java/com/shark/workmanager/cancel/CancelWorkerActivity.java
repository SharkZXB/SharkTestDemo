package com.shark.workmanager.cancel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.shark.workmanager.R;
import com.shark.workmanager.constraints.ConstraintsWorker;

/**
 * 取消任务
 */
public class CancelWorkerActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, CancelWorkerActivity.class));
	}

	private Button mButtonStart;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_worker);
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
		mButtonStart = findViewById(R.id.button_start);
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

	/**
	 * 启动约束任务
	 */
	private void startWorker() {
		// 给任务设置tag->cancel
		OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ConstraintsWorker.class).addTag("cancel").build();
		// 任务入队，WorkManager调度执行
		WorkManager.getInstance().enqueue(request);
	}

	/**
	 * 通过tag取消任务
	 */
	private void cancelWorker() {
		WorkManager.getInstance().cancelAllWorkByTag("cancel");
	}

}
