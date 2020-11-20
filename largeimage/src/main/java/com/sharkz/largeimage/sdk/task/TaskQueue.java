package com.sharkz.largeimage.sdk.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create by LuckyJayce at 2017/3/20
 * <p>
 * 任务队列
 */
public class TaskQueue {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        task.executeOnExecutor(executorService);
    }

    public void cancelTask(Task task) {
        if (task == null) {
            return;
        }
        task.cancel(true);
    }
}
