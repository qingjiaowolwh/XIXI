package com.klgz.library.api;

import com.klgz.library.api.model.BaseModel;
import com.klgz.library.api.model.Status;
import com.klgz.library.exception.ServerException;
import com.klgz.library.exception.TokenInvalidException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by asus on 2016/10/31.
 */

public class RxHelper {

    public static <T> Observable.Transformer<T,T> onMain(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static Throwable resultError(Status status) {
        if (status.isTokenInvalid()) {
            return new TokenInvalidException(status.getMessage());
        } else {
            return new ServerException(status.getMessage());
        }
    }

    public static <T> Observable.Transformer<BaseModel<T>, T> handleResult() {
        return new Observable.Transformer<BaseModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseModel<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseModel<T> result) {
                        if (result.isSuccess()) {
                            return createData(result.getData());
                        } else {
                            return Observable.error(resultError(result.getStatus()));
                        }
                    }
                });
            }
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}