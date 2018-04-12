package com.klgz.xibao.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.klgz.xibao.R;
import com.klgz.xibao.ui.fragment.PermissionsFragment;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.utils.AppConstant;
import com.zmnedu.pickerview.TimePickerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lwh on 2017/8/30.
 */

public class TestActivity extends AppCompatActivity {
    private TimePickerView timePickerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        decimalFormat();




    }

    /**
     * 数字过大科学计算法问题
     */
    private void decimalFormat() {
        DecimalFormat format=new  DecimalFormat("##.00");
        double d=00111011111.20;
        String str=format.format(d);
        System.out.println("TestActivity"+str);
    }

    /**
     * 增强for循环
     */
    private void iterator() {
        List<String> list = new ArrayList<String>();
        list.add("a1");
        list.add("a2");
        list.add("a3");
        list.add("a4");
        list.add("a5");

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String v = it.next();
            if ("a2".equals(v)) {
                it.remove();
            }
        }

        for (String v : list) {
            System.out.println(v);
        }
    }

    private void takePhoto() {

        PermissionsFragment.getInstance(this).requestPermissions(new PermissionsFragment.PermissionsCallback() {
            @Override
            public void requestSuccess() {
                ImagePicker imagePicker=ImagePicker.getInstance();
                imagePicker.setCrop(false);
                imagePicker.takePicture(TestActivity.this, AppConstant.REQUEST_CODE.CAMERA);
            }

            @Override
            public void requestFail() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA);

    }


    /**
     * dialog显示在底部
     */
    private void showAlertDialog(){
        View view= LayoutInflater.from(this).inflate(R.layout.layout_dialog,null);
        AlertDialog dialog=new AlertDialog
                .Builder(this,R.style.ActionSheetDialogStyle)
                .setCancelable(false)
                .create();
        dialog.show();
        //dialog.setView 距离底部总会有间距
        dialog.setContentView(view);
        Window window=dialog.getWindow();
        window.setLayout(
                window.getContext().getResources().getDisplayMetrics().widthPixels,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
    }
}
