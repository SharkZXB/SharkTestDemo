/*
Copyright 2015 shizhefei（LuckyJayce）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.sharkz.largeimage.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;


import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ScrollerCompat;

import com.sharkz.largeimage.sdk.factory.BitmapDecoderFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuckyJayce on 2016/11/24.
 * <p>
 * 加载 大图的自定义View
 */
public class LargeImageView extends View implements BlockImageLoader.OnImageLoadListener, ILargeImageView {

    private static final String TAG="LargeImageView";
    
    private final GestureDetector gestureDetector;  // 手势解析器
    private final ScaleGestureDetector scaleGestureDetector;  // 缩放手势解析器
    private final ScrollerCompat mScroller;  // 滚动
    private final BlockImageLoader imageBlockImageLoader;  // 块图片 加载器
    private BitmapDecoderFactory mFactory;  // 这个是重点 Bitmap 局部加载解析器
    private BlockImageLoader.OnImageLoadListener mOnImageLoadListener;  // 图片加载监听器
    private ScaleHelper scaleHelper;  // 手势缩放助手

    /**
     * 动画差值器
     */
    private AccelerateInterpolator accelerateInterpolator;
    private DecelerateInterpolator decelerateInterpolator;

    /**
     * 获得缩放的最小、大抛射速度
     */
    private final int mMinimumVelocity;
    private final int mMaximumVelocity;

    /**
     * 图片的宽高
     */
    private int mDrawableWidth;
    private int mDrawableHeight;

    /**
     * 自适应、最大、最小缩放比例
     */
    private float fitScale;
    private float maxScale;
    private float minScale;

    private final Paint paint;              // 画笔
    private float mScale = 1;               // 缩放比例
    private Drawable mDrawable;             // 位图 这个位图干嘛的
    private int mLevel;                     // 等级
    private boolean isAttachedWindow;       // 是否负载到屏幕了


    // ========下面是构造函数=========================================================================


    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Log.i(TAG, "LargeImageView: 构造函数开始执行");

        // 创建一个控制滑动的
        mScroller = ScrollerCompat.create(getContext(), null);
        scaleHelper = new ScaleHelper();

        // 设置此视图是否可以接收焦点
        setFocusable(true);
        // 此视图是否单独绘制
        setWillNotDraw(false);

        // 手势 缩放手势
        gestureDetector = new GestureDetector(context, simpleOnGestureListener);
        scaleGestureDetector = new ScaleGestureDetector(context, onScaleGestureListener);

        // 块加载器
        imageBlockImageLoader = new BlockImageLoader(context);
        imageBlockImageLoader.setOnImageLoadListener(this);

        // 获得缩放的最小抛射速度
        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        // 创建画笔
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);

        Log.i(TAG, "LargeImageView: 构造函数 执行完毕");
    }


    // =============================================================================================


    /**
     * View 视图 加载到 Window
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.i(TAG, "onAttachedToWindow: ");
        isAttachedWindow = false;
        if (mDrawable != null) {
            mDrawable.setVisible(getVisibility() == VISIBLE, false);
        }
    }

    /**
     * View 从Window 移除
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "onDetachedFromWindow: ");
        isAttachedWindow = true;
        // 移除的时候停止加载
        imageBlockImageLoader.stopLoad();
        if (mDrawable != null) {
            mDrawable.setVisible(false, false);
        }
    }

    /**
     * 布局大小改变的时候
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: ");
        if (hasLoad()) {
            initFitImageScale(mDrawableWidth, mDrawableHeight);
        }
    }

    // =============================================================================================


    /**
     * 由父级调用，以请求子级在必要时更新其mScrollX *和mScrollY的值。
     * 如果孩子正在使用{@link android.widget.Scroller Scroller} 对象为滚动动画设置动画，通常会这样做。
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        Log.i(TAG, "computeScroll: 由父级调用，以请求子级在必要时更新其mScrollX *和mScrollY的值。 这个干嘛的 ");

        //
        if (scaleHelper.computeScrollOffset()) {
            setScale(scaleHelper.getCurScale(), scaleHelper.getStartX(), scaleHelper.getStartY());
        }

        //
        if (mScroller.computeScrollOffset()) {
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            if (oldX != x || oldY != y) {
                final int rangeY = getScrollRangeY();
                final int rangeX = getScrollRangeX();
                overScrollByCompat(x - oldX, y - oldY, oldX, oldY, rangeX, rangeY,
                        0, 0, false);
            }
            if (!mScroller.isFinished()) {
                notifyInvalidate();
            }
        }
    }

    /**
     * 将 Touch 事件 单独处理
     *
     * @param event event
     * @return true 消费事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: event="+event.toString());
        
        scaleGestureDetector.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        return true;
    }



    /**
     * 检查此视图是否可以沿特定方向水平滚动。
     *
     * @param direction 方向 横向
     * @return
     */
    @Override
    public boolean canScrollHorizontally(int direction) {

        Log.i(TAG, "canScrollHorizontally: direction="+direction);
        
        if (direction > 0) {
            return getScrollX() < getScrollRangeX();
        } else {
            return getScrollX() > 0 && getScrollRangeX() > 0;
        }
    }

    /**
     * 检查此视图是否可以沿特定方向水平滚动。
     *
     * @param direction 方向 垂直
     * @return
     */
    @Override
    public boolean canScrollVertically(int direction) {

        Log.i(TAG, "canScrollVertically: direction="+direction);
        
        if (direction > 0) {
            return getScrollY() < getScrollRangeY();
        } else {
            return getScrollY() > 0 && getScrollRangeY() > 0;
        }
    }


    // =============================================================================================


    @Override
    public void onBlockImageLoadFinished() {

        Log.i(TAG, "onBlockImageLoadFinished: 块加载成功 回调  ");
        
        notifyInvalidate(); 
        if (mOnImageLoadListener != null) {
            mOnImageLoadListener.onBlockImageLoadFinished();
        }
    }

    @Override
    public void onLoadImageSize(final int imageWidth, final int imageHeight) {

        Log.i(TAG, "onLoadImageSize: imageWidth="+imageWidth +"------ imageHeight="+imageHeight);
        
        mDrawableWidth = imageWidth;
        mDrawableHeight = imageHeight;
        final int layoutWidth = getMeasuredWidth();
        final int layoutHeight = getMeasuredHeight();
        if (layoutWidth == 0 || layoutHeight == 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    initFitImageScale(imageWidth, imageHeight);
                }
            });
        } else {
            initFitImageScale(imageWidth, imageHeight);
        }
        notifyInvalidate();
        if (mOnImageLoadListener != null) {
            mOnImageLoadListener.onLoadImageSize(imageWidth, imageHeight);
        }
    }

    @Override
    public void onLoadFail(Exception e) {

        Log.i(TAG, "onLoadFail: e="+e.toString());
        
        if (mOnImageLoadListener != null) {
            mOnImageLoadListener.onLoadFail(e);
        }
    }


    // =============================================================================================

    @Override
    public boolean hasLoad() {

        Log.i(TAG, "hasLoad: ");

        if (mDrawable != null) {
            return true;
        } else if (mFactory != null) {
            // 是否有负载
            return imageBlockImageLoader.hasLoad();
        }
        return false;
    }

    @Override
    public int getImageWidth() {
        if (mDrawable != null) {
            Log.i(TAG, "getImageWidth: ="+mDrawableWidth);
            return mDrawableWidth;
        } else if (mFactory != null) {
            if (hasLoad()) {
                Log.i(TAG, "getImageWidth: ");
                return mDrawableWidth;
            }
        }

        Log.i(TAG, "getImageWidth: 0");
        return 0;
    }

    @Override
    public int getImageHeight() {
        if (mDrawable != null) {
            Log.i(TAG, "getImageHeight: mDrawableHeight="+mDrawableHeight);
            return mDrawableHeight;
        } else if (mFactory != null) {
            if (hasLoad()) {
                Log.i(TAG, "getImageHeight: mDrawableHeight="+mDrawableHeight);
                return mDrawableHeight;
            }
        }
        Log.i(TAG, "getImageHeight: 0");
        return 0;
    }

    /**
     * @param onImageLoadListener
     */
    @Override
    public void setOnImageLoadListener(BlockImageLoader.OnImageLoadListener onImageLoadListener) {
        this.mOnImageLoadListener = onImageLoadListener;
    }


    // =============================================================================================


    /**
     * Sets a Bitmap as the content of this ImageView.  设置一个位图作为此ImageView的内容。
     *
     * @param bm The bitmap to initFitImageScale
     */
    @Override
    public void setImage(Bitmap bm) {
        setImageDrawable(new BitmapDrawable(getResources(), bm));
    }

    @Override
    public void setImage(Drawable drawable) {
        setImageDrawable(drawable);
    }

    @Override
    public void setImage(@DrawableRes int resId) {
        setImageDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        mFactory = null;
        mScale = 1.0f;
        scrollTo(0, 0);
        if (mDrawable != drawable) {
            final int oldWidth = mDrawableWidth;
            final int oldHeight = mDrawableHeight;
            updateDrawable(drawable);
            onLoadImageSize(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            if (oldWidth != mDrawableWidth || oldHeight != mDrawableHeight) {
                requestLayout();
            }
            notifyInvalidate();
        }
    }


    @Override
    public void setImage(BitmapDecoderFactory factory) {
        setImage(factory, null);
    }

    @Override
    public void setImage(BitmapDecoderFactory factory, Drawable defaultDrawable) {
        mScale = 1.0f;
        this.mFactory = factory;
        scrollTo(0, 0); // 屏幕原点开始绘制
        updateDrawable(defaultDrawable); //

        // TODO 加载器 通过 factory 加载数据
        imageBlockImageLoader.setBitmapDecoderFactory(factory);

        // 请求界面重新绘制   刷新界面
        invalidate();
    }


    // =============================================================================================


    /**
     *
     * @param d
     */
    private void updateDrawable(Drawable d) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            unscheduleDrawable(mDrawable);
            if (isAttachedWindow) {
                mDrawable.setVisible(false, false);
            }
        }
        mDrawable = d;

        if (d != null) {
            d.setCallback(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                d.setLayoutDirection(getLayoutDirection());
            }
            if (d.isStateful()) {
                d.setState(getDrawableState());
            }
            if (isAttachedWindow) {
                d.setVisible(getWindowVisibility() == VISIBLE && isShown(), true);
            }
            d.setLevel(mLevel);
            mDrawableWidth = d.getIntrinsicWidth();
            mDrawableHeight = d.getIntrinsicHeight();
//            applyImageTint();
//            applyColorMod();
//
//            configureBounds();
        } else {
            mDrawableWidth = mDrawableHeight = -1;
        }
    }


    public void setOnLoadStateChangeListener(BlockImageLoader.OnLoadStateChangeListener onLoadStateChangeListener) {
        if (imageBlockImageLoader != null) {
            imageBlockImageLoader.setOnLoadStateChangeListener(onLoadStateChangeListener);
        }
    }

    @Override
    public BlockImageLoader.OnImageLoadListener getOnImageLoadListener() {
        return mOnImageLoadListener;
    }


    @Override
    public void setScale(float scale) {
        setScale(scale, getMeasuredWidth() >> 1, getMeasuredHeight() >> 1);
    }

    @Override
    public float getScale() {
        return mScale;
    }

    public void setScale(float scale, int centerX, int centerY) {
        if (!hasLoad()) {
            return;
        }
        float preScale = mScale;
        mScale = scale;
        int sX = getScrollX();
        int sY = getScrollY();
        int dx = (int) ((sX + centerX) * (scale / preScale - 1));
        int dy = (int) ((sY + centerY) * (scale / preScale - 1));
        overScrollByCompat(dx, dy, sX, sY, getScrollRangeX(), getScrollRangeY(), 0, 0, false);
        notifyInvalidate();
    }


    // =============================================================================================

    /**
     * 计算垂直滚动条代表的垂直范围
     *
     * @return 垂直滚动条表示的总垂直范围
     */
    @Override
    public int computeVerticalScrollRange() {

        Log.i(TAG, "computeVerticalScrollRange: ");
        
        final int contentHeight = getHeight() - getPaddingBottom() - getPaddingTop();
        int scrollRange = getContentHeight();
        final int scrollY = getScrollY();
        final int overscrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            scrollRange -= scrollY;
        } else if (scrollY > overscrollBottom) {
            scrollRange += scrollY - overscrollBottom;
        }
        return scrollRange;
    }

    public float getMinScale() {
        return minScale;
    }

    public float getMaxScale() {
        return maxScale;
    }

    public float getFitScale() {
        return fitScale;
    }


    private int getScrollRangeY() {
        final int contentHeight = getHeight() - getPaddingBottom() - getPaddingTop();
        return getContentHeight() - contentHeight;
    }

    /**
     * 计算水平范围内垂直滚动条的拇指的垂直偏移。该值用于计算拇指在滚动条的轨迹内的位置。
     */
    @Override
    public int computeVerticalScrollOffset() {

        Log.i(TAG, "computeVerticalScrollOffset: ");
        
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    /**
     * 在垂直范围内计算垂直滚动条的拇指的垂直范围。此值用于计算滚动条的轨迹内的拇指的长度。
     */
    @Override
    public int computeVerticalScrollExtent() {

        Log.i(TAG, "computeVerticalScrollExtent: ");
        
        return super.computeVerticalScrollExtent();
    }

    /**
     * 计算水平滚动条表示的水平范围。
     */
    @Override
    public int computeHorizontalScrollRange() {


        Log.i(TAG, "computeHorizontalScrollRange: ");
        
        final int contentWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        int scrollRange = getContentWidth();
        final int scrollX = getScrollX();
        final int overscrollRight = Math.max(0, scrollRange - contentWidth);
        if (scrollX < 0) {
            scrollRange -= scrollX;
        } else if (scrollX > overscrollRight) {
            scrollRange += scrollX - overscrollRight;
        }
        return scrollRange;
    }

    private int getScrollRangeX() {
        final int contentWidth = getWidth() - getPaddingRight() - getPaddingLeft();
        return (getContentWidth() - contentWidth);
    }

    private int getContentWidth() {
        if (hasLoad()) {
            return (int) (getMeasuredWidth() * mScale);
        }
        return 0;
    }

    private int getContentHeight() {
        if (hasLoad()) {
            if (getImageWidth() == 0) {
                return 0;
            }
            return (int) (1.0f * getMeasuredWidth() * getImageHeight() / getImageWidth() * mScale);
        }
        return 0;
    }


    /**
     * 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw: =================================");

        int viewWidth = getWidth();
        int viewHeight = getHeight();
        if (viewWidth == 0) {
            return;
        }
        int drawOffsetX = 0;
        int drawOffsetY = 0;
        int contentWidth = getContentWidth();
        int contentHeight = getContentHeight();
        if (viewWidth > contentWidth) {
            drawOffsetX = (viewWidth - contentWidth) / 2;
        }
        if (viewHeight > contentHeight) {
            drawOffsetY = (viewHeight - contentHeight) / 2;
        }
        if (mFactory == null) {
            if (mDrawable != null) {
                mDrawable.setBounds(drawOffsetX, drawOffsetY, drawOffsetX + contentWidth, drawOffsetY + contentHeight);
                mDrawable.draw(canvas);
            }
        } else {
            int mOffsetX = 0;
            int mOffsetY = 0;
            int left = getScrollX();
            int right = left + viewWidth;
            int top = getScrollY();
            int bottom = top + viewHeight;
            float width = mScale * getWidth();
            float imgWidth = imageBlockImageLoader.getWidth();

            float imageScale = imgWidth / width;

            // 需要显示的图片的实际宽度。
            imageRect.left = (int) Math.ceil((left - mOffsetX) * imageScale);
            imageRect.top = (int) Math.ceil((top - mOffsetY) * imageScale);
            imageRect.right = (int) Math.ceil((right - mOffsetX) * imageScale);
            imageRect.bottom = (int) Math.ceil((bottom - mOffsetY) * imageScale);

            int saveCount = canvas.save();

            //如果是大图就需要继续加载图片块，如果不是大图直接用默认的
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (mDrawable == null || !imageBlockImageLoader.hasLoad() || (imageBlockImageLoader.getWidth() * imageBlockImageLoader.getHeight() > (displayMetrics.widthPixels * displayMetrics.heightPixels))) {
                imageBlockImageLoader.loadImageBlocks(drawDatas, imageScale, imageRect, viewWidth, viewHeight);
            }
            if (BlockImageLoader.DEBUG) {
                for (int i = 0; i < drawDatas.size(); i++) {
                    BlockImageLoader.DrawData data = drawDatas.get(i);
                    Rect drawRect = data.imageRect;
                    drawRect.left = (int) (Math.ceil(drawRect.left / imageScale) + mOffsetX) + drawOffsetX;
                    drawRect.top = (int) (Math.ceil(drawRect.top / imageScale) + mOffsetY) + drawOffsetY;
                    drawRect.right = (int) (Math.ceil(drawRect.right / imageScale) + mOffsetX) + drawOffsetX;
                    drawRect.bottom = (int) (Math.ceil(drawRect.bottom / imageScale) + mOffsetY) + drawOffsetY;
                    if (i == 0) {
                        canvas.drawRect(data.imageRect, paint);
                    } else {
                        drawRect.left += 3;
                        drawRect.top += 3;
                        drawRect.bottom -= 3;
                        drawRect.right -= 3;
                        canvas.drawBitmap(data.bitmap, data.srcRect, drawRect, null);
                    }
                }
            } else {
                if (drawDatas.isEmpty()) {
                    if (mDrawable != null) {
                        mDrawable.setBounds(drawOffsetX, drawOffsetY, drawOffsetX + contentWidth, drawOffsetY + contentHeight);
                        mDrawable.draw(canvas);
                    }
                } else {
                    for (BlockImageLoader.DrawData data : drawDatas) {
                        Rect drawRect = data.imageRect;
                        drawRect.left = (int) (Math.ceil(drawRect.left / imageScale) + mOffsetX) + drawOffsetX;
                        drawRect.top = (int) (Math.ceil(drawRect.top / imageScale) + mOffsetY) + drawOffsetY;
                        drawRect.right = (int) (Math.ceil(drawRect.right / imageScale) + mOffsetX) + drawOffsetX;
                        drawRect.bottom = (int) (Math.ceil(drawRect.bottom / imageScale) + mOffsetY) + drawOffsetY;
                        canvas.drawBitmap(data.bitmap, data.srcRect, drawRect, null);
                    }
                }
            }
            canvas.restoreToCount(saveCount);
        }
    }

    private List<BlockImageLoader.DrawData> drawDatas = new ArrayList<>();

    private Rect imageRect = new Rect();


    /**
     * TODO 设置合适的缩放大小
     *
     * @param imageWidth
     * @param imageHeight
     */
    private void initFitImageScale(int imageWidth, int imageHeight) {
        final int layoutWidth = getMeasuredWidth();
        final int layoutHeight = getMeasuredHeight();
        if (imageWidth > imageHeight) {
            fitScale = (1.0f * imageWidth / layoutWidth) * layoutHeight / imageHeight;
            maxScale = 1.0f * imageWidth / layoutWidth * 4;
            minScale = 1.0f * imageWidth / layoutWidth / 4;
            if (minScale > 1) {
                minScale = 1;
            }
        } else {
            fitScale = 1.0f;
            minScale = 0.25f;
            maxScale = 1.0f * imageWidth / layoutWidth;
            float a = (1.0f * imageWidth / layoutWidth) * layoutHeight / imageHeight;
            float density = getContext().getResources().getDisplayMetrics().density;
            maxScale = maxScale * density;
            if (maxScale < 4) {
                maxScale = 4;
            }
            if (minScale > a) {
                minScale = a;
            }
        }
        if (criticalScaleValueHook != null) {
            minScale = criticalScaleValueHook.getMinScale(this, imageWidth, imageHeight, minScale);
            maxScale = criticalScaleValueHook.getMaxScale(this, imageWidth, imageHeight, maxScale);
        }
    }

    private void notifyInvalidate() {
        ViewCompat.postInvalidateOnAnimation(LargeImageView.this);
    }




    private boolean overScrollByCompat(int deltaX, int deltaY,
                                       int scrollX, int scrollY,
                                       int scrollRangeX, int scrollRangeY,
                                       int maxOverScrollX, int maxOverScrollY,
                                       boolean isTouchEvent) {
        int oldScrollX = getScrollX();
        int oldScrollY = getScrollY();

        int newScrollX = scrollX;

        newScrollX += deltaX;

        int newScrollY = scrollY;

        newScrollY += deltaY;

        // Clamp values if at the limits and record
        final int left = -maxOverScrollX;
        final int right = maxOverScrollX + scrollRangeX;
        final int top = -maxOverScrollY;
        final int bottom = maxOverScrollY + scrollRangeY;

        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }

        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }

        if (newScrollX < 0) {
            newScrollX = 0;
        }
        if (newScrollY < 0) {
            newScrollY = 0;
        }
        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);
        return getScrollX() - oldScrollX == deltaX || getScrollY() - oldScrollY == deltaY;
    }

    private boolean fling(int velocityX, int velocityY) {
        if (Math.abs(velocityX) < mMinimumVelocity) {
            velocityX = 0;
        }
        if (Math.abs(velocityY) < mMinimumVelocity) {
            velocityY = 0;
        }
        final int scrollY = getScrollY();
        final int scrollX = getScrollX();
        final boolean canFlingX = (scrollX > 0 || velocityX > 0) &&
                (scrollX < getScrollRangeX() || velocityX < 0);
        final boolean canFlingY = (scrollY > 0 || velocityY > 0) &&
                (scrollY < getScrollRangeY() || velocityY < 0);
        boolean canFling = canFlingY || canFlingX;
        if (canFling) {
            velocityX = Math.max(-mMaximumVelocity, Math.min(velocityX, mMaximumVelocity));
            velocityY = Math.max(-mMaximumVelocity, Math.min(velocityY, mMaximumVelocity));
            int height = getHeight() - getPaddingBottom() - getPaddingTop();
            int width = getWidth() - getPaddingRight() - getPaddingLeft();
            int bottom = getContentHeight();
            int right = getContentWidth();
            mScroller.fling(getScrollX(), getScrollY(), velocityX, velocityY, 0, Math.max(0, right - width), 0,
                    Math.max(0, bottom - height), width / 2, height / 2);
            notifyInvalidate();
            return true;
        }
        return false;
    }

    protected void onOverScrolled(int scrollX, int scrollY,
                                  boolean clampedX, boolean clampedY) {
        super.scrollTo(scrollX, scrollY);
    }



    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    /**
     * 点击事件
     *
     * @param l
     */
    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.onClickListener = l;
    }

    /**
     * 长安事件
     *
     * @param l
     */
    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
        this.onLongClickListener = l;
    }

    public void setCriticalScaleValueHook(CriticalScaleValueHook criticalScaleValueHook) {
        this.criticalScaleValueHook = criticalScaleValueHook;
    }

    private CriticalScaleValueHook criticalScaleValueHook;

    /**
     * Hook临界值
     */
    public interface CriticalScaleValueHook {

        /**
         * 返回最小的缩放倍数
         * scale为1的话表示，显示的图片和View一样宽
         *
         * @param largeImageView
         * @param imageWidth
         * @param imageHeight
         * @param suggestMinScale 默认建议的最小的缩放倍数
         * @return
         */
        float getMinScale(LargeImageView largeImageView, int imageWidth, int imageHeight, float suggestMinScale);

        /**
         * 返回最大的缩放倍数
         * scale为1的话表示，显示的图片和View一样宽
         *
         * @param largeImageView
         * @param imageWidth
         * @param imageHeight
         * @param suggestMaxScale 默认建议的最大的缩放倍数
         * @return
         */
        float getMaxScale(LargeImageView largeImageView, int imageWidth, int imageHeight, float suggestMaxScale);

    }

    /**
     * 手势
     */
    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            if (!mScroller.isFinished()) {
                mScroller.abortAnimation();
            }
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (!isEnabled()) {
                return false;
            }
            if (onClickListener != null && isClickable()) {
                onClickListener.onClick(LargeImageView.this);
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!isEnabled()) {
                return false;
            }
            overScrollByCompat((int) distanceX, (int) distanceY, getScrollX(), getScrollY(), getScrollRangeX(), getScrollRangeY(), 0, 0, false);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (!isEnabled()) {
                return;
            }
            if (onLongClickListener != null && isLongClickable()) {
                onLongClickListener.onLongClick(LargeImageView.this);
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (!isEnabled()) {
                return false;
            }
            fling((int) -velocityX, (int) -velocityY);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (!isEnabled()) {
                return false;
            }
            if (onDoubleClickListener != null && onDoubleClickListener.onDoubleClick(LargeImageView.this, e)) {
                return true;
            }
            if (!hasLoad()) {
                return false;
            }
            float doubleScale;
            if (fitScale < 2) {
                doubleScale = 2;
            } else {
                if (fitScale > maxScale) {
                    doubleScale = maxScale;
                } else {
                    doubleScale = fitScale;
                }
            }
            float newScale;
            if (mScale < 1) {
                newScale = 1;
            } else if (mScale < doubleScale) {
                newScale = doubleScale;
            } else {
                newScale = 1;
            }
            smoothScale(newScale, (int) e.getX(), (int) e.getY());
            return true;
        }
    };


    public void smoothScale(float newScale, int centerX, int centerY) {
        if (mScale > newScale) {
            if (accelerateInterpolator == null) {
                accelerateInterpolator = new AccelerateInterpolator();
            }
            scaleHelper.startScale(mScale, newScale, centerX, centerY, accelerateInterpolator);
        } else {
            if (decelerateInterpolator == null) {
                decelerateInterpolator = new DecelerateInterpolator();
            }
            scaleHelper.startScale(mScale, newScale, centerX, centerY, decelerateInterpolator);
        }
        notifyInvalidate();
    }

    /**
     * 手势缩放
     */
    private ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (!isEnabled()) {
                return false;
            }
            if (!hasLoad()) {
                return false;
            }
            float newScale;
            newScale = mScale * detector.getScaleFactor();
            if (newScale > maxScale) {
                newScale = maxScale;
            } else if (newScale < minScale) {
                newScale = minScale;
            }
            setScale(newScale, (int) detector.getFocusX(), (int) detector.getFocusY());
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    };




    public OnDoubleClickListener getOnDoubleClickListener() {
        return onDoubleClickListener;
    }


    // =============================================================================================

    /**
     * 双击事件
     *
     * @param onDoubleClickListener
     */
    public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
        this.onDoubleClickListener = onDoubleClickListener;
    }

    public interface OnDoubleClickListener {
        boolean onDoubleClick(LargeImageView view, MotionEvent event);
    }

    private OnDoubleClickListener onDoubleClickListener;
}
