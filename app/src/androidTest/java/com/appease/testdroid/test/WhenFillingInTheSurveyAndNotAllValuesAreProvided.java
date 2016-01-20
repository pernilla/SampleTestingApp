package com.appease.testdroid.test;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.appease.testdroid.R;
import com.appease.testdroid.views.WelcomeActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WhenFillingInTheSurveyAndNotAllValuesAreProvided {

    @Rule
    public ActivityTestRule<WelcomeActivity> activityRule = new ActivityTestRule(WelcomeActivity.class);
    private WelcomeActivity activity;


    @Before
    public void setup() {
        activity = activityRule.getActivity();

        onView(withId(R.id.editFirstName)).perform(typeText("Donald"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editSurveyAddress)).perform(typeText("First Ave"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editSurveyPostalAdress)).perform(typeText("12056"), ViewActions.closeSoftKeyboard());

        onView(withId(R.id.buttonSend)).perform(click());
    }

    @Test
    public void ItShouldShowAnErrorScreen() {
        onView(withText("All values are required")).inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}