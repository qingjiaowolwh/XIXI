package com.klgz.xibao;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.klgz.library.base.BaseFragment;
import com.klgz.library.base.ContainerActivity;
import com.klgz.library.base.recyclerview.BaseQuickAdapter;
import com.klgz.library.base.recyclerview.listener.OnItemClickListener;
import com.klgz.library.util.RecyclerViewUtils;
import com.klgz.xibao.ui.activity.MainActivity;
import com.klgz.xibao.ui.fragment.TestFragment;


public class ItemFragment extends BaseFragment {
    LinearLayoutManager linearLayoutManager;

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_item_list;
    }

    @Override
    protected void setUpView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerViewUtils.addScrollToTopListener(recyclerView, linearLayoutManager, new RecyclerViewUtils.OnScrollToTopListener() {
            @Override
            public void scrollToTop() {
                ((MainActivity)getActivity()).mAppbar.setExpanded(true, true);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(R.layout.fragment_item,DummyContent.ITEMS));
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString(ContainerActivity.EXTRA_FRAGMENT_TITLE,position+"");
                ContainerActivity.startActivity(mContext, TestFragment.class,bundle);
            }
        });
    }

    @Override
    protected void setUpData() {

    }


}
