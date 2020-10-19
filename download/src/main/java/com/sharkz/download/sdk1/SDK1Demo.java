package com.sharkz.download.sdk1;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/19  19:26
 * 描    述
 * 修订历史：
 * ================================================
 */
class SDK1Demo {

    //  private void downLoadFile() {
    //        try {
    //            if (file == null) {
    //                file = new File(rootFile, name);
    //                raf = new RandomAccessFile(file, "rwd");
    //            } else {
    //                downLoadSize = file.length();
    //                if (raf == null) {
    //                    raf = new RandomAccessFile(file, "rwd");
    //                }
    //                raf.seek(downLoadSize);
    //            }
    //            totalSize = getContentLength(path);
    //            if (downLoadSize == totalSize) {
    //                //已经下载完成
    //                return;
    //            }
    //
    //            OkHttpClient client = new OkHttpClient();
    //            Request request = new Request.Builder().url(path).
    //                    addHeader("Range", "bytes=" + downLoadSize + "-" + totalSize).build();
    //            Response response = client.newCall(request).execute();
    //            InputStream ins = response.body().byteStream();
    //            int len = 0;
    //            byte[] by = new byte[1024];
    //            long endTime = System.currentTimeMillis();
    //            while ((len = ins.read(by)) != -1 && isDown) {
    //                raf.write(by, 0, len);
    //                downLoadSize += len;
    //                if (System.currentTimeMillis() - endTime > 1000) {
    //                    final double dd = downLoadSize / (totalSize * 1.0);
    //                    DecimalFormat format = new DecimalFormat("#0.00");
    //                    String value = format.format((dd * 100)) + "%";
    //                    Log.i("tag", "==================" + value);
    //                    handler.post(new Runnable() {
    //                        @Override
    //                        public void run() {
    //                            progress.onProgress((int) (dd * 100));
    //                        }
    //                    });
    //                }
    //            }
    //            response.close();
    //        } catch (Exception e) {
    //            e.getMessage();
    //        }
    //
    //    }
    //
    //    /**
    //     * 通过OkhttpClient获取文件的大小
    //     *
    //     * @param url
    //     * @return
    //     * @throws IOException
    //     */
    //    public long getContentLength(String url) throws IOException {
    //        OkHttpClient client = new OkHttpClient();
    //        Request request = new Request.Builder().url(url).build();
    //        Response response = client.newCall(request).execute();
    //        long length = response.body().contentLength();
    //        response.close();
    //        return length;
    //    }

}
