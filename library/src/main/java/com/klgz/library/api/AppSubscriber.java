package com.klgz.library.api;

import android.util.Log;

import com.klgz.library.exception.ExceptionManager;

import rx.Subscriber;

/**
 * Created by asus on 2016/5/17.
 */
public abstract class AppSubscriber<T> extends Subscriber<T> {

    private final static String TAG = AppSubscriber.class.getSimpleName();

    public interface IOnStartAction {
        void onStartAction();

        void onErrorAction();

        void onFinishAction();
    }

    private IOnStartAction iosa;

    public AppSubscriber() {
    }

    public AppSubscriber(IOnStartAction iosa) {
        this.iosa = iosa;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (iosa != null)
            iosa.onStartAction();
    }

    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted: ");
        if (iosa != null)
            iosa.onFinishAction();
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError: ", e);
        if (iosa != null)
            iosa.onErrorAction();
        ExceptionManager.handleException(e);
    }

    @Override
    public void onNext(T t) {
        call(t);
    }

    public abstract void call(T t);
}
