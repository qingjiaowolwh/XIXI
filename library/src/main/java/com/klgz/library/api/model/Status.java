package com.klgz.library.api.model;

public class Status {
    /*
     * "status": { "message": "SUCCESS", "systemTime":
	 * "2015-12-26 18:25:28.734", "code": 200 }
	 */

    private String msg;
    private String systemTime;
    private int code;

    public String getMessage() {
        return msg;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 数据正常
     */
    private static final int CODE_SUCCESS = 200;
    /**
     * 数据错误
     */
    private static final int CODE_FAIL = -100;

    /**
     * token失效
     */
    private static final int TOKEN_INVALID = 20302;
    /**
     * 用户不存在
     */
    private static final int NO_USER = 20301;

    boolean isSuccess() {
        return code == CODE_SUCCESS;
    }

    public boolean isTokenInvalid() {
        return code == TOKEN_INVALID;
    }
}
