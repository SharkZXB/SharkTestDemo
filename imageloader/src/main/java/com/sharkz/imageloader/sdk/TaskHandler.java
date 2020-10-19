package com.sharkz.imageloader.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.sharkz.imageloader.R;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/3/14  14:04
 * 描    述 消息处理 注意这里使用的是主线程的 Looper
 * 修订历史：
 * ================================================
 */
public class TaskHandler extends Handler {

    private static TaskHandler taskHandler;

    public static TaskHandler getInstance(){
        if (taskHandler==null){
            synchronized (TaskHandler.class){
                if (taskHandler==null){
                    taskHandler = new TaskHandler();
                }
            }
        }
        return taskHandler;
    }

    private TaskHandler() {
        super(Looper.getMainLooper());
    }


    // =============================================================================================


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        //给imageView加载bitmap
        TaskResult result =(TaskResult) msg.obj;
        if (result==null){
            return;
        }

        // 显示图片的控件
        ImageView imageView =result.getImageView();
        if (imageView==null){
            return;
        }

        //判断是否数据错乱
        String url =(String)imageView.getTag();
        if (TextUtils.equals(url,result.getUrl())){
            if(result.getBitmap()!=null){
                imageView.setImageBitmap(result.getBitmap());
            }else {
                imageView.setImageResource(R.drawable.ic_error);
            }
        }else{
            // JUtils.Log("不是最新数据");
        }
    }

}
