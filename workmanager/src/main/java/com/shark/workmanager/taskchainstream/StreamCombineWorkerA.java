package com.shark.workmanager.taskchainstream;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * A任务的输出中只有一个key： a_key -> 100
 */
public class StreamCombineWorkerA extends Worker {

	public StreamCombineWorkerA(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Data data = new Data.Builder().putInt("a_key", 100).build();
		return Result.success(data);
	}
}
