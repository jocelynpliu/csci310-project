package csci310.team53.easyteamup;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;


import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import csci310.team53.easyteamup.activities.HomeActivity;
import csci310.team53.easyteamup.data.Event;


@RunWith(AndroidJUnit4.class)
//@LargeTest
public class WhiteBoxTest {

    @Rule
    public ActivityScenarioRule activityRule = new ActivityScenarioRule(HomeActivity.class);

    EasyTeamUp app;

    @Before
    public void setup() {

//        app = (EasyTeamUp) activityRule.getActivity().getApplication();
    }


    @Test
    public void addEventToDatabase() {

        Event e = new Event(new ObjectId(), "Greek Life Protest", "USC Village",
                "protest", "tapeters", false, "11/02/2022", "11:00 AM",
                "12:00 PM", new ArrayList<>(), new ArrayList<>());
        ObjectId id = e.getId();

        app.getEventHandler().createEvent(e).getAsync(task -> {
            app.getEventHandler().retrieveEvent(id.toString()).getAsync(task2 -> {
                Event retrievedEvent = task2.get().next();
                assertEquals(e.getName(), retrievedEvent.getName());
                assertEquals(e.getDescription(), retrievedEvent.getDescription());
                assertEquals(e.getDate(), retrievedEvent.getDate());
            });
        });

    }
}
