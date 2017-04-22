package com.corellidev.alcotester.presenter;

import com.corellidev.alcotester.model.DrinkItem;
import com.corellidev.alcotester.view.EditDrinkView;

import io.realm.Realm;

/**
 * Created by kksiazek on 14.11.16.
 */

public class EditDrinkPresenterImpl implements EditDrinkPresenter {

    private DrinkItem selectedDrink;

    private EditDrinkView editDrinkView;

    public EditDrinkPresenterImpl() {
    }

    @Override
    public void setMVPView(EditDrinkView mvpView) {
        this.editDrinkView = mvpView;
    }

    @Override
    public void changeSelectedDrink(DrinkItem drinkItem) {
        if(drinkItem == null) {
            selectedDrink = null;
            editDrinkView.showEmptyView();
        } else {
            selectedDrink = drinkItem;
            editDrinkView.showDrink(drinkItem);
        }
    }

    @Override
    public void selectNewDrink(DrinkItem drinkItem) {
        Realm realm = Realm.getDefaultInstance();
        if(drinkItem != null) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(drinkItem);
            realm.commitTransaction();
        }
        changeSelectedDrink(realm.where(DrinkItem.class).equalTo(DrinkItem.ID_FIELD_NAME, drinkItem.getId()).findFirst());
        realm.close();
    }

    @Override
    public DrinkItem getCurrentDrinkItem() {
        return selectedDrink;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }
}
