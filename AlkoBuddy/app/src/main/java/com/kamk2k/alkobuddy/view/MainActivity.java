package com.kamk2k.alkobuddy.view;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.kamk2k.alkobuddy.R;
import com.kamk2k.alkobuddy.model.events.ResetDrinkState;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.ApplicationModule;
import com.kamk2k.alkobuddy.presenter.dagger.DaggerApplicationComponent;
import com.kamk2k.alkobuddy.presenter.dagger.DaggerMainActivityComponent;
import com.kamk2k.alkobuddy.presenter.dagger.MainActivityComponent;
import com.kamk2k.alkobuddy.presenter.dagger.PresentersModule;
import com.kamk2k.alkobuddy.presenter.utils.MVPActivityPresenter;
import com.kamk2k.alkobuddy.view.utils.App;
import com.kamk2k.alkobuddy.view.utils.MVPActivityView;
import com.kamk2k.alkobuddy.presenter.MainActivityPresenter;
import com.kamk2k.alkobuddy.view.utils.MVPRetainWorkerFragment;
import com.kamk2k.alkobuddy.view.utils.StatusToCreatePagerAdapter;
import com.kamk2k.alkobuddy.presenter.service.StatusUpdateService;

import javax.inject.Inject;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;


public class MainActivity extends MVPActivityView {

    public static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.pager) ViewPager mViewPager;
    @Inject StatusToCreatePagerAdapter mFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if(MVPRetainWorkerFragment.getRetainedPresenter(TAG) == null) {
//            presenter = new MainActivityPresenter(this);
//            MVPRetainWorkerFragment.registerPresenterToRetain(TAG, presenter);
//        } else {
//            presenter = (MVPActivityPresenter)MVPRetainWorkerFragment.getRetainedPresenter(TAG);
//        }
        MainActivityComponent component = DaggerMainActivityComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
//        ApplicationComponent component = DaggerApplicationComponent.builder().
//                presentersModule(new PresentersModule(App.getApplication())).build();
        component.inject(this);
//        App.getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mFragmentPagerAdapter);
        startUpdateService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopUpdateService();
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
            EventBus.getDefault().post(new ResetDrinkState());
        }

        return super.onOptionsItemSelected(item);
    }

    private void startUpdateService() {
        Intent intent = new Intent(this, StatusUpdateService.class);
        startService(intent);
    }

    private void stopUpdateService() {
        Intent intent = new Intent(this, StatusUpdateService.class);
        stopService(intent);
    }

}
