package com.example.bluesky.gankio.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.bluesky.gankio.View.Fragment.MyFraments;

import static com.example.bluesky.gankio.View.Fragment.MyFraments.MyFramentsgetInstance;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public final int COUNT = 5;
    private String[] titles = new String[]{"Android", "iOS", "前端", "拓展资源", "瞎推荐"};
    private Context context;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("postion", "getItem: "+position);
        return MyFramentsgetInstance(position);
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}