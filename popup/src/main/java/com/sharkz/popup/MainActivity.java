package com.sharkz.popup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.sharkz.popup.arrowpopup.SimpleTextTip;
import com.sharkz.popup.arrowpopup.TransformersTip;
import com.sharkz.popup.arrowpopup.ArrowGravity;
import com.sharkz.popup.arrowpopup.TipGravity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mb_anchor_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo1();
            }
        });
        findViewById(R.id.mb_anchor_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo2();
            }
        });
        findViewById(R.id.mb_anchor_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo3();
            }
        });

        test(R.id.mb_test, TestActivity.class);
        test(R.id.mb_recyclerview_test, RecyclerViewTestActivity.class);
    }

    private void test(int id, final Class<? extends Activity> clazz) {
        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, clazz));
            }
        });
    }

    // 方式一：在 Java 代码中设置浮窗位置，在布局文件中设置浮窗背景、箭头位置
    private void demo1() {
        View anchorView = findViewById(R.id.mb_anchor_one);
        new TransformersTip(anchorView, R.layout.layout_demo1_tip) {
            @Override
            protected void initView(View contentView) {
                // 点击浮窗中自定按钮关闭浮窗
                contentView.findViewById(R.id.tv_tip_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissTip();
                    }
                });
            }
        }
                .setTipGravity(TipGravity.TO_BOTTOM_CENTER) // 设置浮窗相对于锚点控件展示的位置
                .setArrowGravity(ArrowGravity.CENTER_TO_START) // 设置箭头相对于浮窗的位置
                .setTipOffsetXDp(0) // 设置浮窗在 x 轴的偏移量
                .setTipOffsetYDp(-6) // 设置浮窗在 y 轴的偏移量

                .setBackgroundDimEnabled(true) // 设置是否允许浮窗的背景变暗
                .setDismissOnTouchOutside(false) // 设置点击浮窗外部时是否自动关闭浮窗

                .show(); // 显示浮窗
    }

    // 方式二：在 Java 代码中设置浮窗位置、浮窗背景、箭头位置
    private void demo2() {
        View anchorView = findViewById(R.id.mb_anchor_two);
        new TransformersTip(anchorView, R.layout.layout_demo2_tip) {
            @Override
            protected void initView(View contentView) {
                // 点击浮窗中自定按钮关闭浮窗
                contentView.findViewById(R.id.tv_tip_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissTip();
                    }
                });
            }
        }
                .setArrowGravity(ArrowGravity.TO_BOTTOM_CENTER) // 设置箭头相对于浮窗的位置
                .setBgColor(Color.WHITE) // 设置背景色
                .setShadowColor(Color.parseColor("#33000000")) // 设置阴影色
                .setArrowHeightDp(6) // 设置箭头高度
                .setRadiusDp(4) // 设置浮窗圆角半径
                .setArrowOffsetXDp(0) // 设置箭头在 x 轴的偏移量
                .setArrowOffsetYDp(0) // 设置箭头在 y 轴的偏移量
                .setShadowSizeDp(6) // 设置阴影宽度

                .setTipGravity(TipGravity.TO_TOP_CENTER) // 设置浮窗相对于锚点控件展示的位置
                .setTipOffsetXDp(0) // 设置浮窗在 x 轴的偏移量
                .setTipOffsetYDp(6) // 设置浮窗在 y 轴的偏移量

                .setBackgroundDimEnabled(false) // 设置是否允许浮窗的背景变暗
                .setDismissOnTouchOutside(false) // 设置点击浮窗外部时是否自动关闭浮窗

                .show(); // 显示浮窗
    }

    /**
     * 方式三：对于仅有文字的浮窗，可以直接使用 SimpleTextTip
     */
    private void demo3() {
        View anchorView = findViewById(R.id.mb_anchor_three);
        new SimpleTextTip(anchorView)
                .setTextContent("适用于只有文字的浮窗\n不写布局文件\n在 Java 代码中设置文本内容属性") // 设置浮窗文本内容
                .setTextPaddingDp(12) // 设置文字与浮窗边框的间距
                .setTextColor(Color.BLACK) // 设置文字颜色
                .setTextSizeSp(14) // 设置文字大小
                .setTextGravity(Gravity.CENTER) // 设置文字对其方式
                .setLineSpacingExtraDp(4) // 设置文字行间距

                .setArrowGravity(ArrowGravity.TO_BOTTOM_CENTER) // 设置箭头相对于浮窗的位置
                .setBgColor(Color.WHITE) // 设置背景色
                .setShadowColor(Color.parseColor("#33000000")) // 设置阴影色
                .setArrowHeightDp(6) // 设置箭头高度
                .setRadiusDp(4) // 设置浮窗圆角半径
                .setArrowOffsetXDp(0) // 设置箭头在 x 轴的偏移量
                .setArrowOffsetYDp(0) // 设置箭头在 y 轴的偏移量
                .setShadowSizeDp(6) // 设置阴影宽度

                .setTipGravity(TipGravity.TO_TOP_ALIGN_START) // 设置浮窗相对于锚点控件展示的位置
                .setTipOffsetXDp(0) // 设置浮窗在 x 轴的偏移量
                .setTipOffsetYDp(6) // 设置浮窗在 y 轴的偏移量

                .setBackgroundDimEnabled(false) // 设置是否允许浮窗的背景变暗
                .setDismissOnTouchOutside(true) // 设置点击浮窗外部时是否自动关闭浮窗

                .show(); // 显示浮窗
    }
}
