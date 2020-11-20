package com.sharkz.largeimage.sdk.task;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:44
 * 描    述 任务封装
 * 修订历史：
 * ================================================
 */
public abstract class Task extends AsyncTask<Void, Void, Void> {

    protected void onCancelled() {
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected final void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected final Void doInBackground(Void... params) {
        doInBackground();
        return null;
    }

    protected abstract void doInBackground();

    @Override
    protected final void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        onPostExecute();
    }

    protected void onPostExecute() {
    }
}
