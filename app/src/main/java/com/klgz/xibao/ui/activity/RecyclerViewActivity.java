package com.klgz.xibao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.klgz.library.base.recyclerview.BaseQuickAdapter;
import com.klgz.xibao.R;
import com.klgz.xibao.ui.fragment.PermissionsFragment;
import com.klgz.xibao.utils.LocationUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by lwh on 2018/1/31.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    private List<String> datas = new ArrayList<>();
    public LinearLayout recycler_ll;
    private BaseQuickAdapter adapter;
    private List<LinearLayout> lls = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
//        ButterKnife.bind(this);
//        recycler_ll = (LinearLayout) findViewById(R.id.recycler_ll);

        final LinearLayout view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_recycler, null);
        lls.add(view);
        recycler_ll.addView(view);

        findViewById(R.id.bt_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout view = (LinearLayout) LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_recycler, null);
                lls.add(view);
                recycler_ll.addView(view);
            }
        });
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ddd");
                subscriber.onError(new RuntimeException("空"));
                subscriber.onCompleted();

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i("Observable:onComplete","");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("Observable:onError",e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i("Observable:onNext",s);
                onError(new RuntimeException("出错了"));
            }
        });
//        recyclerAndLocation();
    }

    private void recyclerAndLocation() {

        PermissionsFragment.getInstance(this).requestPermissions(new PermissionsFragment.PermissionsCallback() {
            @Override
            public void requestSuccess() {

                LocationUtils.getInstance().register(RecyclerViewActivity.this, 3000, 0, new LocationUtils.OnLocationListener() {

                    @Override
                    public void onLocation(Location location) {
                        datas.add("onLocationChanged" + "\n" + location.toString() + "\n" + LocationUtils.getAddress(RecyclerViewActivity.this, location.getLatitude(), location.getLongitude()).toString());
                    }
                });

            }

            @Override
            public void requestFail() {

            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void startApp() {
        Intent intent = new Intent();
        intent.setClassName("com.megvii.facialrecognition", "com.megvii.polizeilichassistant.activity.LoginActivity");
        startActivity(intent);
    }


//    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
//        @Override
//        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
//            return new CustomViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(CustomViewHolder holder, int position) {
//            holder.text.setText(datas.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return datas.size();
//        }
//    }
//
//
//    public class CustomViewHolder extends RecyclerView.ViewHolder{
//        private TextView text;
//
//        public CustomViewHolder(View itemView) {
//            super(itemView);
//            text= (TextView) itemView.findViewById(R.id.item_text);
//        }
//    }


//    Environment.getDataDirectory() = /data
//Environment.getDownloadCacheDirectory() = /cache
//Environment.getExternalStorageDirectory() = /mnt/sdcard
//Environment.getExternalStoragePublicDirectory(“test”) = /mnt/sdcard/test
//Environment.getRootDirectory() = /system
//    getPackageCodePath() = /data/app/com.my.app-1.apk
//    getPackageResourcePath() = /data/app/com.my.app-1.apk
//    getCacheDir() = /data/data/com.my.app/cache
//    getDatabasePath(“test”) = /data/data/com.my.app/databases/test
//    getDir(“test”, Context.MODE_PRIVATE) = /data/data/com.my.app/app_test
//    getExternalCacheDir() = /mnt/sdcard/Android/data/com.my.app/cache
//    getExternalFilesDir(“test”) = /mnt/sdcard/Android/data/com.my.app/files/test
//    getExternalFilesDir(null) = /mnt/sdcard/Android/data/com.my.app/files
//    getFilesDir() = /data/data/com.my.app/files
}
