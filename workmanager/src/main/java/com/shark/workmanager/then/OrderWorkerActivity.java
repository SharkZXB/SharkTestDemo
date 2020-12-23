package com.shark.workmanager.then;

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

/**
 * 顺序执行任务
 */
public class OrderWorkerActivity extends AppCompatActivity {

	public static void startUp(Context context) {
		context.startActivity(new Intent(context, OrderWorkerActivity.class));
	}

	private Button mButtonStart;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_worker);
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
	 * A,B,C三个任务顺序执行
	 */
	private void startWorker() {
		// A
		OneTimeWorkRequest requestA = new OneTimeWorkRequest.Builder(OrderWorkerA.class).build();
		// B
		OneTimeWorkRequest requestB = new OneTimeWorkRequest.Builder(OrderWorkerB.class).build();
		// C
		OneTimeWorkRequest requestC = new OneTimeWorkRequest.Builder(OrderWorkerC.class).build();
		// 任务入队，WorkManager调度执行
		WorkManager.getInstance().beginWith(requestA).then(requestB).then(requestC).enqueue();
	}


}
