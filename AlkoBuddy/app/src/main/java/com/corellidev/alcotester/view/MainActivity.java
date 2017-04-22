package com.corellidev.alcotester.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.corellidev.alcotester.R;
import com.corellidev.alcotester.presenter.MainActivityPresenter;
import com.corellidev.alcotester.presenter.dagger.ApplicationComponent;
import com.corellidev.alcotester.presenter.dagger.MainActivityComponent;
import com.corellidev.alcotester.presenter.dagger.MainActivityModule;
import com.corellidev.alcotester.presenter.utils.AnalyticsLogger;
import com.corellidev.alcotester.view.utils.MVPActivityView;
import com.corellidev.alcotester.view.utils.StatusToEditPagerAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.corellidev.alcotester.view.utils.StatusToEditPagerAdapter
        .EDIT_DRINK_FRAGMENT_POSITION;
import static com.corellidev.alcotester.view.utils.StatusToEditPagerAdapter.STATUS_FRAGMENT_POSITION;


public class MainActivity extends MVPActivityView implements MainActivityView{

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String OPTIONS_SHARED_PREFERENCES = "OptionsSharedPreferences";
    public static final String SHARED_PREF_IS_IN_REMOVE_MODE = "isInRemoveMode";

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @Inject
    MainActivityPresenter presenter;
    @Inject
    AnalyticsLogger analyticsLogger;
    StatusToEditPagerAdapter statusToEditPagerAdapter;
    private MainActivityComponent mainActivityComponent;

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int
                positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if(position == STATUS_FRAGMENT_POSITION) {
                analyticsLogger.logEvent(AnalyticsLogger.STATUS_SCREEN_DISPLAYED_EVENT);
            } else if(position == EDIT_DRINK_FRAGMENT_POSITION) {
                analyticsLogger.logEvent(AnalyticsLogger.EDIT_SCREEN_DISPLAYED_EVENT);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(isStatusFragmentDisplayed()) {
                presenter.clearDrinkListSelection();
            } else if(isEditDrinkFragmentDisplayed()) {
                presenter.selectDisplayedDrinkOnList();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewPager.setAdapter(statusToEditPagerAdapter);
        viewPager.addOnPageChangeListener(pageChangeListener);
        tabLayout.setupWithViewPager(viewPager);
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
        mainActivityComponent = component.plus(new MainActivityModule(this));
        mainActivityComponent.inject(this);
        statusToEditPagerAdapter = mainActivityComponent.getStatusToEditPagerAdapter();
        presenter.setMVPView(this);
    }

    @Override
    public boolean isStatusFragmentDisplayed() {
        if(viewPager == null) {
            return false;
        } else {
            return viewPager.getCurrentItem() == STATUS_FRAGMENT_POSITION;
        }
    }

    @Override
    public boolean isEditDrinkFragmentDisplayed() {
        if(viewPager == null) {
            return false;
        } else {
            return viewPager.getCurrentItem() == EDIT_DRINK_FRAGMENT_POSITION;
        }
    }

    @Override
    public void switchToEditFragment() {
        if(viewPager != null) {
            viewPager.setCurrentItem(EDIT_DRINK_FRAGMENT_POSITION);
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
            analyticsLogger.logEvent(AnalyticsLogger.SETTINGS_OPTION_CLICKED_EVENT);
            return true;
        } else if(id == R.id.action_reset) {
            presenter.resetDrinkState();
            analyticsLogger.logEvent(AnalyticsLogger.RESET_OPTION_CLICKED_EVENT);
        } else if(id == R.id.action_remove) {
            item.setChecked(!item.isChecked());
            setRemoveModeSharedPrefValue(item.isChecked());
            analyticsLogger.logEvent(AnalyticsLogger.REMOVE_OPTION_CLICKED_EVENT);
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
        presenter.updateUserDataFromPreferences(PreferenceManager.getDefaultSharedPreferences(this));
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
