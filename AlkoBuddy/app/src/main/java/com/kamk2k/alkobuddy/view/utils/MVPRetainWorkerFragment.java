package com.kamk2k.alkobuddy.view.utils;

import android.app.Fragment;
import android.os.Bundle;

import com.kamk2k.alkobuddy.presenter.utils.MVPPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 2015-05-01.
 */
public class MVPRetainWorkerFragment extends MVPFragmentView {

    private static Map<String, MVPPresenter> retainedPresenters = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    public static void registerPresenterToRetain(String tag, MVPPresenter presenter) {
        retainedPresenters.put(tag, presenter);
    }

    public static MVPPresenter getRetainedPresenter(String tag) {
        return retainedPresenters.get(tag);
    }
}
