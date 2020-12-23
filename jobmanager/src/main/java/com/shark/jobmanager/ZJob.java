package com.shark.jobmanager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

/**
 * author : 三丰
 * date   : 2020/12/23
 * desc   :
 */
public class ZJob extends Job {

    private static final String TAG = "Shark------->";
    private String text;

    protected ZJob(String text) {
        super(new Params(Integer.parseInt(text)).persist());
        this.text = text;
        Log.i(TAG, text + "  init");
    }

    @Override
    public void onAdded() {
        Log.i(TAG, text + "  Onadded to task list");
    }

    @Override
    public void onRun() throws Throwable {
        Log.i(TAG, text + "  onRun");
        Thread.sleep(1000);
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        Log.i(TAG, text + "  onCancel");
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }
}
