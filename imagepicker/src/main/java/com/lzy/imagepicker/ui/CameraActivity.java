package com.lzy.imagepicker.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.utils.AppConstant;
import com.lzy.imagepicker.utils.BitmapUtils;
import com.lzy.imagepicker.utils.CameraUtil;
import com.lzy.imagepicker.utils.SystemUtils;
import com.lzy.imagepicker.utils.WeakHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("deprecation")
public class CameraActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {
    private Camera mCamera;
    private SurfaceView surfaceView;
    private SurfaceHolder mHolder;
    private int mCameraId = 0;
    private Context context;

    //屏幕宽高
    private int screenWidth;
    private int screenHeight;
    private LinearLayout home_custom_top_relative;
    private ImageView camera_delay_time;
    private View home_camera_cover_top_view;
    private View home_camera_cover_bottom_view;
    private ImageView flash_light;
    private TextView camera_delay_time_text;
    private ImageView camera_square;
    private int index;
    //底部高度 主要是计算切换正方形时的动画高度
    private int menuPopviewHeight;
    //动画高度
    private int animHeight;
    //闪光灯模式 0:关闭 1: 开启 2: 自动
    private int light_num = 0;
    //延迟时间
    private int delay_time;
    private int delay_time_temp;
    private boolean isview = false;
    private boolean is_camera_delay;
    private ImageView camera_frontback;
    private ImageView camera_close;
    private RelativeLayout homecamera_bottom_relative;
    private ImageView img_camera;
    private ImageView imageView;
    //    Viewfinder viewfinder;
    private int picHeight;
    private int width;
    private int height;
    private Rect mBorder;
    private String outPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        context = this;
        outPath = getIntent().getStringExtra("path");
        initView();
        initData();
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setOnClickListener(this);

        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        img_camera = (ImageView) findViewById(R.id.img_camera);
        img_camera.setOnClickListener(this);

        //关闭相机界面按钮
        camera_close = (ImageView) findViewById(R.id.camera_close);
        camera_close.setOnClickListener(this);

        //top 的view
        home_custom_top_relative = (LinearLayout) findViewById(R.id.home_custom_top_relative);
        home_custom_top_relative.setAlpha(0.5f);

        //前后摄像头切换
        camera_frontback = (ImageView) findViewById(R.id.camera_frontback);
        camera_frontback.setOnClickListener(this);

        //延迟拍照时间
        camera_delay_time = (ImageView) findViewById(R.id.camera_delay_time);
        camera_delay_time.setOnClickListener(this);

        //正方形切换
        camera_square = (ImageView) findViewById(R.id.camera_square);
        camera_square.setOnClickListener(this);

        //拍照时动画
        home_camera_cover_top_view = findViewById(R.id.home_camera_cover_top_view);
        home_camera_cover_bottom_view = findViewById(R.id.home_camera_cover_bottom_view);
        home_camera_cover_top_view.setAlpha(1);
        home_camera_cover_bottom_view.setAlpha(1);

        //闪光灯
        flash_light = (ImageView) findViewById(R.id.flash_light);
        flash_light.setOnClickListener(this);

        camera_delay_time_text = (TextView) findViewById(R.id.camera_delay_time_text);

        homecamera_bottom_relative = (RelativeLayout) findViewById(R.id.homecamera_bottom_relative);
    }

    private void initData() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        menuPopviewHeight = screenHeight - screenWidth * 4 / 3;
        animHeight = (screenHeight - screenWidth - menuPopviewHeight - SystemUtils.dp2px(context, 44)) / 2;

        //这里相机取景框我这是为宽高比3:4 所以限制底部控件的高度是剩余部分
        RelativeLayout.LayoutParams bottomParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, menuPopviewHeight);
        bottomParam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        homecamera_bottom_relative.setLayoutParams(bottomParam);
    }

    private WeakHandler mHandler = new WeakHandler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case AppConstant.WHAT.SUCCESS:
                    if (delay_time > 0) {
                        camera_delay_time_text.setText("" + delay_time);
                    }

                    try {
                        if (delay_time == 0) {
                            captrue();
                            is_camera_delay = false;
                            camera_delay_time_text.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        return;
                    }

                    break;

                case AppConstant.WHAT.ERROR:
                    is_camera_delay = false;
                    break;

            }
        }
    };


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_camera) {
            if (isview) {
                if (delay_time == 0) {
                    switch (light_num) {
                        case 0:
                            //关闭
                            CameraUtil.getInstance().turnLightOff(mCamera);
                            break;
                        case 1:
                            CameraUtil.getInstance().turnLightOn(mCamera);
                            break;
                        case 2:
                            //自动
                            CameraUtil.getInstance().turnLightAuto(mCamera);
                            break;
                    }
                    captrue();
                } else {
                    camera_delay_time_text.setVisibility(View.VISIBLE);
                    camera_delay_time_text.setText(String.valueOf(delay_time));
                    is_camera_delay = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (delay_time > 0) {
                                //按秒数倒计时
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    mHandler.sendEmptyMessage(AppConstant.WHAT.ERROR);
                                    return;
                                }
                                delay_time--;
                                mHandler.sendEmptyMessage(AppConstant.WHAT.SUCCESS);
                            }
                        }
                    }).start();
                }
                isview = false;
            }

        } else if (i == R.id.camera_frontback) {
            switchCamera();


            //退出相机界面 释放资源
        } else if (i == R.id.camera_close) {
            if (is_camera_delay) {
                Toast.makeText(CameraActivity.this, "正在拍照请稍后...", Toast.LENGTH_SHORT).show();
                return;
            }
            finish();


            //闪光灯
        } else if (i == R.id.flash_light) {
            if (mCameraId == 1) {
                //前置
//                    ToastFactory.showLongToast(context, "请切换为后置摄像头开启闪光灯");
                return;
            }
            Camera.Parameters parameters = mCamera.getParameters();
            switch (light_num) {
                case 0:
                    //打开
                    light_num = 1;
                    flash_light.setImageResource(R.drawable.btn_camera_flash_on);
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//开启
                    mCamera.setParameters(parameters);
                    break;
                case 1:
                    //自动
                    light_num = 2;
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    mCamera.setParameters(parameters);
                    flash_light.setImageResource(R.drawable.btn_camera_flash_auto);
                    break;
                case 2:
                    //关闭
                    light_num = 0;
                    //关闭
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(parameters);
                    flash_light.setImageResource(R.drawable.btn_camera_flash_off);
                    break;
            }


            //延迟拍照时间
        } else if (i == R.id.camera_delay_time) {
            switch (delay_time) {
                case 0:
                    delay_time = 3;
                    delay_time_temp = delay_time;
                    camera_delay_time.setImageResource(R.drawable.btn_camera_timing_3);
                    break;

                case 3:
                    delay_time = 5;
                    delay_time_temp = delay_time;
                    camera_delay_time.setImageResource(R.drawable.btn_camera_timing_5);
                    break;

                case 5:
                    delay_time = 10;
                    delay_time_temp = delay_time;
                    camera_delay_time.setImageResource(R.drawable.btn_camera_timing_10);
                    break;

                case 10:
                    delay_time = 0;
                    delay_time_temp = delay_time;
                    camera_delay_time.setImageResource(R.drawable.btn_camera_timing_0);
                    break;

            }
        }
    }

    public void switchCamera() {
        releaseCamera();
        mCameraId = (mCameraId + 1) % mCamera.getNumberOfCameras();
        mCamera = getCamera(mCameraId);
        if (mHolder != null) {
            startPreview(mCamera, mHolder);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 获取Camera实例
     *
     * @return
     */
    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {
            e.printStackTrace();


        }
        return camera;
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        if (camera == null) {
            finish();
            return;
        }
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            //亲测的一个方法 基本覆盖所有手机 将预览矫正
            CameraUtil.getInstance().setCameraDisplayOrientation(this, mCameraId, camera);
            camera.startPreview();
            isview = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void captrue() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                isview = false;
                //将data 转换为位图 或者你也可以直接保存为文件使用 FileOutputStream
                //这里我相信大部分都有其他用处把 比如加个水印 后续再讲解
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap saveBitmap = CameraUtil.getInstance().setTakePicktrueOrientation(mCameraId, bitmap);

                saveBitmap = Bitmap.createScaledBitmap(saveBitmap, screenWidth, picHeight, true);

                if (TextUtils.isEmpty(outPath)) {
                    outPath = getExternalFilesDir(Environment.DIRECTORY_PICTURES)+
                            File.separator + System.currentTimeMillis() + ".jpeg";

                }

                BitmapUtils.saveJPGE_After(context, saveBitmap, outPath, 100);

                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }

                if (!saveBitmap.isRecycled()) {
                    saveBitmap.recycle();
                }
            }
        });
    }


    private void setupCamera(Camera camera) {
        Camera.Parameters mCameraParameters = camera.getParameters();
       final List<String> modes = mCameraParameters.getSupportedFocusModes();
        if (modes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (modes.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
            mCameraParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else {
            if (!modes.isEmpty())
            mCameraParameters.setFocusMode(modes.get(0));
        }

//        mCameraParameters.setPreviewSize(previewSize.width, previewSize.height);
//
//        mCameraParameters.setPictureSize(pictrueSize.width, pictrueSize.height);
        camera.setParameters(mCameraParameters);
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

}
