package com.klgz.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created lwh
 */
public abstract class BaseFragment extends Fragment {
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(setLayoutResourceID(), container, false);
        mContext = getActivity();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView(getView());
        setUpData();
    }

    protected abstract void setUpView(View view);

    protected <T extends View> T findView(int id) {
        return (T) getView().findViewById(id);
    }

    protected abstract int setLayoutResourceID();

    protected abstract void setUpData();

}
