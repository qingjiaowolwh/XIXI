package com.klgz.library.util;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;

/**
 * Created by lwh on 2017/7/6.
 */

public class ViewUtils {

    public static void initSwipeRefreshLayout(Context mContext, SwipeRefreshLayout swipeRefreshLayout) {
        if (swipeRefreshLayout!=null){
            swipeRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_light, android.R.color.holo_orange_light,
                    android.R.color.holo_green_light, android.R.color.holo_red_light);
            // 这句话是为了，第一次进入页面的时候显示加载进度条
            swipeRefreshLayout.setProgressViewOffset(
                    false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                            , 24,mContext. getResources().getDisplayMetrics()));
        }
    }
}
