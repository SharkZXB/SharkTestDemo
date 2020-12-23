package com.shark.workmanager.combine;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ConbineWorkerE extends Worker{

	public ConbineWorkerE(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		try {
			//模拟耗时任务
			Thread.sleep(1000);
			Log.d("tuacy", "ConbineWorkerE work");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return Result.success();
	}
}
