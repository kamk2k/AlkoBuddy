package com.corellidev.alcotester.presenter;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.corellidev.alcotester.R;
import com.corellidev.alcotester.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

/**
 * Created by kksiazek on 22.07.16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class StatusFragmentPresenterImplTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testResetAndDrinkClick() throws Exception {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Reset")).perform(click());
        //// TODO: 22.07.16 locale formating needs to be considered here + date field checking
        onView(withId(R.id.promil_text_field)).check(matches(withText(equalTo("0,00"))));
        onView(withId(R.id.bottom_fragment)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.promil_text_field)).check(matches(withText(not(equalTo("0,00")))));
    }
}