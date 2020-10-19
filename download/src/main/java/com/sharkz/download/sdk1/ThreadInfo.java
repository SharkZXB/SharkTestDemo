package com.sharkz.download.sdk1;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  18:55
 * 描    述
 * 修订历史：
 * ================================================
 */
public class ThreadInfo {

    private int id;         // ID
    private String url;     // 下载地址
    private long start;     // 开始长度
    private long end;       // 目标文件的总长度
    private long finished;  // 完成的长度

    public ThreadInfo() {

    }

    public ThreadInfo(int id, String url, long start, long end, long finished) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("ThreadInfo{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", start='").append(start).append('\'');
        sb.append(", end='").append(end).append('\'');
        sb.append(", finish=").append(finished);
        sb.append('}');
        return sb.toString();
    }

}
