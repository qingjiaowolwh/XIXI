package com.klgz.library.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.klgz.library.R;

/**
 * Created by lwh on 2017/6/27.
 */

public class DialogHelper {

    /**
     * 网络请求失败重试Dialog
     * @param mContext
     * @param positiveCallback
     * @return
     */
    public static MaterialDialog showRetry(Context mContext,MaterialDialog.SingleButtonCallback positiveCallback) {
        return showBasicNoTitle(mContext, R.string.wangluoqingqiushibai, R.string.ok, positiveCallback, R.string.cancel, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 确定 取消无Title Dialog
     * @param mContext
     * @param content
     * @param positiveCallback
     * @return
     */
    public static MaterialDialog showOkAndCancel(Context mContext,@StringRes int content,MaterialDialog.SingleButtonCallback positiveCallback){
        return showBasicNoTitle(mContext, content, R.string.ok,positiveCallback,R.string.cancel, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    private static MaterialDialog showBasicNoTitle(Context mContext,@StringRes int content,@StringRes int positive,MaterialDialog.SingleButtonCallback positiveCallback,@StringRes int negative,MaterialDialog.SingleButtonCallback negativeCallback) {
        return new  MaterialDialog.Builder(mContext)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .onPositive(positiveCallback)
                .onNegative(negativeCallback)
                .show();
    }

    /**
     * 确定 取消有Title Dialog
     * @param mContext
     * @param title
     * @param content
     * @param positiveCallback
     * @return
     */
    public static MaterialDialog showOkAndCancelWithTitle(Context mContext,@StringRes int title,@StringRes int content,MaterialDialog.SingleButtonCallback positiveCallback){
        return showBasic(mContext,
                title,
                content,
                R.string.ok,
                positiveCallback,
                R.string.cancel,
                new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                dialog.dismiss();
            }
        });
    }

    private static MaterialDialog showBasic(Context mContext,@StringRes int title,@StringRes int content,@StringRes int positive,MaterialDialog.SingleButtonCallback positiveCallback,@StringRes int negative,MaterialDialog.SingleButtonCallback negativeCallback) {
        return new  MaterialDialog.Builder(mContext)
                .title(title)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .onPositive(positiveCallback)
                .onNegative(negativeCallback)
                .show();
    }

//    positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
//    passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.password);
//    public static MaterialDialog showBasicIcon(Context mContext) {
//        return new MaterialDialog.Builder(this)
//                .title(R.string.googleWifi)
//                .customView(R.layout.dialog_customview, true)
//                .positiveText(R.string.connect)
//                .negativeText(android.R.string.cancel)
//                .onPositive(
//                        (dialog1, which) -> showToast("Password: " + passwordInput.getText().toString()))
//                .build();
//    }

//    public static MaterialDialog showBasicLongContent(Context mContext,@StringRes int title,@StringRes int content) {
//        return new  MaterialDialog.Builder(mContext)
//                .title(title)
//                .content(content)
//                .positiveText(R.string.ok)
//                .negativeText(R.string.cancel)
//                .show();
//    }

//    public static MaterialDialog showBasicIcon(Context mContext) {
//       MaterialDialog dialog=new  MaterialDialog.Builder(mContext)
//                .iconRes(R.mipmap.ic_launcher)
//                .limitIconToDefaultSize() // limits the displayed icon size to 48dp
//                .title(R.string.useGoogleLocationServices)
//                .content(R.string.useGoogleLocationServicesPrompt)
//               .positiveText(R.string.ok)
//               .negativeText(R.string.cancel)
//                .show();
//        return dialog;
//    }

//    public static MaterialDialog showBasicCheckPrompt(Context mContext) {
//       MaterialDialog dialog=new  MaterialDialog.Builder(mContext)
//                .iconRes(R.mipmap.ic_launcher)
//                .limitIconToDefaultSize()
//                .title(Html.fromHtml(getString(R.string.permissionSample, getString(R.string.app_name))))
//                .positiveText(R.string.allow)
//                .negativeText(R.string.deny)
//                .onAny((dialog, which) -> showToast("Prompt checked? " + dialog.isPromptCheckBoxChecked()))
//                .checkBoxPromptRes(R.string.dont_ask_again, false, null)
//                .show();
//        return dialog;
//    }

}
