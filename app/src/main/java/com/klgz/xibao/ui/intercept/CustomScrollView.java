package com.klgz.xibao.ui.intercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by lwh on 2017/12/26.
 * dispatchTouchEvent -> onInterceptTouchEvent -> onTouchEvent
 */

public class CustomScrollView extends ScrollView {


    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("CustomScrollView:   dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("CustomScrollView:   onInterceptTouchEvent");
        //父viewgroup代码     (要确保down是不拦截，move和up时要拦截)
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println("CustomScrollView:   onTouchEvent");
        return super.onTouchEvent(ev);
    }
}
