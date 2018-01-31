package com.klgz.library.api;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.klgz.library.R;
import com.klgz.library.exception.ServerException;
import com.klgz.library.exception.TokenInvalidException;
import com.klgz.library.global.MyApplication;
import com.klgz.library.util.NetworkUtil;
import com.klgz.library.util.ToastUtil;
import com.klgz.library.util.UserInfoUtil;

import rx.Subscriber;

/**
 * Created by asus on 2016/12/5.
 */
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private final static String TAG = RxSubscriber.class.getSimpleName();

    private FrameLayout rootView;
    private Context mContext;
    private View mLoadingView;

    protected RxSubscriber(View parent) {
        this.rootView = (FrameLayout) parent;
        mContext = rootView.getContext();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart: ");
        if (rootView != null) {
            if (mLoadingView == null) {
//                mLoadingView = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_view, rootView, false);
            }
            rootView.removeView(mLoadingView);
            rootView.addView(mLoadingView);
        }
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted: ");
        if (rootView != null && mLoadingView != null)
            rootView.removeView(mLoadingView);
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        if (rootView != null && mLoadingView != null)
            rootView.removeView(mLoadingView);
        if (!NetworkUtil.isNetwork(MyApplication.getInstance())) {
            ToastUtil.show(R.string.qingjianchawangluo);
        } else if (e instanceof TokenInvalidException) {
            UserInfoUtil.tokenInvalid();
        } else if (e instanceof ServerException) {
            ToastUtil.show(e.getMessage());
        } else {
            ToastUtil.show(R.string.wangluoqingqiushibai);
        }
    }

    @Override
    public void onNext(T t) {
        Log.i(TAG, "onNext: ");
    }

}
