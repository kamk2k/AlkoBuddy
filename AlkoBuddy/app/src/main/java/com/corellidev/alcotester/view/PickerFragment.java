package com.corellidev.alcotester.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corellidev.alcotester.R;
import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.presenter.MainActivityPresenter;
import com.corellidev.alcotester.presenter.dagger.ApplicationComponent;
import com.corellidev.alcotester.presenter.utils.AnalyticsLogger;
import com.corellidev.alcotester.view.utils.DrinksAdapter;
import com.corellidev.alcotester.view.utils.MVPFragmentView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;


public class PickerFragment extends MVPFragmentView {

    private static final String TAG = PickerFragment.class.getSimpleName();
    public static final int NUMBER_OF_GRID_COLUMS = 2;

    @Inject
    Context context;
    @Inject
    MainActivityPresenter mainActivityPresenter;
    @Inject
    AnalyticsLogger analyticsLogger;
    @BindView(R.id.drinks_list)
    RecyclerView drinksRecyclerView;
    private RecyclerView.LayoutManager drinksLayoutManager;
    DrinksAdapter drinksAdapter;

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
        initiDrinksAdapter();

        drinksRecyclerView.setHasFixedSize(true);
        drinksLayoutManager = new GridLayoutManager(context, NUMBER_OF_GRID_COLUMS);
        drinksRecyclerView.setLayoutManager(drinksLayoutManager);

        drinksRecyclerView.setAdapter(drinksAdapter);
        return root;
    }

    private void initiDrinksAdapter() {
        RealmResults<DrinkItem> results = Realm.getDefaultInstance().where(DrinkItem.class).findAll();
        drinksAdapter = new DrinksAdapter(context, mainActivityPresenter, results, analyticsLogger);
    }

}
