package csci310.team53.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import csci310.team53.easyteamup.activities.HomeActivity;
import csci310.team53.easyteamup.activities.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BlackBoxTest {


    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void checkLogin() {
        // Test goes here

//        onView(withText("Hello world!")).check(matches(isDisplayed()));
//
//        ActivityScenario<HomeActivity> scenario = activityRule.getScenario();
//
//
//        activityRule.getScenario().onActivity(activity -> {
//
//                }
//        );
        onView(withId(R.id.username)).perform(typeText("test username"));
        onView(withId(R.id.username)).check(matches(withText("test username")));

        onView(withId(R.id.password)).perform(typeText("test password"));
        onView(withId(R.id.password)).check(matches(withText("test password")));
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.csci310", appContext.getPackageName());



    }
}
