package com.shark.workmanager.constraints;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.shark.workmanager.R;

/**
 * 约束任务
 */
public class ConstraintsWorkerActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, ConstraintsWorkerActivity.class));
	}

	private Button mButtonStart;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_constraints_worker);
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
		// 设置只有在wifi状态下才能执行
		Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.UNMETERED).build();

		// 设置约束条件
		OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(ConstraintsWorker.class).setConstraints(constraints).build();

		// 任务入队，WorkManager调度执行
		WorkManager.getInstance().enqueue(request);
	}

}
