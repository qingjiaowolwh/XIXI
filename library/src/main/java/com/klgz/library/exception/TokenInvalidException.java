package com.klgz.library.exception;

/**
 * Created by wty on 2016/10/31.
 * token失效的异常
 */

public class TokenInvalidException extends ServerException {

    public TokenInvalidException(String msg) {
        super(msg);
    }
}
