package com.lzy.imagepicker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.R;
import com.lzy.imagepicker.utils.CameraManager;

@SuppressWarnings("deprecation")
public class CameraActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener {
    private Activity mContext;
    private SurfaceView surfaceView;
    private FrameLayout surfaceContentRly;

    private ImageView flashLightImg;
    private ImageView frontBackImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mContext = this;
        init();
        setListener();
    }

    private void setListener() {
        findViewById(R.id.camera_close_img).setOnClickListener(this);
        flashLightImg.setOnClickListener(this);
        frontBackImg.setOnClickListener(this);
        findViewById(R.id.camera_take_photo).setOnClickListener(this);

    }

    private void init() {
        surfaceContentRly = (FrameLayout) findViewById(R.id.camera_surface_content_rly);
        flashLightImg = (ImageView) findViewById(R.id.camera_flash_light_img);
        frontBackImg = (ImageView) findViewById(R.id.camera_front_back_img);

        surfaceView = (SurfaceView) findViewById(R.id.camera_surfaceView);
        final SurfaceHolder mHolder = surfaceView.getHolder();
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isStart = CameraManager.getInstance().start(mContext, surfaceView.getHolder(), surfaceContentRly);
        if (!isStart) {
            Toast.makeText(CameraActivity.this, "相机权限被禁止，请前往设置中心打开权限", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        CameraManager.getInstance().release();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        CameraManager.getInstance().setUp(mContext, holder,surfaceContentRly);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        CameraManager.getInstance().setUp(mContext, holder,surfaceContentRly);
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        CameraManager.getInstance().release();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.camera_close_img) {
            CameraManager.getInstance().release();
            finish();
        } else if (i == R.id.camera_flash_light_img) {
            CameraManager.getInstance().setCameraFlash();
            flashLightImg.setSelected(!CameraManager.getInstance().getFlashPosition().equals(CameraManager.FLASH_MODE_OFF));
        } else if (i == R.id.camera_front_back_img) {
            CameraManager.getInstance().setFacing(CameraActivity.this, surfaceView, surfaceContentRly);
        } else if (i == R.id.camera_take_photo) {
            CameraManager.getInstance().takePicture(CameraActivity.this);
        }
    }

}
