package com.kamk2k.alkobuddy.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.MainActivityComponent;
import com.kamk2k.alkobuddy.presenter.dagger.MainActivityModule;
import com.kamk2k.alkobuddy.view.utils.MVPActivityView;
import com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter
        .CREATE_DRINK_FRAGMENT_POSITION;
import static com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter.STATUS_FRAGMENT_POSITION;


public class MainActivity extends MVPActivityView implements MainActivityView{

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String OPTIONS_SHARED_PREFERENCES = "OptionsSharedPreferences";
    public static final String SHARED_PREF_IS_IN_REMOVE_MODE = "isInRemoveMode";

    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @Inject
    MainActivityPresenter presenter;
    StatusToCreatePagerAdapter mFragmentPagerAdapter;
    private MainActivityComponent mMainActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        setRemoveModeSharedPrefValue(false);
        presenter.onCreate();
    }

    private void setRemoveModeSharedPrefValue(boolean b) {
        SharedPreferences.Editor edit = getSharedPreferences(OPTIONS_SHARED_PREFERENCES, 0).edit();
        edit.putBoolean(SHARED_PREF_IS_IN_REMOVE_MODE, b);
        edit.commit();
    }

    @Override
    public void injectActivity(ApplicationComponent component) {
        mMainActivityComponent = component.plus(new MainActivityModule(this));
        mMainActivityComponent.inject(this);
        mFragmentPagerAdapter = mMainActivityComponent.getStatusToCreatePagerAdapter();
        presenter.setMVPView(this);
    }

    @Override
    public boolean isStatusFragmentDisplayed() {
        if(mViewPager == null) {
            return false;
        } else {
            return mViewPager.getCurrentItem() == STATUS_FRAGMENT_POSITION;
        }
    }

    @Override
    public boolean isCreateDrinkFragmentDisplayed() {
        if(mViewPager == null) {
            return false;
        } else {
            return mViewPager.getCurrentItem() == CREATE_DRINK_FRAGMENT_POSITION;
        }
    }

    @Override
    public void switchToCreateFragment() {
        if(mViewPager != null) {
            mViewPager.setCurrentItem(CREATE_DRINK_FRAGMENT_POSITION);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_reset) {
            presenter.resetDrinkState();
        } else if(id == R.id.action_remove) {
            item.setChecked(!item.isChecked());
            setRemoveModeSharedPrefValue(item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
