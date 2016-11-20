package com.kamk2k.alkobuddy.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.view.utils.DrinksAdapter;
import com.kamk2k.alkobuddy.view.utils.MVPFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Lazy;


public class PickerFragment extends MVPFragmentView {

    private static final String TAG = PickerFragment.class.getSimpleName();
    public static final int NUMBER_OF_GRID_COLUMS = 2;

    @Inject
    Context mContext;
    @BindView(R.id.drinks_list)
    RecyclerView mDrinksRecyclerView;
    private RecyclerView.LayoutManager mDrinksLayoutManager;
    @Inject
    Lazy<DrinksAdapter> drinksAdapter;

    public PickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void injectFragment(ApplicationComponent component) {
        component.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.picker_fragment, container, false);
        ButterKnife.bind(this, root);


        mDrinksRecyclerView.setHasFixedSize(true);
        mDrinksLayoutManager = new GridLayoutManager(mContext, NUMBER_OF_GRID_COLUMS);
        mDrinksRecyclerView.setLayoutManager(mDrinksLayoutManager);

        mDrinksRecyclerView.setAdapter(drinksAdapter.get());
        return root;
    }

}
