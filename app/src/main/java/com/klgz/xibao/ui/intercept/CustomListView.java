package com.klgz.xibao.ui.intercept;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by lwh on 2017/12/26.
 * CustomScrollView:   dispatchTouchEvent -> CustomScrollView:   onInterceptTouchEvent -> CustomListView:   dispatchTouchEvent
 * -> CustomListView:   onInterceptTouchEvent -> CustomListView:   onTouchEvent
 */

public class CustomListView extends ListView {

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

 //****************内部拦截法**********************//
//重写父view
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        System.out.println("CustomScrollView:   onInterceptTouchEvent");
//        //父viewgroup代码     (要确保down是不拦截，move和up时要拦截)
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            return false;
//        } else {
//            return true;
//        }
//    }

//    switch (ev.getAction()) {
//        case MotionEvent.ACTION_DOWN:
//            //通知父viewgroup处理该事件   true不拦截  false拦截
//            getParent().requestDisallowInterceptTouchEvent(true);
//            break;
//        case MotionEvent.ACTION_MOVE:
//            //通知父viewgroup来拦截处理该事件
//            getParent().requestDisallowInterceptTouchEvent(true);
//        case MotionEvent.ACTION_UP:
//            break;
//    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("CustomListView:   dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("CustomListView:   onInterceptTouchEvent");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println("CustomListView:   onTouchEvent");
        requestDisallowInterceptTouchEvent(false);
        return super.onTouchEvent(ev);
    }

}
