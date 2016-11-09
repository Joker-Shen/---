package com.qianfeng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> listTitle;
    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList,List<String> listTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.listTitle = listTitle;
    }

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
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
        return listTitle.get(position%listTitle.size());
    }

}
