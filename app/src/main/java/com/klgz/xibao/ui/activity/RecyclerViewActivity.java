package com.klgz.xibao.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.klgz.xibao.R;
import com.klgz.xibao.ui.fragment.PermissionsFragment;
import com.klgz.xibao.utils.LocationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwh on 2018/1/31.
 */

public class RecyclerViewActivity extends AppCompatActivity{
    private LRecyclerView mRecyclerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<String> datas=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

//        recyclerAndLocation();
    }

    private void recyclerAndLocation() {
        mRecyclerView= (LRecyclerView) findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(new CustomAdapter());
        mRecyclerView.setAdapter(mLRecyclerViewAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

//        for (int i = 0; i < 3; i++) {
//            datas.add("item:"+i);
//
//        }
        mLRecyclerViewAdapter.notifyDataSetChanged();

        PermissionsFragment.getInstance(this).requestPermissions(new PermissionsFragment.PermissionsCallback() {
            @Override
            public void requestSuccess() {

                LocationUtils.getInstance().register(RecyclerViewActivity.this, 3000, 0, new LocationUtils.OnLocationListener() {

                    @Override
                    public void onLocation(Location location) {
                        datas.add("onLocationChanged"+"\n"+location.toString()+"\n"+LocationUtils.getAddress(RecyclerViewActivity.this,location.getLatitude(),location.getLongitude()).toString());
                        mLRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void requestFail() {

            }
        }, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void startApp() {
        Intent intent=new Intent();
        intent.setClassName("com.megvii.facialrecognition","com.megvii.polizeilichassistant.activity.LoginActivity");
        startActivity(intent);
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.text.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView text;

        public CustomViewHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
