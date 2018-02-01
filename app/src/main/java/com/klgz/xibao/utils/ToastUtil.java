package com.klgz.xibao.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Toast 工具类
 * 主要     显示、
 * 取消显示、
 * 短时间显示、
 * 长时间显示、
 * 自定义时间显示、
 * 显示位置
 * 自定义view显示
 */
public class ToastUtil {

  private static boolean isShow = true;//默认显示
  private static Toast mToast = null;//全局唯一的Toast

  /*private控制不应该被实例化*/
  private ToastUtil() {
    throw new UnsupportedOperationException("不能被实例化");
  }


  /**
   * 全局控制是否显示 Toast
   *
   * @param isShowToast 是否显示
   */
  public static void controlShow(boolean isShowToast) {
    isShow = isShowToast;
  }

  /**
   * 取消显示 Toast
   */
  public void cancelToast() {
    if (isShow && mToast != null) {
      mToast.cancel();
    }
  }

  /**
   * 短时间显示Toast
   *
   * @param context 上下文
   * @param message 消息内容
   */
  public static void showShort(Context context, CharSequence message) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT);
      } else {
        mToast.setText(message);
      }
      mToast.show();
    }
  }

  /**
   * 短时间显示Toast
   *
   * @param context 上下文
   * @param resId 资源 id
   */
  public static void showShort(Context context, int resId) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_SHORT);
      } else {
        mToast.setText(resId);
      }
      mToast.show();
    }
  }

  /**
   * 长时间显示Toast
   *
   * @param context 上下文
   * @param message 消息内容
   */
  public static void showLong(Context context, CharSequence message) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG);
      } else {
        mToast.setText(message);
      }
      mToast.show();
    }
  }

  /**
   * 长时间显示Toast
   *
   * @param context 上下文
   * @param resId 资源id
   */
  public static void showLong(Context context, int resId) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), resId, Toast.LENGTH_LONG);
      } else {
        mToast.setText(resId);
      }
      mToast.show();
    }
  }

  /**
   * 自定义显示Toast时间
   *
   * @param context 上下文
   * @param message 消息内容
   * @param duration 显示时间(单位:毫秒)
   */
  public static void show(Context context, CharSequence message, int duration) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), message, duration);
      } else {
        mToast.setText(message);
      }
      mToast.show();
    }
  }

  /**
   * 自定义显示Toast时间
   *
   * @param context 上下文
   * @param resId 资源id
   * @param duration 显示时间(单位:毫秒)
   */
  public static void show(Context context, int resId, int duration) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), resId, duration);
      } else {
        mToast.setText(resId);
      }
      mToast.show();
    }
  }

  /**
   * 自定义Toast的View
   *
   * @param context 上下文
   * @param message 消息内容
   * @param duration 显示时间(单位:毫秒)
   * @param view 自定义View
   */
  public static void customToastView(Context context, CharSequence message, int duration,
      View view) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), message, duration);
      } else {
        mToast.setText(message);
      }
      if (view != null) {
        mToast.setView(view);
      }
      mToast.show();
    }
  }

  /**
   * 自定义Toast的位置
   *
   * @param context 上下文
   * @param message 消息内容
   * @param duration 显示时间(单位:毫秒)
   * @param gravity 显示位置
   * @param xOffset x 偏移
   * @param yOffset y 偏移
   */
  public static void customToastGravity(Context context, CharSequence message, int duration,
      int gravity, int xOffset, int yOffset) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), message, duration);
      } else {
        mToast.setText(message);
      }
      mToast.setGravity(gravity, xOffset, yOffset);
      mToast.show();
    }
  }

  /**
   * 自定义带图片和文字的Toast，上面是图片，下面是文字
   *
   * @param context 上下文
   * @param message 消息内容
   * @param iconResId 图片的资源id
   * @param duration 显示时间
   * @param gravity 显示位置
   * @param xOffset x 偏移
   * @param yOffset y 偏移
   */
  public static void showToastWithImageAndText(Context context, CharSequence message, int iconResId,
      int duration, int gravity, int xOffset, int yOffset) {
    if (isShow) {
      if (mToast == null) {
        mToast = Toast.makeText(context.getApplicationContext(), message, duration);
      } else {
        mToast.setText(message);
      }
      mToast.setGravity(gravity, xOffset, yOffset);
      LinearLayout toastView = (LinearLayout) mToast.getView();
      ImageView imageView = new ImageView(context);
      imageView.setImageResource(iconResId);
      toastView.addView(imageView, 0);
      mToast.show();
    }
  }
}
