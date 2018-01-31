package com.klgz.library.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * Created by lwh on 2017/6/28.
 */

public class ImageUtil {

    public static RequestManager with(Activity mActivity){
       return Glide.with(mActivity);
    }

    public static RequestManager with(Context mContext){
        return Glide.with(mContext);
    }

    public static RequestManager with(Fragment mFragment){
        return Glide.with(mFragment);
    }
}
