package com.hiwhuUI.Activity.util;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import data.staticData;


public class TabAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();

    public TabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case staticData.TUIJIAN:
                return "推荐";
            case staticData.GONGYI:
                return "公益";
            case staticData.JIANGZUO:
                return "讲座";
            case staticData.TIYU:
                return "体育";
            case staticData.JINGSAI:
                return "竞赛";
            case staticData.WENYI:
                return "文艺";
            default:
                return "其他";
        }
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
}
