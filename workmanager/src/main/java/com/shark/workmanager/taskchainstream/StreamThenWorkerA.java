package com.shark.workmanager.taskchainstream;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * A任务输出10
 */
public class StreamThenWorkerA extends Worker {

	public StreamThenWorkerA(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Data data = new Data.Builder().putInt("a_out", 10).build();
		return Result.success(data);
	}
}
