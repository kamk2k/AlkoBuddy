package com.kamk2k.alkobuddy.view.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kamk2k.alkobuddy.view.StatusFragment;
import com.kamk2k.alkobuddy.view.CreateDrinkFragment;

import javax.inject.Inject;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusToCreatePagerAdapter extends FragmentPagerAdapter {

    StatusFragment mStatusFragment = new StatusFragment();
    CreateDrinkFragment mCreateDrinkFragment = new CreateDrinkFragment();

    public StatusToCreatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 :
                return "Status";
            case 1 :
                return "Create";
            default:
                return "WTFFFFF!!!!";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                return mStatusFragment;
            case 1 :
                return mCreateDrinkFragment;
            default:
                return mStatusFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
