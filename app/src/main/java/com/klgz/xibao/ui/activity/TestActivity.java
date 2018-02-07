package com.klgz.xibao.ui.activity;

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

/**
 * Created by lwh on 2017/8/30.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        showAlertDialog();
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_PICK);
//        intent.setData(Contacts.People.CONTENT_URI);
//        startActivity(intent);




    }




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
