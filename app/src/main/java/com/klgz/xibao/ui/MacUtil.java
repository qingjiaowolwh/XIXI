package com.klgz.xibao.ui;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Created by lwh on 2018/1/29.
 */

public class MacUtil {
    private static final String TAG=MacUtil.class.getSimpleName();
    /**
     * 根据wifi信息获取本地mac
     * @param context
     * @return
     * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
     */
    public static String getLocalMacAddressFromWifiInfo(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo winfo = wifi.getConnectionInfo();
        String mac =  winfo.getMacAddress();
        return mac;
    }

    /**
     * 获取mac地址
     * @param context
     * @return
     */
    public static   String getMacAddress(Context context){

        //如果是6.0以下，直接通过wifimanager获取
        Log.e(TAG,"wifimanager"+getMacAddress0(context));
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            String macAddress0 = getMacAddress0(context);
            if(!TextUtils.isEmpty(macAddress0)){
                return macAddress0;
            }
        }

        String str="";
        String macSerial="";
        try {
            Process pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.e(TAG,"exec(\"cat/sys/class/net/wlan0/address\")"+macSerial);
        if (macSerial == null || "".equals(macSerial)) {
            try {
                Log.e(TAG,"loadFileAsString(\"/sys/class/net/eth0/address\")"+loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17));
                return loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return macSerial;
    }


    private static   String getMacAddress0(Context context) {
        if (isAccessWifiStateAuthorized(context)) {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = null;
            try {
                wifiInfo = wifiMgr.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "";

    }

    /**
     * Check whether accessing wifi state is permitted
     *
     * @param context
     * @return
     */
    private static boolean isAccessWifiStateAuthorized(Context context) {
        if (PackageManager.PERMISSION_GRANTED == context
                .checkCallingOrSelfPermission("android.permission.ACCESS_WIFI_STATE")) {
            return true;
        } else
            return false;
    }


    private static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }
    private static   String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }
}
