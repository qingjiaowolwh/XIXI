package com.klgz.library.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lwh on 2017/7/6.
 */

public class RecyclerViewUtils {

    public static void addScrollToTopListener(RecyclerView recyclerView, final LinearLayoutManager manager, final OnScrollToTopListener topListener){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView paramAnonymousRecyclerView, int paramAnonymousInt) {
                super.onScrollStateChanged(paramAnonymousRecyclerView, paramAnonymousInt);
                if ((paramAnonymousInt == 0) && (manager.findFirstCompletelyVisibleItemPosition() == 0))
                topListener.scrollToTop();
            }
        });
    }

    public interface OnScrollToTopListener{
        void scrollToTop();
    }
}
