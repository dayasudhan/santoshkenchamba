package com.kuruvatech.santoshkenchaamba.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kuruvatech.santoshkenchaamba.fragment.ScreenSlidePageFragment;

import java.util.ArrayList;
import java.util.List;


public class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        private List<String> picList = new ArrayList<>();
    Context mContext;
    float pagewidth= 0.5f;
    public ScreenSlidePagerAdapter(FragmentManager fm , Context context, int width) {

        super(fm);
        float width2 = width;
        float pre = 540;
        pagewidth = pre/width2;
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {

        return ScreenSlidePageFragment.newInstance(this.picList.get(i));
    }

    @Override
    public int getCount() {
        return picList.size();
    }
    @Override
    public float getPageWidth(int position) {
        return(pagewidth);
    }
    public void addAll(List<String> picList) {
        this.picList = picList;
    }
}
