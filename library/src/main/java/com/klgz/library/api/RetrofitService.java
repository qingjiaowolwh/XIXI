package com.klgz.library.api;

import android.util.Log;

import com.klgz.library.BuildConfig;
import com.klgz.library.global.Constants;
import com.klgz.library.global.MyApplication;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by asus on 2016/3/10.
 */
public class RetrofitService {

    public static final String TAG = RetrofitService.class.getSimpleName();

    private static ApiService apiService;

    public static ApiService getInstance() {
        if (apiService == null) {
            synchronized (TAG) {
                if (apiService == null) {
                    apiService = initRetrofit();
                }
            }

        }
        return apiService;
    }

    private static ApiService initRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (!BuildConfig.DEBUG)
                Log.i("OkHttp", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(new File(MyApplication.getInstance().getCacheDir(), Constants.APP_NAME), 10 * 1024 * 1024))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HOST_COMMON)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new MyGsonBuilder().createGson()))
                .build();
//        final Picasso picasso = new Picasso.Builder(MyApplication.getInstance())
//                .downloader(new OkHttp3Downloader(okHttpClient))
//                .build();
//        Picasso.setSingletonInstance(picasso);
        return retrofit.create(ApiService.class);
    }
}
