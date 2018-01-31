package com.klgz.library.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.klgz.library.R;
import com.klgz.library.R2;
import com.klgz.library.util.MultiViewUtils;
import com.klgz.library.widgets.MultiStateView;
import com.klgz.library.widgets.Titlebar;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseMultiStateFragment extends BaseFragment {
    public MultiViewUtils multiViewUtils;
    @BindView(R2.id.title_bar)
    Titlebar mTitleBar;
    @BindView(R2.id.multiStateView)
    MultiStateView mMultiStateView;
    private Bundle mBundle;

//    public static BaseMultiStateFragment getInstance(){

//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(setLayoutResourceID(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        multiViewUtils = new MultiViewUtils(mContext,mMultiStateView, getContentView());
        String title=mBundle.getString(ContainerActivity.EXTRA_FRAGMENT_TITLE);
        if (title!=null)
        mTitleBar.setTitleText(title);
    }

    /**
     * 默认布局  布局中需要 MultiStateView id是 multiStateView
     *
     * @return
     */
    @Override
    protected int setLayoutResourceID() {
        return R.layout.layout_base_with_title;
    }

    protected abstract @LayoutRes int getContentView();

}
