package com.megvii.annotationlibrary;

/**
 * Created by lwh on 2018/3/29.
 */

public interface ViewInjector<T> {
    void inject(T t, Object source);
}
