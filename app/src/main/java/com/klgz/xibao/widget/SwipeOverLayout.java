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

public class SwipeOverLayout extends FrameLayout {
    private static final String TAG=SwipeOverLayout.class.getSimpleName()+":";
    private ViewDragHelper.Callback mCallback;
    private ViewDragHelper mDragHelper;
    private View mBackView; //item的侧边布局
    private View mFrontView;//当前显示的item布局
    private int mWidth; //屏幕的宽度,mFrontView的宽度
    private int mHeight; //mFrontView的高度
    private int mRange;//mFrontView侧拉时向左移动的最大距离,即mBackView的宽度
    public SwipeOverLayout(Context context) {
        this(context, null);
    }
    public SwipeOverLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SwipeOverLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //1.初始ViewDragHelper
    private void init() {
        mCallback = new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return mBackView==child;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                if (left > mWidth) {
                    left = mWidth;
                } else if (left < (mWidth - mRange)) {
                    left = mWidth - mRange;
                }
                return left;
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId)
            {
                mDragHelper.captureChildView(mBackView, pointerId);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (xvel<0){
                    open();
                }else if(xvel>0){
                    close();
                }else if(mWidth-mBackView.getLeft()>mRange/2.0f){
                    open();
                }else{
                    close();
                }
            }
        };
        mDragHelper = ViewDragHelper.create(this, mCallback);
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBackView = getChildAt(1);
        mFrontView = getChildAt(0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mFrontView.getMeasuredWidth();
        mHeight = mFrontView.getMeasuredHeight();
        mRange = mBackView.getMeasuredWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        layoutChildView(false);
    }


    private void layoutChildView(boolean isOpen) {
        Rect fontRect = computerFontViewRect(isOpen);
        int left = fontRect.right;
        mBackView.layout(left, 0, left + mRange, mHeight);
    }

    private Rect computerFontViewRect(boolean isOpen) {
        int left = isOpen ? -mRange : 0;
        return new Rect(left, 0, left + mWidth, mHeight);
    }

    public void open() {
        open(true);
    }

    public void open(boolean isSmooth) {
        if (isSmooth) {
            if (mDragHelper.smoothSlideViewTo(mBackView, mWidth-mRange, 0)) {
                //动画在继续
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            layoutChildView(true);
        }
    }

    public void close() {
        close(true);
    }

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
