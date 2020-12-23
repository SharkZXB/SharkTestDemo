package com.sharkz.largeimage.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sharkz.largeimage.R;
import com.sharkz.largeimage.glide.LargeImageViewTarget;
import com.sharkz.largeimage.sdk.UpdateImageView;

import java.util.ArrayList;
import java.util.List;

public class ListImageActivity extends AppCompatActivity {

    private List<Image> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_image);
        loadData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    private void loadData() {
        list.add(new Image("http://ww2.sinaimg.cn/bmiddle/af0d43ddgw1fadp68mojcj20j65ognpd.jpg", 440, 4693));
        list.add(new Image("http://ww2.sinaimg.cn/bmiddle/af0d43ddgw1fadp697bfcj20j65bou0x.jpg", 440, 4400));
        list.add(new Image("http://ww2.sinaimg.cn/bmiddle/af0d43ddgw1fadp69qv02j20j65box6p.jpg", 440, 4400));
        list.add(new Image("http://ww2.sinaimg.cn/bmiddle/af0d43ddgw1fadp6au40dj20j60j6zq3.jpg", 440, 440));
        list.add(new Image("http://ww1.sinaimg.cn/bmiddle/af0d43ddgw1fadp6b3vcdj20j24xve81.jpg", 440, 4107));
        list.add(new Image("http://ww1.sinaimg.cn/bmiddle/af0d43ddgw1fadp6bp6bvj20j63js7wh.jpg", 440, 2933));
    }

    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(getLayoutInflater().inflate(R.layout.item_image, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.setData(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class ItemViewHolder extends RecyclerView.ViewHolder {
            private UpdateImageView updateImageView;
            private Image data;

            public ItemViewHolder(View itemView) {
                super(itemView);
                updateImageView = (UpdateImageView) itemView.findViewById(R.id.imageView);
            }

            public void setData(Image data) {
                this.data = data;
                int width = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
                int height = width * data.height / data.width;
                ViewGroup.LayoutParams layoutParams = updateImageView.getLayoutParams();
                layoutParams.height = height;
                updateImageView.setLayoutParams(layoutParams);

                Glide.with(updateImageView.getContext()).load(data.url).downloadOnly(new LargeImageViewTarget(updateImageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        //设置加载中的占位图
//                        updateImageView.setImage(new ColorDrawable(Color.GREEN));
                    }
                });
            }
        }
    }

}
