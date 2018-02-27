package com.klgz.xibao.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.klgz.xibao.R;

import java.util.List;

/**
 * Created by lwh on 2017/12/26.
 */

public class ViewPagerListActivity extends AppCompatActivity{
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_list);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter();


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("CustomActivity:   dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("CustomActivity:   onTouchEvent");
        return super.onTouchEvent(event);
    }


    private class  CustomPagerAdapter extends PagerAdapter{
        private List<View> datas;

        public CustomPagerAdapter(List<View> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


}
