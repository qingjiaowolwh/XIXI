package com.klgz.library.api;


import com.klgz.library.api.model.BaseModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    @FormUrlEncoded
    @POST("api.php/?s=/User/getCode")
    Observable<BaseModel<List<String>>> getCode(@Field("data") String data);
//
//    @FormUrlEncoded
//    @POST("api.php/?s=/User/register")
//    Observable<BaseModel<UserInfo>> register(@Field("data") String data);
//
//    @FormUrlEncoded
//    @POST("api.php/?s=/Highlights/HighlightsList")
//    Observable<BaseModel<List<Highlight>>> getHighlightsList(@Field("data") String data);


}