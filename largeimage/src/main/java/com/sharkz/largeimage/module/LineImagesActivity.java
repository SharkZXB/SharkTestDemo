package com.sharkz.largeimage.module;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.sharkz.largeimage.R;
import com.sharkz.largeimage.sdk.UpdateImageView;
import com.sharkz.largeimage.sdk.factory.InputStreamBitmapDecoderFactory;

import java.util.ArrayList;
import java.util.List;

public class LineImagesActivity extends AppCompatActivity {

    private List<Image> list;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_images);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        list = new ArrayList<>();
        list.add(new Image("111.jpg", 4000 , 6750));
        list.add(new Image("aaa.jpg", 30000 , 926));
        list.add(new Image("ccc.jpg", 600, 30000));

        list.add(new Image("111.jpg", 4000 , 6750));
        list.add(new Image("aaa.jpg", 30000 , 926));
        list.add(new Image("ccc.jpg", 600, 30000));

        try {
            int i = 0;
            for (Image image : list) {
                UpdateImageView imageView = new UpdateImageView(this);
                int w = getResources().getDisplayMetrics().widthPixels;
                int height = (int) (1.0f * image.height * w / image.width);
                linearLayout.addView(imageView, new LinearLayout.LayoutParams(w, height));
                imageView.setImage(new InputStreamBitmapDecoderFactory(getAssets().open(image.url)));
                imageView.setIndex(i++);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}