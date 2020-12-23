package com.shark.workmanager.taskchainstream;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * 在C任务中获取到A,B任务的输出。
 */
public class StreamCombineWorkerC extends Worker {

    public StreamCombineWorkerC(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();

        // 注意;这里我用的是getIntArray
        int[] aKeyValueList = data.getIntArray("a_key");
        int[] bKeyValueList = data.getIntArray("b_key");
        Log.d("tuacy", "a_key = " + aKeyValueList[0]);
        Log.d("tuacy", "b_key = " + bKeyValueList[0]);

        return Result.success();
    }
}
