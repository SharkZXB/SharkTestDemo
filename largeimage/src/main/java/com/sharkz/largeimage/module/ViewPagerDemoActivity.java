package com.sharkz.largeimage.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.sharkz.largeimage.R;
import com.sharkz.largeimage.sdk.largeview.LargeImageView;
import com.sharkz.largeimage.sdk.factory.InputStreamBitmapDecoderFactory;
import com.shizhefei.view.viewpager.RecyclingPagerAdapter;

import java.io.IOException;

public class ViewPagerDemoActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
    }

    private RecyclingPagerAdapter adapter = new RecyclingPagerAdapter() {
        private String[] ss = {"ccc.jpg", "111.jpg", "aaa.jpg"};

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.ttt, container, false);
            }
            LargeImageView largeImageView = (LargeImageView) convertView;
            try {
                largeImageView.setImage(new InputStreamBitmapDecoderFactory(getAssets().open(ss[position])));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return largeImageView;
        }

        @Override
        public int getCount() {
            return ss.length;
        }
    };
}
