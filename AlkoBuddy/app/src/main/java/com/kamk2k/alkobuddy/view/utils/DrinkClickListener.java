package com.kamk2k.alkobuddy.view.utils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.kamk2k.alkobuddy.model.DrinkItem;
import com.kamk2k.alkobuddy.model.events.DrinkEvent;
import com.kamk2k.alkobuddy.model.events.UpdateEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by PC on 2015-02-25.
 */
public class DrinkClickListener implements AdapterView.OnItemClickListener {

    private static final String TAG = DrinkClickListener.class.getSimpleName() ;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "You have clicked it!!!");
        DrinkItem clickedDrink = (DrinkItem) parent.getItemAtPosition(position);
        EventBus.getDefault().post(new DrinkEvent(clickedDrink));
        EventBus.getDefault().post(new UpdateEvent(null));
    }
}
