package com.klgz.xibao.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.klgz.xibao.R;
import com.klgz.xibao.ui.MacUtil;
import com.klgz.xibao.ui.fragment.PermissionsFragment;
import com.megvii.videolibrary.VideoPlayerController;
import com.megvii.videolibrary.VideoPlayerView;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by lwh on 2018/1/15.
 */

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private VideoPlayerView videoPlayer;
    private String url = "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super";
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        PermissionsFragment.getInstance(this).requestPermissions(new PermissionsFragment.PermissionsCallback() {
            @Override
            public void requestSuccess() {
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/data.mp4";
                videoPlayer = (VideoPlayerView) findViewById(R.id.videoPlayer);
                videoPlayer.setPlayerType(VideoPlayerView.TYPE_IJK);
                videoPlayer.setUp(url);
                videoPlayer.setVideoPlayerController(new VideoPlayerController(VideoActivity.this));


                MacUtil.getMacAddress(VideoActivity.this);
            }

            @Override
            public void requestFail() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        videoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
//        videoPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
//        if (videoPlayer.onBackPressd()) return;
        super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        MediaMetadataRetriever retriever=new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            Bitmap bitmap = retriever.getFrameAtTime(videoPlayer.getCurrentPosition()* 1000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            String path = Environment.getExternalStorageDirectory() +File.separator + videoPlayer.getCurrentPosition() + ".jpg";
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}