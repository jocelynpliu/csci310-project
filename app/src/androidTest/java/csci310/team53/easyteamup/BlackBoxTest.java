package csci310.team53.easyteamup;

import static android.content.Intent.*;
import static androidx.test.InstrumentationRegistry.getTargetContext;
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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
//import android.support.test.rule.ActivityTesRule;

import androidx.test.espresso.intent.Intents;
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

import csci310.team53.easyteamup.activities.EventsActivity;
import csci310.team53.easyteamup.activities.HomeActivity;
import csci310.team53.easyteamup.activities.HostedEventsActivity;
import csci310.team53.easyteamup.activities.InboxActivity;
import csci310.team53.easyteamup.activities.LoginActivity;
import csci310.team53.easyteamup.activities.MapsActivity;

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


    // login tests-----------------------------------------------------------------------------------------------
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
        intended(hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)));



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
//        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));

    }


    // buttons from home-------------------------------------------------------------------------------------
    @Test
    public void attendingFromHome(){
        logIn();

        onView(withId(R.id.myEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), EventsActivity.class)));
    }

    @Test
    public void inboxFromHome(){
        logIn();

        onView(withId(R.id.inboxButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), InboxActivity.class)));
    }


    @Test
    public void hostedFromHome(){
        logIn();

        onView(withId(R.id.hostedEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HostedEventsActivity.class)));
    }

    @Test
    public void mapFromHome(){
        logIn();

        onView(withId(R.id.mapButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), MapsActivity.class)));
    }



    // buttons from attending events-------------------------------------------------------------------------------------
    @Test
    public void inboxFromAttending(){
        logIn();

        onView(withId(R.id.myEventsButton)).perform(click());
        onView(withId(R.id.inboxButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), InboxActivity.class)));
    }

    @Test
    public void hostedFromAttending(){
        logIn();

        onView(withId(R.id.myEventsButton)).perform(click());
        onView(withId(R.id.hostedEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HostedEventsActivity.class)));
    }


    @Test
    public void homeFromAttending(){
        logIn();

        onView(withId(R.id.myEventsButton)).perform(click());
        onView(withId(R.id.homeButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)), Intents.times(2));
    }


    // buttons from inbox-------------------------------------------------------------------------------------
    @Test
    public void homeFromInbox(){
        logIn();

        onView(withId(R.id.inboxButton)).perform(click());
        onView(withId(R.id.myEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), EventsActivity.class)));
    }

    @Test
    public void hostedFromInbox(){
        logIn();

        onView(withId(R.id.inboxButton)).perform(click());
        onView(withId(R.id.hostedEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HostedEventsActivity.class)));
    }


    @Test
    public void attendingFromInbox(){
        logIn();

        onView(withId(R.id.inboxButton)).perform(click());
        onView(withId(R.id.myEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), EventsActivity.class)));
    }


    // buttons from hosted events-------------------------------------------------------------------------------------
    @Test
    public void homeFromHosted(){
        logIn();

        onView(withId(R.id.hostedEventsButton)).perform(click());
        onView(withId(R.id.homeButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)), Intents.times(2));
    }

    @Test
    public void attendingFromHosted(){
        logIn();

        onView(withId(R.id.hostedEventsButton)).perform(click());
        onView(withId(R.id.myEventsButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), EventsActivity.class)));
    }

    @Test
    public void inboxFromHosted(){
        logIn();

        onView(withId(R.id.hostedEventsButton)).perform(click());
        onView(withId(R.id.inboxButton)).perform(click());
        try {
            Thread.sleep(2000);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), InboxActivity.class)));
    }




    public void logIn(){
        onView(withId(R.id.loginUsername)).perform(typeText("tapeters"));
        onView(withId(R.id.password)).perform(typeText("mypassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        try {
            Thread.sleep(1000);
        }
        catch(Exception e){

        }
    }



}
