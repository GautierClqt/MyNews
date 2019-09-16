package com.cliquet.gautier.mynews.Utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cliquet.gautier.mynews.controllers.Fragments.FragmentDisplayer;

public class PageAdapter extends FragmentPagerAdapter {

    //2 - Default Constructor
    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Override
    public int getCount() {
        return 3; //3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentDisplayer.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: //Page number 1
                return "Top Stories";
            case 1: //Page number 2
                return "Most Popular";
            case 2: //Page number 3
                return "Sports";
            default:
                return null;
        }
    }
}
