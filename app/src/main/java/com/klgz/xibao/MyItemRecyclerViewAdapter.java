package com.klgz.xibao;

import com.klgz.library.base.recyclerview.BaseQuickAdapter;
import com.klgz.library.base.recyclerview.BaseViewHolder;

import java.util.List;


public class MyItemRecyclerViewAdapter extends BaseQuickAdapter<DummyContent.DummyItem,BaseViewHolder> {

    public MyItemRecyclerViewAdapter( int layoutResId, List<DummyContent.DummyItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DummyContent.DummyItem item, int position) {
        helper.setText(R.id.id,""+position);
        helper.setText(R.id.content,""+item.content);
    }

}
