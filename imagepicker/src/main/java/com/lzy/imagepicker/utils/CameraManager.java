package com.lzy.imagepicker.utils;

/**
 * Created by yue on 2015/8/13.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Environment;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.imagepicker.bean.AspectRatio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@SuppressWarnings("deprecation")
public class CameraManager {
    private static CameraManager cameraUtil;
    private Camera mCamera;
    //0为后置 1为前置
    private int cameraId;
    /**
     * 只添加了闪光的的开和关
     */
    private String flashMode = Camera.Parameters.FLASH_MODE_OFF;
    public static String FLASH_MODE_OFF = Camera.Parameters.FLASH_MODE_OFF;
    /**
     * 打开摄像头
     * Camera.CameraInfo.CAMERA_FACING_BACK  0
     * Camera.CameraInfo.CAMERA_FACING_FRONT  1
     */
    private int mFacing = Camera.CameraInfo.CAMERA_FACING_BACK;

    //所有支持的 预览size
    private static final SortedSet<AspectRatio> supportPreviewSizes = new TreeSet<>();
    //所有支持的 图片size
    private static final SortedSet<AspectRatio> supportPictureSizes = new TreeSet<>();
    //DEFAULT_ASPECT_RATIO 预览size
    private static final SortedSet<AspectRatio> previewSizes = new TreeSet<>();
    //DEFAULT_ASPECT_RATIO 图片size
    private static final SortedSet<AspectRatio> pictureSizes = new TreeSet<>();
    //默认宽高比(相机的宽高  与实际相反)
    public static final AspectRatio DEFAULT_ASPECT_RATIO = new AspectRatio(4, 3);
    //默认resultCode
    public static final int DEFAULT_RESULT_CODE = 1314;
    //图片输出路径
    public static final String OUT_PATH = "out_path";
    //最小宽度
    public static final int MIN_WIDTH = 720;

    public static CameraManager getInstance() {
        if (cameraUtil == null)
            cameraUtil = new CameraManager();
        return cameraUtil;
    }


    /**
     * 开始方法  在onResume中调用
     * @param mContext
     * @param holder
     * @param surfaceContentRly
     * @return
     */
    public boolean start(Activity mContext, SurfaceHolder holder, View surfaceContentRly) {
        boolean isStart = openCamera();
        setUp(mContext, holder, surfaceContentRly);
        return isStart;
    }

    /**
     * 设置相机参数
     * @param mContext
     * @param holder
     * @param surfaceContentRly
     */
    public void setUp(Activity mContext, SurfaceHolder holder, View surfaceContentRly) {
        adjustCameraParameters(mContext, surfaceContentRly);
        setUpPreView(mContext, holder);

    }

    private boolean openCamera() {
        try {
            if (mCamera == null) {
                mCamera = Camera.open(cameraId);
            }
        } catch (Exception e) {
            //小米 360 魅族等手机
            return false;
        }
        return true;

    }

    /**
     * 释放相机资源  onPause中需调用
     */
    public void release() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }


    private void adjustCameraParameters(Activity mContext, View surfaceContentRly) {
        int width = getScreenWidth(mContext);
        //初始相机高度(防止没有获取到mCamera 图片变形)
        ViewGroup.LayoutParams layoutParams = surfaceContentRly.getLayoutParams();
        layoutParams.height = (int) (CameraManager.DEFAULT_ASPECT_RATIO.toFloat() * width);
        surfaceContentRly.setLayoutParams(layoutParams);
        if (mCamera == null)
            return;
        Camera.Parameters parameters = mCamera.getParameters();
        //设置对焦
        List<String> modes = parameters.getSupportedFocusModes();
        if (modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else {
            parameters.setFocusMode(modes.get(0));
        }

        supportPreviewSizes.clear();
        supportPictureSizes.clear();
        previewSizes.clear();
        pictureSizes.clear();
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            AspectRatio mAspectRatio = new AspectRatio(size.width, size.height);
            supportPreviewSizes.add(mAspectRatio);
            if (mAspectRatio.toFloat() == DEFAULT_ASPECT_RATIO.toFloat()) {
                previewSizes.add(mAspectRatio);
            }
        }

        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            AspectRatio mAspectRatio = new AspectRatio(size.width, size.height);
            supportPictureSizes.add(mAspectRatio);
            if (mAspectRatio.toFloat() == DEFAULT_ASPECT_RATIO.toFloat()) {
                pictureSizes.add(mAspectRatio);
            }
        }
        AspectRatio size = chooseOptimalSize(MIN_WIDTH, previewSizes);
        if (size == null) {
            size = chooseOptimalSize(MIN_WIDTH, supportPreviewSizes);
        }
        parameters.setPreviewSize(size.getWidth(), size.getHeight());

        int height = (int) (width * size.toFloat());
        ViewGroup.LayoutParams lp = surfaceContentRly.getLayoutParams();
        lp.height = height;
        surfaceContentRly.setLayoutParams(lp);

        AspectRatio pictureSize = chooseOptimalSize(MIN_WIDTH, pictureSizes);
        if (pictureSize == null) {
            pictureSize = chooseOptimalSize(MIN_WIDTH, supportPictureSizes);
        }
        parameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());

        parameters.setRotation(CameraManager.calcCameraRotation(mContext, cameraId));
        parameters.setPictureFormat(ImageFormat.JPEG);
        mCamera.setParameters(parameters);
    }

    private static int getScreenWidth(Activity mContext) {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    private static AspectRatio chooseOptimalSize(int width, SortedSet<AspectRatio> aspectRatios) {
        AspectRatio result = null;
        for (AspectRatio aspectRatio : aspectRatios) {
            if (width <= aspectRatio.getHeight()) {
                return aspectRatio;
            }
            result = aspectRatio;
        }
        return result;
    }

    private void setUpPreView(Activity mContext, SurfaceHolder holder) {
        if (mCamera == null)
            return;
        mCamera.stopPreview();
        try {

            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.setDisplayOrientation(calcDisplayOrientation(mContext, cameraId));
        mCamera.startPreview();
    }


    private static int calcDisplayOrientation(Activity activity, int cameraId) {
        Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, mCameraInfo);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int screenOrientationDegrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                screenOrientationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                screenOrientationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                screenOrientationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                screenOrientationDegrees = 270;
                break;
        }
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            return (360 - (mCameraInfo.orientation + screenOrientationDegrees) % 360) % 360;
        } else {  // back-facing
            return (mCameraInfo.orientation - screenOrientationDegrees + 360) % 360;
        }
    }

    private static int calcCameraRotation(Activity activity, int cameraId) {
        Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, mCameraInfo);
        int screenOrientationDegrees = activity.getWindowManager().getDefaultDisplay().getRotation();
        if (mCameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            return (mCameraInfo.orientation + screenOrientationDegrees) % 360;
        } else {  // back-facing
            final int landscapeFlip = isLandscape(screenOrientationDegrees) ? 180 : 0;
            return (mCameraInfo.orientation + screenOrientationDegrees + landscapeFlip) % 360;
        }
    }

    private static boolean isLandscape(int orientationDegrees) {
        return (orientationDegrees == 90 || orientationDegrees == 270);
    }

    /**
     * 设置闪光灯 默认FLASH_MODE_OFF关闭
     */
    public void setCameraFlash() {
        if (flashMode.equals(Camera.Parameters.FLASH_MODE_ON)) {
            flashMode = Camera.Parameters.FLASH_MODE_OFF;
        } else {
            flashMode = Camera.Parameters.FLASH_MODE_ON;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(flashMode);
        mCamera.setParameters(parameters);

    }

    public String getFlashPosition() {
        return flashMode;
    }


    public void setFacing(Activity mContext, SurfaceView surfaceView, ViewGroup surfaceContentRly) {
        if (mFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
        } else {
            mFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        release();
        Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
        for (int i = 0, count = Camera.getNumberOfCameras(); i < count; i++) {
            Camera.getCameraInfo(i, mCameraInfo);
            if (mCameraInfo.facing == mFacing) {
                cameraId = i;
            }
        }

        start(mContext, surfaceView.getHolder(), surfaceContentRly);
    }

    public void takePicture(final Activity mContext) {
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                String outPath = saveImage(mContext, data);

                Intent intent = new Intent();
                intent.putExtra(OUT_PATH, outPath);
                mContext.setResult(DEFAULT_RESULT_CODE, intent);
            }
        });
    }


    /**
     * 根据  MIN_WIDTH/options.outWidth
     * @param mContext
     * @param data
     * @return
     */
    private static String saveImage(Context mContext, byte[] data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int h = (int) (MIN_WIDTH * 1.0f / options.outWidth * options.outHeight);

        //第一次缩放后最大宽度不可能达到MIN_WIDTH的两倍
        options.inJustDecodeBounds = false;
        options.inSampleSize=options.outWidth/MIN_WIDTH;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        //第二次压缩
        bitmap = Bitmap.createScaledBitmap(bitmap,MIN_WIDTH, h, true);
        File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                +System.currentTimeMillis() + ".jpeg");
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        return file.getAbsolutePath();
    }

}
