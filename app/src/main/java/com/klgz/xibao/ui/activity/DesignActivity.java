package com.klgz.xibao.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.klgz.xibao.R;
import com.klgz.xibao.utils.RegexUtil;

/**
 * Created by lwh on 2018/2/6.
 */

public class DesignActivity extends AppCompatActivity {
    private TextInputLayout mobile_layout;
    private EditText mobile_edit;
    private TextInputLayout password_layout;
    private EditText password_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        mobile_layout= (TextInputLayout) findViewById(R.id.mobile_layout);
        mobile_edit= (EditText) findViewById(R.id.mobile);
        mobile_layout.setHint("请输入手机号码");

        password_layout= (TextInputLayout) findViewById(R.id.password_layout);
        password_edit= (EditText) findViewById(R.id.password);
        password_layout.setHint("请输入密码");

        mobile_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!RegexUtil.matcherRegex(RegexUtil.REGEX_MOBIL_PHONE_NO,mobile_edit.getText().toString().trim())){
                    mobile_layout.setErrorEnabled(true);
                    mobile_layout.setError("号码输入错误");
                }else{
                    mobile_layout.setErrorEnabled(false);
                }
            }
        });

        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password =password_edit.getText().toString().trim();
                if (password.length()>12||password.length()<6){
                    password_layout.setErrorEnabled(true);
                    password_layout.setError("密码必须6-12位之间");
                }else{
                    password_layout.setErrorEnabled(false);
                }
            }
        });
    }
}
