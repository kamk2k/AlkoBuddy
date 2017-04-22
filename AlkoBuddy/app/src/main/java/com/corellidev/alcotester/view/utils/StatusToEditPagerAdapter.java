package com.corellidev.alcotester.view.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.corellidev.alcotester.view.EditDrinkFragment;
import com.corellidev.alcotester.view.StatusFragment;

/**
 * Created by PC on 2015-02-23.
 */
public class StatusToEditPagerAdapter extends FragmentPagerAdapter {

    public static final int STATUS_FRAGMENT_POSITION = 0;
    public static final int EDIT_DRINK_FRAGMENT_POSITION = 1;

    StatusFragment mStatusFragment = new StatusFragment();
    EditDrinkFragment mEditDrinkFragment = new EditDrinkFragment();

    public StatusToEditPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case STATUS_FRAGMENT_POSITION :
                return "Status";
            case EDIT_DRINK_FRAGMENT_POSITION:
                return "Edit";
            default:
                return "WTFFFFF!!!!";
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case STATUS_FRAGMENT_POSITION :
                return mStatusFragment;
            case EDIT_DRINK_FRAGMENT_POSITION:
                return mEditDrinkFragment;
            default:
                return mStatusFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
