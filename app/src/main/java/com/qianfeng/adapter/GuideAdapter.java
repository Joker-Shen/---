package com.qianfeng.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/10/11.
 */

public class GuideAdapter extends PagerAdapter {

    private ImageView[] pic;

    public GuideAdapter(ImageView[] pic) {
        this.pic = pic;
    }

    @Override
    public int getCount() {
        return pic.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pic[position]);
        //super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pic[position]);
        return pic[position];
    }
}
