package com.shark.workmanager.taskchainstream;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * B任务的输出中有两个key：b_key -> 100、a_key -> 200
 * 有个key在A任务中也出现了
 */
public class StreamCombineWorkerB extends Worker {

	public StreamCombineWorkerB(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Data data = new Data.Builder().putInt("b_key", 100).putInt("a_key", 200).build();
		return Result.success(data);
	}
}
