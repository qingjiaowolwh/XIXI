package com.klgz.xibao;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ItemFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 4;
    }

}
