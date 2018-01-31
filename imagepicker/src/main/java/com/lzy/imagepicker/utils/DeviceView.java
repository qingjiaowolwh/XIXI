package com.lzy.imagepicker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


/**
 * 带取景框的 surface view
 *
 * @author Geek_Soledad (msdx.android@qq.com)
 * @version 2015-12-28 3.2
 * @since 2015-12-28 3.2
 */
public class DeviceView extends View {
    private final Paint mPaint;

    private int DEFUALT_WIDTH = 300;
    private int DEFUALT_HEIGHT = 400;
    private Rect mBorder = new Rect();
    private Bitmap mBitmap;
    private Matrix matrix;
    public DeviceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mPaint.setDither(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();
        Path myPath=new Path();
        myPath.moveTo(0,height/5*4);
        myPath.lineTo(width/5,height/10*7);
        myPath.lineTo(250,100);
        myPath.lineTo(300,80);
        canvas.drawPath(myPath, mPaint);

    }

    public Rect getBorder() {
        return mBorder;
    }


}