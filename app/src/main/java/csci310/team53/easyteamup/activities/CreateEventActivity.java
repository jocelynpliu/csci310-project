package csci310.team53.easyteamup.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.layout.DatePickerFragment;
import csci310.team53.easyteamup.layout.TimePickerFragment;

/**
 * Activity that allows users to enter in details to create an event.
 *
 * @author Jocelyn Liu, Thomas Peters
 */
public class CreateEventActivity extends AppCompatActivity {

    private EasyTeamUp app;
    private Button createEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        this.app = (EasyTeamUp) this.getApplication();
        createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(v -> createEvent());
    }

    /**
     * Grabs data from form fields and creates a new Event object in database.
     */
    private void createEvent() {
        final EditText name = (EditText) findViewById(R.id.eventName);
        final EditText location = (EditText) findViewById(R.id.eventLocation);
        final EditText description = (EditText) findViewById(R.id.description);
        final CheckBox isPrivate = (CheckBox) findViewById(R.id.checkBox);

        createEventButton.setOnClickListener(v -> {
            // Create new event
            final Event event = new Event();
            event.setId(new ObjectId());
            event.setName(name.getText().toString());
            event.setLocation(location.getText().toString());
            event.setDescription(description.getText().toString());
            event.setHost(app.getRealm().currentUser().getId());
            event.setPrivate(isPrivate.isChecked());
            event.setInvitees(new ArrayList<String>());
            // TODO: Set event date
            // TODO: Set event start time
            // TODO Set event end time

            // Add event to database
            app.getEventHandler().createEvent(event).getAsync(task -> {
                if (task.isSuccess()) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Log.v("Event", task.getError().getErrorMessage());
                }
            });
        });
    }

    /**
     * Displays a TimePicker dialog screen when a button is pressed.
     * @param v the current view
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        Log.v("dialog: ", String.valueOf(newFragment.getDialog()));
    }

    /**
     * Displays a DatePicker dialog screen when a button is pressed.
     * @param v the current view
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}