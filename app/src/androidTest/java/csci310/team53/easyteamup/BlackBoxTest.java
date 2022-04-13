package csci310.team53.easyteamup;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
//import android.support.test.rule.ActivityTesRule;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import csci310.team53.easyteamup.activities.LoginActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BlackBoxTest {

    @Before
    public void setup() {

    }

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void checkLogin() {

        onView(withId(R.id.loginUsername)).perform(typeText("tapeters"));
        onView(withId(R.id.password)).perform(typeText("mypassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        try {
            Thread.sleep(1000);
        }
        catch(Exception e){

        }
//        intended(hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)));
        onView(withId(R.id.homeLayout)).check(matches(isDisplayed()));


    }

    @Test
    public void checkLoginFail() {

        onView(withId(R.id.loginUsername)).perform(typeText("tapeters"));
        onView(withId(R.id.password)).perform(typeText("wrongpassword"), closeSoftKeyboard() );
        onView(withId(R.id.login_button)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }

        onView(withId(R.id.loginLayout)).check(matches(isDisplayed()));

    }
}
