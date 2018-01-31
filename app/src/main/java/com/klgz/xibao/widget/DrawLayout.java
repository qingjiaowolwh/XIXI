package com.klgz.xibao.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by lwh on 2017/11/20.
 */

public class DrawLayout extends LinearLayout {
    private final static String TAG = DrawLayout.class.getSimpleName();
    private ViewDragHelper mDragHelper;
//    private View mRightView;
    private View mLeftView;

    private Point mAutoBackOriginPos = new Point();

    public DrawLayout(Context context) {
        this(context, null);
    }


    public DrawLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new CustomCallBack());
        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }


    private class CustomCallBack extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mLeftView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        //处理可点击问题
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //mAutoBackView手指释放时可以自动回去
            if (releasedChild == mLeftView) {
                mDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mLeftView, pointerId);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean event = mDragHelper.shouldInterceptTouchEvent(ev);
        Log.e(TAG, "event:" + event);
        return event;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mLeftView.getLeft();
        mAutoBackOriginPos.y = mLeftView.getTop();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mLeftView = getChildAt(0);
//        mAutoBackView = getChildAt(1);
//        mRightView = getChildAt(2);
    }
}

