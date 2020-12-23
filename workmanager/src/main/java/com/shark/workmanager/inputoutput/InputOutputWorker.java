package com.shark.workmanager.inputoutput;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class InputOutputWorker extends Worker {

	public InputOutputWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
	}

	@NonNull
	@Override
	public Result doWork() {

		try {
			//模拟耗时任务
			Thread.sleep(3000);

			// 获取传递进来的数据
			Data inputData = getInputData();

			// 获取到输入的参数，我们又把输入的参数给outputData
			Data outputData = new Data.Builder().putString("key_name", inputData.getString("key_name")+"   修改之后的数据").build();

			// 处理完成之后 返回
			return Result.success(outputData);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return Result.success();
	}
}
