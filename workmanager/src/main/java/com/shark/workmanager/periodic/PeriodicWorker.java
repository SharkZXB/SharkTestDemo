package com.shark.workmanager.periodic;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.tencent.mmkv.MMKV;

public class PeriodicWorker extends Worker {

	public PeriodicWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {
		try {

			MMKV mmkv = MMKV.defaultMMKV();
			mmkv.encode("PeriodicWorker",(mmkv.decodeInt("PeriodicWorker",0)+1));

			//模拟耗时任务
			Thread.sleep(1000);
			Log.d("tuacy", "periodic work" +mmkv.decodeInt("PeriodicWorker",0));

		} catch (InterruptedException e) {
			e.printStackTrace();
			return Result.failure();
		}
		return Result.success();
	}
}
