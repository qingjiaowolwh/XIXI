package com.klgz.library.api;


import com.klgz.library.util.PreferenceHelper;
import com.klgz.library.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiTools {

    private static final String TAG = ApiTools.class.getSimpleName();

    private static JSONObject buildTokenAndCheckID() {
        JSONObject data = new JSONObject();
        UserInfo info = PreferenceHelper.getUserInfo();
        try {
            if (info == null) {
                data.put("checkID", "");
                data.put("token", "");
            } else {
                data.put("checkID", info.getCheckID());
                data.put("token", info.getToken());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 通用的获取params无内容时的参数
     *
     * @return
     */
    public static String getNullParams() {
        JSONObject params = new JSONObject();
        JSONObject data = buildTokenAndCheckID();
        try {
            data.put("params", params);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 修改密码
     */
    public static String reqResetPassword(String phone, String oldPassword, String newPassword) {
        JSONObject params = new JSONObject();
        JSONObject data = buildTokenAndCheckID();
        try {
            params.put("phone", phone);
            params.put("password", oldPassword);
            params.put("newPassword", newPassword);

            data.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 找回密码
     */
    public static String reqFindPassword(String phone, String password, String code) {
        JSONObject params = new JSONObject();
        JSONObject data = buildTokenAndCheckID();
        try {
            params.put("phone", phone);
            params.put("password", password);
            params.put("code", code);
            data.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 用户登录
     */
    public static String reqLogin(String phone, String password) {
        JSONObject params = new JSONObject();
        JSONObject data = buildTokenAndCheckID();
        try {
            params.put("phone", phone);
            params.put("password", password);
            data.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }


    /**
     * 注册
     *
     * @param phone
     * @param password
     * @param code
     * @return
     */
    public static String reqRegister(String phone, String password, String code) {
        JSONObject params = new JSONObject();
        JSONObject data = buildTokenAndCheckID();
        try {
            params.put("phone", phone);
            params.put("password", password);
            params.put("code", code);

            data.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    /**
     * 获取验证码
     *
     * @param phoneNum
     * @param type
     * @return
     */
    public static String reqGetCode(String phoneNum, String type) {
        JSONObject params = new JSONObject();
        JSONObject data = buildTokenAndCheckID();
        try {
            params.put("phone", phoneNum);
            params.put("type", type);
            data.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data.toString();
    }
}