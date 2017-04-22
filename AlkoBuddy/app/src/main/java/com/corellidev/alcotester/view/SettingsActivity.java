package com.corellidev.alcotester.view;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by PC on 2015-08-02.
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
