package com.klgz.library.exception;

/**
 * Created by wty on 2016/10/31.
 * 服务器返回的异常
 */

public class ServerException extends Throwable {

    public ServerException(String msg) {
        super(msg);
    }
}
