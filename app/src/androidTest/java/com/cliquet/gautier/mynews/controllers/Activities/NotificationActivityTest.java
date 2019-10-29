package com.cliquet.gautier.mynews.controllers.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.InstrumentationRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNot.not;

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

        Intent mIntent = new Intent(targetContext, SearchQueriesSelectionActivity.class);
        mIntent.putExtra("actitivy_called", 1);
    }

    private void openNotificationActivity() {
        onView(allOf(withContentDescription(R.string.menu_description))).perform(click());

        onView(allOf(withId(R.id.title), withText("Notifications"))).perform(click());
        //onView(allOf(withId(R.id.menu_notifications_menu))).perform(click());
    }

    private void clickOnCheckbox(int checkboxId) {
        onView(allOf(withId(checkboxId))).perform(click());
    }

    private void clickOnSwitch(int switchId) {
        onView(allOf(withId(switchId))).perform(click());
    }

    @Test
    public void notificationActivityTest() {
        int id;

        openNotificationActivity();

        id = R.id.activity_search_articles_arts_checkbox;
        clickOnCheckbox(id);

        id = R.id.activity_search_articles_sports_checkbox;
        clickOnCheckbox(id);

        id = R.id.activity_search_articles_switch;
        clickOnSwitch(id);

        pressBack();
    }

    //
    @Test
    public void testCheckboxNotCheckedIfSwitchIsFalse() throws Exception {
        int id = R.id.activity_search_articles_arts_checkbox;

        openNotificationActivity();
        clickOnCheckbox(id);

        pressBack();
        openNotificationActivity();

        onView(withId(id)).check(matches(isNotChecked()));
    }

    //once the switch is on true position, all other views become unable
    @Test
    public void testViewsNotEnabledWhenSwitchIsTrue() throws Exception {
        openNotificationActivity();
        clickOnSwitch(R.id.activity_search_articles_switch);

        onView(withId(R.id.activity_search_articles_switch)).check(matches(isChecked()));
        onView(withId(R.id.activity_search_articles_arts_checkbox)).check(matches(not(isEnabled())));
        onView(withId(R.id.activity_search_articles_terms_edittext)).check(matches(not(isEnabled())));
    }

    @Test
    public void testViewsStateAreSavedWhenSwitchIsTrue() {
        openNotificationActivity();

        clickOnCheckbox(R.id.activity_search_articles_business_checkbox);
        clickOnCheckbox(R.id.activity_search_articles_travel_checkbox);
        clickOnSwitch(R.id.activity_search_articles_switch);

        //close and reopen the activity to check if clicked views are still checked
        pressBack();
        openNotificationActivity();

        onView(withId(R.id.activity_search_articles_switch)).check(matches(isChecked()));
        onView(withId(R.id.activity_search_articles_business_checkbox)).check(matches(isChecked()));
        onView(withId(R.id.activity_search_articles_travel_checkbox)).check(matches(isChecked()));
    }

    //open notification activity and check displayed views
    @Test
    public void testCorrectViewsAreDisplayedWhenNotificationsIsCalled() {
        openNotificationActivity();

        //those views must be displayed if user ask for notification
        onView(withId(R.id.activity_search_articles_switch)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_search_articles_switch_textview)).check(matches(isDisplayed()));

        //those views must not be displayed if user ask for notifications
        onView(withId(R.id.activity_search_articles_begindate_edittext)).check(matches(not(isDisplayed())));
        onView(withId(R.id.activity_search_articles_enddate_edittext)).check(matches(not(isDisplayed())));
        onView(withId(R.id.activity_search_articles_search_button)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testCorrespondanceBetweenNotificationSelectionAndCreatedHasmap() {
        Utils utils = new Utils();
        HashMap<String, String> madeUpHashMap = new HashMap<>();

        openNotificationActivity();
        clickOnCheckbox(R.id.activity_search_articles_business_checkbox);
        clickOnCheckbox(R.id.activity_search_articles_travel_checkbox);
        clickOnSwitch(R.id.activity_search_articles_switch);

        madeUpHashMap.put("end_date", "20191029");
        madeUpHashMap.put("begin_date", "20191028");
        madeUpHashMap.put("fq", "news_desk:(\"Business\" \"Travel\")");
        madeUpHashMap.put("page", "0");

        assertEquals(madeUpHashMap, utils.getHashMap());
    }
}
