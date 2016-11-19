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

    public static final int STATUS_FRAGMENT_POSITION = 0;
    public static final int CREATE_DRINK_FRAGMENT_POSITION = 1;

    StatusFragment mStatusFragment = new StatusFragment();
    CreateDrinkFragment mCreateDrinkFragment = new CreateDrinkFragment();

    public StatusToCreatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case STATUS_FRAGMENT_POSITION :
                return "Status";
            case CREATE_DRINK_FRAGMENT_POSITION :
                return "Create";
            default:
                return "WTFFFFF!!!!";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case STATUS_FRAGMENT_POSITION :
                return mStatusFragment;
            case CREATE_DRINK_FRAGMENT_POSITION :
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
