package com.shark.workmanager.taskchainstream;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 只是做一个简单的打印
 */
public class StreamThenWorkerC extends Worker{

	public StreamThenWorkerC(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		Data inputData = getInputData();
		int b_out = inputData.getInt("b_out", 0);
		//获取到B任务的输出，我们只是做一个简单的输出。
		Log.d("tuacy", "value = " + b_out);
		return Result.success();
	}
}
