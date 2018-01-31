package com.klgz.library.util;

import com.klgz.library.model.UserInfo;

public class UserInfoUtil {

    public static boolean isLogin() {
        UserInfo info = PreferenceHelper.getUserInfo();
        return info == null;
    }

    public static void logout() {
        PreferenceHelper.removeUserInfo();//删除用户信息
//        PreferenceHelper.remove(Constants.FIRST);//没有引导页临时删除
    }

    public static void tokenInvalid() {
        ToastUtil.show("登录信息过时，请重新登录");
        logout();
//        RxBusManager.post(RxBusEvent.KEY_ALL_FINISH, "");//结束所有页面
//        LoginActivity.actionStart(MyApplication.getInstance());//启动登录页
    }
}
