package com.klgz.library.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.klgz.library.global.MyApplication;
import com.klgz.library.model.UserInfo;

public class PreferenceHelper {

    // 用户信息
    private final static String USERINFO = "userinfo";

    public static UserInfo getUserInfo() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        String str = settings.getString(USERINFO, null);
        if (str == null)
            return null;
        else {
            return new Gson().fromJson(str, UserInfo.class);
        }
    }

    public static boolean setUserInfo(UserInfo info) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().putString(USERINFO, new Gson().toJson(info)).commit();
    }


    public static boolean removeUserInfo() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().remove(USERINFO).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.getBoolean(key, defValue);
    }

    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().putBoolean(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.getInt(key, defValue);
    }

    public static boolean putInt(String key, int value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().putInt(key, value).commit();
    }

    public static long getLong(String key, long defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.getLong(key, defValue);
    }

    public static boolean putLong(String key, long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().putLong(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.getString(key, defValue);
    }

    public static boolean putString(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().putString(key, value).commit();
    }

    public static boolean remove(String key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().remove(key).commit();
    }

    public static boolean clear() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        return sp.edit().clear().commit();
    }

    public static void registerOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
                    .registerOnSharedPreferenceChangeListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterOnPrefChangeListener(OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance())
                    .unregisterOnSharedPreferenceChangeListener(listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
