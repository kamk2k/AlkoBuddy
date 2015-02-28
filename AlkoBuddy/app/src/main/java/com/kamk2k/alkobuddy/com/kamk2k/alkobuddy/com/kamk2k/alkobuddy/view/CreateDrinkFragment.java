package com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.com.kamk2k.alkobuddy.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kamk2k.alkobuddy.R;

/**
 * Created by PC on 2015-02-23.
 */
public class CreateDrinkFragment extends Fragment {

    public CreateDrinkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_drink_fragment, container, false);
        return rootView;
    }
}
