package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.controller;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.model.DrinkItem;

/**
 * Created by PC on 2015-02-25.
 */
public class DrinkClickListener implements AdapterView.OnItemClickListener {

    private static final String TAG = DrinkClickListener.class.getSimpleName() ;

    private PerMileCalculator mPermPerMileCalculator;
    private StatusDisplayer mStatusDisplayer;

    public DrinkClickListener(PerMileCalculator mPermPerMileCalculator, StatusDisplayer mStatusDisplayer) {
        this.mPermPerMileCalculator = mPermPerMileCalculator;
        this.mStatusDisplayer = mStatusDisplayer;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "You have clicked it!!!");
        DrinkItem clickedDrink = (DrinkItem) parent.getItemAtPosition(position);
        mPermPerMileCalculator.drink(clickedDrink);
        mStatusDisplayer.update();
    }
}
