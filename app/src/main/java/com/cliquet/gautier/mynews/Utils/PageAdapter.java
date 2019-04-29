package com.cliquet.gautier.mynews.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cliquet.gautier.mynews.controllers.Fragments.FavoriteFragment;
import com.cliquet.gautier.mynews.controllers.Fragments.MostPopularFragment;
import com.cliquet.gautier.mynews.controllers.Fragments.TopStoriesFragment;

public class PageAdapter extends FragmentPagerAdapter {

    //2 - Default Constructor
    public PageAdapter(FragmentManager mgr) {
        super(mgr);
    }

    @Override
    public int getCount() {
        return(3); //3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance();
            case 1: //Page number 2
                return MostPopularFragment.newInstance();
            case 2: //Page number 3
                return FavoriteFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: //Page number 1
                return "Top Stories";
            case 1: //Page number 2
                return "Most Popular";
            case 2: //Page number 3
                return "Favorites";
            default:
                return null;
        }
    }
}
