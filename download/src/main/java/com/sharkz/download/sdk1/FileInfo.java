package com.sharkz.download.sdk1;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  18:56
 * 描    述
 * 修订历史：
 * ================================================
 */
public class FileInfo implements Serializable {

    private int id;             // ID
    private String url;         // 下载地址
    private String fileName;    // 文件名
    private long length;        // 文件大小
    private long finish;        // 完成的大小

    public FileInfo() {

    }

    public FileInfo(int id, String url, String fileName, long length, long finish) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.length = length;
        this.finish = finish;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFinish() {
        return finish;
    }

    public void setFinish(long finish) {
        this.finish = finish;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FileInfo{");
        sb.append("id=").append(id);
        sb.append(", url='").append(url).append('\'');
        sb.append(", fileName='").append(fileName).append('\'');
        sb.append(", length=").append(length);
        sb.append(", finish=").append(finish);
        sb.append('}');
        return sb.toString();
    }

}
