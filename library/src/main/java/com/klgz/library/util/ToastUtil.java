/**
 *
 */
package com.klgz.library.util;

import android.widget.Toast;

import com.klgz.library.global.MyApplication;

public class ToastUtil {

    private static Toast toast;

    static {
        try {
            toast = Toast.makeText(MyApplication.getInstance().getApplicationContext(), "", Toast.LENGTH_SHORT);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void show(String info) {
        toast.setText(info);
        showToast();
    }

    public static void show(int info) {
        toast.setText(info);
        showToast();
    }

    private static void showToast() {
            toast.show();
    }
}
