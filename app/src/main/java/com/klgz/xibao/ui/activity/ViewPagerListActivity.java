package com.klgz.xibao.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.klgz.xibao.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lwh on 2017/12/26.
 */

public class ViewPagerListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_list);


        ListView listView= (ListView)findViewById(R.id.listview);
        List<Map<String, Object>> contents=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("TITLE", "Test Title"+i);
            contents.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, contents, R.layout.list_item, new String[] {"TITLE"}, new int[] { R.id.title});
        listView.setAdapter(adapter);

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



}
