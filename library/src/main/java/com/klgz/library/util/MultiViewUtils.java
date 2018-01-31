package com.klgz.library.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.klgz.library.R;
import com.klgz.library.widgets.MultiStateView;

/**
 * Created by lwh on 2017/7/6.
 */

public class MultiViewUtils {
    private Handler mHandler=new Handler(Looper.getMainLooper());
    private Runnable run;
    private Context mContext;
    private MultiStateView mMultiStateView;
    private @LayoutRes int layoutRes;

    public MultiViewUtils(Context mContext, MultiStateView mMultiStateView,@LayoutRes int layoutRes) {
        this.mContext = mContext;
        this.mMultiStateView = mMultiStateView;
        this.layoutRes=layoutRes;
        initMultiStateView();
    }

    public void initMultiStateView() {
        if (mMultiStateView!=null){
            mMultiStateView.setLoadingView(R.layout.layout_loading_view);
            mMultiStateView.setErrorView(R.layout.layout_error_view);
            mMultiStateView.setEmptyView(R.layout.layout_empty_view);
            mMultiStateView.setContentView(layoutRes);
            // 网络请求失败后的重试按
            mMultiStateView.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.retry).setOnClickListener(listener);
        }
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (NetworkUtil.isNetwork(mContext)) {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
                if (run != null)
                    mHandler.post(run);
            } else {
                ToastUtil.show(mContext.getResources().getString(R.string.qingjianchawangluo));
            }
        }
    };

    /**
     * 网络请求重试
     * @param run
     */
    public void retry(Runnable run){
        this.run=run;
        mMultiStateView.setErrorState();
    }

    public MultiStateView getMultiStateView() {
        return mMultiStateView;
    }

}
