package com.corellidev.alcotester.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.corellidev.alcotester.R;

/**
 * Created by PC on 2015-08-02.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        mContext = getActivity();
        Preference pref = findPreference(mContext.getString(R.string.weight_preference_key));
        pref.setSummary(((EditTextPreference)pref).getText() + " " + mContext.getString(R.string.kg_abbreviation));
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference pref = findPreference(key);
        if(key.equals(mContext.getString(R.string.weight_preference_key)) && pref instanceof EditTextPreference) {
            pref.setSummary(((EditTextPreference)pref).getText() + " " + mContext.getString(R.string.kg_abbreviation));
        }
    }
}
