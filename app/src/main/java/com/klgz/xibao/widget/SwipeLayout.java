package com.klgz.xibao.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by lwh on 2017/12/19.
 */

public class SwipeLayout extends FrameLayout {
    private static final String TAG=SwipeLayout.class.getSimpleName()+":";
    private ViewDragHelper.Callback mCallback;
    private ViewDragHelper mDragHelper;
    private View mBackView; //item的侧边布局
    private View mFrontView;//当前显示的item布局
    private int mWidth; //屏幕的宽度,mFrontView的宽度
    private int mHeight; //mFrontView的高度
    private int mRange;//mFrontView侧拉时向左移动的最大距离,即mBackView的宽度
    public SwipeLayout(Context context) {
        this(context, null);
    }
    public SwipeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //1.初始ViewDragHelper
    private void init() {
        mCallback = new ViewDragHelper.Callback() {
            //3.在回调方法中处理触摸事件
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true; //允许所有子控件的滑动
            }
            //设定滑动的边界值
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (child == mFrontView) {
                    //前景View的滑动范围是(0~ -mRange)
                    if (left > 0) {
                        left = 0;
                    } else if (left < -mRange) {
                        left = -mRange;
                    }
                }
                if (child == mBackView) {
                    //背景View的滑动范围是(mWidth - mRange ~ mWidth)
                    if (left > mWidth) {
                        left = mWidth;
                    } else if (left < (mWidth - mRange)) {
                        left = mWidth - mRange;
                    }
                }
                //返回修正过的建议值
                return left;
            }
            //监听View的滑动位置的改变,同步前景View和背景View的滑动事件
            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                if (changedView == mFrontView) {
                    //当滑动前景View时,也需要滑动背景View
                    mBackView.offsetLeftAndRight(dx);
                } else if (changedView == mBackView) {
                    //当滑动背景View时,也需要滑动前景View
                    mFrontView.offsetLeftAndRight(dx);
                }
                // 兼容老版本
                invalidate();
            }
            //处理释放后的开启和关闭动作
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (xvel < 0) {
                    //有向左滑动的速度,则打开
                    open();
                } else if (xvel == 0 && mFrontView.getLeft() < -mRange / 2.0f) {
                    //前景View向左滑动的left小于背景View宽度一半的负值时,打开
                    open();
                } else {
                    //其他情况为关闭
                    close();
                }
            }
        };
        mDragHelper = ViewDragHelper.create(this, mCallback);
    }
    //2.传递触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    //获取子控件的引用
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackView = getChildAt(0); //获取背景View,即展示数据的Item的右边隐藏的侧滑布局
        mFrontView = getChildAt(1);//获取前景View,即展示数据的Item
    }
    //获取子控件的相关宽高信息
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mFrontView.getMeasuredWidth();
        mHeight = mFrontView.getMeasuredHeight();
        mRange = mBackView.getMeasuredWidth();
    }

    //确定子控件的初始位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutChildView(false);
    }
    /**
     * 放置子控件的位置
     *
     * @param isOpen 是否是打开前景View,true打开,false关闭
     */
    private void layoutChildView(boolean isOpen) {
        //计算前景View的位置,将坐标信息封装到矩形中
        Rect fontRect = computerFontViewRect(isOpen);
        //摆放前景View
        mFrontView.layout(fontRect.left, fontRect.top, fontRect.right, fontRect.bottom);
        //摆放背景View,left坐标是前景View的right坐标
        int left = fontRect.right;
        mBackView.layout(left, 0, left + mRange, mHeight);
        //由于上面是后摆放背景View,所以会覆盖前景View,因此需要通过下面的方式将前景View显示在前面
        bringChildToFront(mFrontView);
    }
    /**
     * 计算前景View的坐标
     *
     * @param isOpen 是否是打开前景View
     * @return
     */
    private Rect computerFontViewRect(boolean isOpen) {
        int left = isOpen ? -mRange : 0;
        return new Rect(left, 0, left + mWidth, mHeight);
    }
    /**
     * 打开侧边栏mBackView,默认平滑打开
     */
    public void open() {
        open(true);
    }
    /**
     * 打开侧边栏mBackView
     *
     * @param isSmooth 是否平滑打开
     */
    public void open(boolean isSmooth) {
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(mFrontView, -mRange, 0)) {
                //动画在继续
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutChildView(true);
        }
    }
    /**
     * 关闭侧边栏mBackView,默认平滑关闭
     */
    public void close() {
        close(true);
    }
    /**
     * 关闭侧边栏mBackView
     *
     * @param isSmooth 是否平滑关闭
     */
    public void close(boolean isSmooth) {
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(mBackView, mWidth, 0)) {
                //动画在继续
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutChildView(false);
        }
    }
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            //动画还在继续
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

}
