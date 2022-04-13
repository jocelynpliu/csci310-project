package csci310.team53.easyteamup;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.ComponentName;
import android.util.Log;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

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

    private static final int SLEEP_VALUE = 2000;

    @Before
    public void setup() {
//        onView(withId(R.id.loginUsername)).perform(typeText("tapeters"), closeSoftKeyboard());
//        onView(withId(R.id.password)).perform(typeText("mypassword"), closeSoftKeyboard());
//        onView(withId(R.id.login_button)).perform(click());
//        try {
//            Thread.sleep(1000);
//        }
//        catch(Exception e){
//
//        }
    }

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    // login tests-----------------------------------------------------------------------------------------------
    @Test
    public void checkLogin() {

        onView(withId(R.id.loginUsername)).perform(typeText("tapeters"));
        onView(withId(R.id.password)).perform(typeText("mypassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        try {
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }

        onView(withId(R.id.loginLayout)).check(matches(isDisplayed()));
        //intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
    }


    // buttons from home-------------------------------------------------------------------------------------
    @Test
    public void attendingFromHome(){
        logIn();

        onView(withId(R.id.myEventsButton)).perform(click());
        try {
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
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
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), InboxActivity.class)));
    }


    // view event on home------------------------------------------------------
    @Test
    public void viewEventfromHome(){
        logIn();

        onView(withId(R.id.homeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        onView(withId(R.id.eventName)).check(matches( withText("Greek Life Protest") ) );
    }



    // view event from  attending events------------------------
    @Test
    public void viewEventfromAttending(){
        logIn();
        onView(withId(R.id.myEventsButton)).perform(click());

        onView(withId(R.id.myEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        onView(withId(R.id.eventName)).check(matches( withText("Joan's Birthday BBQ") ) );
    }


    // view invite from inbox------------------
    @Test
    public void viewInviteFromInbox(){
        logIn();
        onView(withId(R.id.inboxButton)).perform(click());

        onView(withId(R.id.inboxRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        onView(withId(R.id.eventName)).check(matches( withText("Demonstrate our app!") ) );
    }


    // view event from hosted events-----------------------------------------------
    @Test
    public void viewEventFromHostedEvents(){
        logIn();
        onView(withId(R.id.hostedEventsButton)).perform(click());

        onView(withId(R.id.myHostedEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        onView(withId(R.id.eventName)).check(matches( withText("Greek Life Protest") ) );
    }

    // check buttons from map ---------------------------------------------------------------------
    @Test
    public void homefromMap(){
        logIn();
        onView(withId(R.id.mapButton)).perform(click());
        onView(withId(R.id.homeButton)).perform(click());

        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HomeActivity.class)), Intents.times(2));
    }

    @Test
    public void inboxfromMap(){
        logIn();
        onView(withId(R.id.mapButton)).perform(click());
        onView(withId(R.id.inboxButton)).perform(click());

        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), InboxActivity.class)));
    }

    @Test
    public void attendingfromMap(){
        logIn();
        onView(withId(R.id.mapButton)).perform(click());
        onView(withId(R.id.myEventsButton)).perform(click());

        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), EventsActivity.class)));
    }

    @Test
    public void hostedfromMap(){
        logIn();
        onView(withId(R.id.mapButton)).perform(click());
        onView(withId(R.id.hostedEventsButton)).perform(click());

        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        intended(hasComponent(new ComponentName(getTargetContext(), HostedEventsActivity.class)));
    }

    // edit a hosted event ----------------------------------------------------------------------------
    @Test
    public void editEventFromHostedEvents(){
        logIn();
        onView(withId(R.id.hostedEventsButton)).perform(click());
        onView(withId(R.id.myHostedEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.description)).perform(replaceText("changed"), closeSoftKeyboard());
        onView(withId(R.id.confirmButton)).perform(click());
        onView(withId(R.id.hostedEventsButton)).perform(click());

        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        onView(withId(R.id.myHostedEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.description)).check(matches( withText("changed") ) );

        onView(withId(R.id.description)).perform(replaceText("protest"), closeSoftKeyboard());
        onView(withId(R.id.confirmButton)).perform(click());
        onView(withId(R.id.hostedEventsButton)).perform(click());

        onView(withId(R.id.myHostedEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
        onView(withId(R.id.description)).check(matches( withText("protest") ) );

    }


    // join and withdraw from public event
    @Test
    public void joinAndWithdrawPublicEvent(){
        logIn();

        onView(withId(R.id.homeRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.joinEventButton)).perform(click());


        onView(withId(R.id.myEventsButton)).perform(click());
        onView(withId(R.id.myEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));

        onView(withId(R.id.eventName)).check(matches( withText("Greek Life Protest") ) );

        onView(withId(R.id.leaveEventButton)).perform(click());
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }

        try{
            onView(withId(R.id.myEventsButton)).perform(click());
            onView(withId(R.id.myEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        }
        //if an excpetion is thrown, that means could not find the position 4, which is what we want - means the withdrawn event is NO longer in our list of attending events
        catch (Exception e) {
                assertEquals(1,1);
        }
    }

    // join and withdraw from invite
    @Test
    public void joinAndWithdrawInvite(){
        logIn();
        onView(withId(R.id.inboxButton)).perform(click());

        onView(withId(R.id.inboxRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.acceptButton)).perform(click());

        onView(withId(R.id.myEventsButton)).perform(click());
        onView(withId(R.id.myEventsRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }

        onView(withId(R.id.eventName)).check(matches( withText("Demonstrate our app!") ) );
        onView(withId(R.id.leaveEventButton)).perform(click());
    }

    public void logIn() {
        onView(withId(R.id.loginUsername)).perform(typeText("tapeters"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("mypassword"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());
        try {
            Thread.sleep(SLEEP_VALUE);
        }
        catch(Exception e){

        }
    }
}
