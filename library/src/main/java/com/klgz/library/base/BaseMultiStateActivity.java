package com.klgz.library.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.klgz.library.R;
import com.klgz.library.R2;
import com.klgz.library.util.MultiViewUtils;
import com.klgz.library.widgets.MultiStateView;
import com.klgz.library.widgets.Titlebar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseMultiStateActivity extends BaseActivity {
    @BindView(R2.id.title_bar)
    Titlebar mTitleBar;
    @BindView(R2.id.multiStateView)
    MultiStateView mMultiStateView;
    public MultiViewUtils viewUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(setLayoutResourceID());
        ButterKnife.bind(this);
        viewUtils = new MultiViewUtils(mContext, mMultiStateView, getContentView());
        setUpView();
        setUpData();
    }

    /**
     * 默认布局  布局中需要 MultiStateView id是 multiStateView
     *
     * @return
     */
    protected int setLayoutResourceID() {
        return R.layout.layout_base_with_title;
    }

    protected abstract int getContentView();

    protected abstract void init();

    protected abstract void setUpView();

    protected abstract void setUpData();

}
