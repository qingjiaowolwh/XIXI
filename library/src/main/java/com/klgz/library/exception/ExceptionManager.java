package com.klgz.library.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.klgz.library.R;
import com.klgz.library.global.MyApplication;
import com.klgz.library.util.NetworkUtil;
import com.klgz.library.util.ToastUtil;

import org.json.JSONException;

import java.util.concurrent.TimeoutException;

/**
 * Created by lwh on 2017/6/22.
 * 异常管理类
 */

public class ExceptionManager {

    public static void handleException(Throwable e){
        if (!NetworkUtil.isNetwork(MyApplication.getInstance())) {
            ToastUtil.show(R.string.qingjianchawangluo);
        }else if (e instanceof TokenInvalidException){
            //token失效
            ToastUtil.show(e.getMessage());
        } else if (e instanceof ServerException){
            //服务器异常
            ToastUtil.show(e.getMessage());
        } else if (e instanceof TimeoutException){
            //超时
            ToastUtil.show(R.string.wangluoqingqiushibai);
        } else if (e instanceof JsonParseException|| e instanceof JSONException|| e instanceof ParseException){
            //数据解析异常
            ToastUtil.show(R.string.shujujiexiyichang );
        }else{
            //未知异常
            ToastUtil.show(R.string.weizhiyichang);
        }
    }
}
