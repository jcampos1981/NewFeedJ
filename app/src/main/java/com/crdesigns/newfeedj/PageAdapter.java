package com.crdesigns.newfeedj;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    int tabcount;

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new ChannelsFragment();
        default:
            return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
