package com.sharkz.largeimage.sdk.factory;

import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;

import java.io.File;
import java.io.IOException;

/**
 * 从文件中解析Bitmap
 */
public class FileBitmapDecoderFactory implements BitmapDecoderFactory {

    private String path;

    public FileBitmapDecoderFactory(String filePath) {
        super();
        this.path = filePath;
    }

    public FileBitmapDecoderFactory(File file) {
        super();
        this.path = file.getAbsolutePath();
    }

    @Override
    public BitmapRegionDecoder made() throws IOException {
        return BitmapRegionDecoder.newInstance(path, false);
    }

    @Override
    public int[] getImageInfo() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return new int[]{options.outWidth, options.outHeight};
    }
}