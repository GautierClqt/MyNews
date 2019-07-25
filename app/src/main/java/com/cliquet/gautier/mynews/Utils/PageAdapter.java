package com.cliquet.gautier.mynews.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cliquet.gautier.mynews.controllers.Fragments.FavoriteFragment;
import com.cliquet.gautier.mynews.controllers.Fragments.MostPopularFragment;
import com.cliquet.gautier.mynews.controllers.Fragments.TopStoriesFragment;

public class PageAdapter extends FragmentPagerAdapter {

    //2 - Default Constructor
    public PageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return(3); //3 - Number of page to show
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: //Page number 1
                return TopStoriesFragment.newInstance(0);
            case 1: //Page number 2
                return TopStoriesFragment.newInstance(1);
            case 2: //Page number 3
                return TopStoriesFragment.newInstance(2);
            default:
                return null;
//            case 1: //Page number 2
//                return MostPopularFragment.newInstance();
//            case 2: //Page number 3
//                return FavoriteFragment.newInstance();
//            default:
//                return null;
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
                return "Sports";
            default:
                return null;
        }
    }
}
