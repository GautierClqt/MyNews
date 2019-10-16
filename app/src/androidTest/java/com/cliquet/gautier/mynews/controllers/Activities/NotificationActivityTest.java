package com.cliquet.gautier.mynews.controllers.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.cliquet.gautier.mynews.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NotificationActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
                @Override
                protected void beforeActivityLaunched() {
                    SharedPreferences clearPreferences = InstrumentationRegistry.getTargetContext().getSharedPreferences("MyNewsPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = clearPreferences.edit();
                    editor.clear();
                    editor.commit();
                    super.beforeActivityLaunched();
                }
            };

    @Before
    public void setUp() {
        Context targetContext = getInstrumentation().getTargetContext();

        Intent mIntent = new Intent(targetContext, SearchQueriesSelection.class);
        mIntent.putExtra("actitivy_called", 1);
    }

    private void openNotificationActivity() {
        ViewInteraction overflowMenuButton = onView(
                //allOf(withContentDescription("Options supplémentaires"),
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toolbar),
                                        2),
                                1),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Notifications"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());
    }

    private void checkCheckbox(int checkboxId, int parentPos, int layoutPos) {
        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(checkboxId),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_search_articles_checkboxeslayout_LinearLayout),
                                        parentPos),
                                layoutPos),
                        isDisplayed()));
        appCompatCheckBox.perform(click());
    }

    @Test
    public void notificationActivityTest() {
        int id;

        SharedPreferences testPreferences;
        testPreferences = InstrumentationRegistry.getTargetContext().getSharedPreferences("MyNewsPreferences", MODE_PRIVATE);

        openNotificationActivity();

        id = R.id.activity_search_articles_arts_checkbox;
        checkCheckbox(id,0, 0);

        id = R.id.activity_search_articles_sports_checkbox;
        checkCheckbox(id,1, 1);

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.activity_search_articles_switch),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.appcompat.widget.LinearLayoutCompat")),
                                        4),
                                1),
                        isDisplayed()));
        switch_.perform(click());

        pressBack();

        List<String> list = new ArrayList<>();
        list.add("arts");
        list.add("sports");

        testPreferences.getString("search_terms", "");
        testPreferences.getString("checkboxes_state", "");
        //HashMap<String, String> hashMap =

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
