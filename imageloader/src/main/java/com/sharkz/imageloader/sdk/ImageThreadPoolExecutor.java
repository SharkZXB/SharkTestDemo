package com.sharkz.imageloader.sdk;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  13:56
 * 描    述 自定义的线程池，用于执行Runnable任务
 * 修订历史：
 * ================================================
 */
public class ImageThreadPoolExecutor extends ThreadPoolExecutor {

    private static ImageThreadPoolExecutor imageThreadPoolExecutor;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();    // cpu 核心数
    private static final int CORE_POOL_SIZE = CPU_COUNT*4 +1;                           // 核心线程数量
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT*5 +1;                        // 最大线程数量
    private static final long KEEP_ALIVE =10L;                                          // 空闲存活时间


    // =============================================================================================


    /**
     * 获取线程池单列
     */
    public static synchronized ImageThreadPoolExecutor getInstance(){
        if(imageThreadPoolExecutor==null){
            imageThreadPoolExecutor = new ImageThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),sThreadFactory);
        }
        return imageThreadPoolExecutor;
    }


    // =============================================================================================


    /**
     * 用于给线程池创建线程的线程工厂类
     */
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r,"EasyImageLoader#" + mCount.getAndIncrement());
        }
    };

    /**
     *
     * @param corePoolSize
     * @param maximumPoolSize
     * @param keepAliveTime
     * @param unit
     * @param workQueue
     * @param threadFactory
     */
    private  ImageThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                     TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }



}
