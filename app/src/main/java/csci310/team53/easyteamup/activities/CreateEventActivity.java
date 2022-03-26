package csci310.team53.easyteamup.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import csci310.team53.easyteamup.R;
import android.content.Intent;
import android.util.Log;
import android.widget.CheckBox;

import org.bson.types.ObjectId;

import java.util.ArrayList;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.data.Event;

/**
 * Activity that allows users to enter in details to create an event.
 *
 * @author Jocelyn Liu, Thomas Peters
 */
public class CreateEventActivity extends AppCompatActivity {

    private EasyTeamUp app;
    private Button createEventButton;
    private Button inviteButton;

    private EditText dateText;
    private EditText startTimeText;
    private EditText endTimeText;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener sTime;
    private TimePickerDialog.OnTimeSetListener eTime;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        inviteButton = (Button) findViewById(R.id.inviteButton);
        inviteButton.setOnClickListener(v ->  openMenu());

        this.app = (EasyTeamUp) this.getApplication();
        createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(v -> createEvent());

        // Creates the Date/Time Pickers and update the EditText with the user's inputted values
        dateText = findViewById(R.id.dateText);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);
        calendar = Calendar.getInstance();

        date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String format = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            dateText.setText(sdf.format(calendar.getTime()));
        };

        sTime = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            String format = "hh:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            startTimeText.setText(sdf.format(calendar.getTime()));

        };

        eTime = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            String format = "hh:mm a";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            endTimeText.setText(sdf.format(calendar.getTime()));
        };
    }

    public void datePickerClick(View view) {
        new DatePickerDialog(CreateEventActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void sTimePickerClick(View view) {
        new TimePickerDialog(CreateEventActivity.this, sTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void eTimePickerClick(View view) {
        new TimePickerDialog(CreateEventActivity.this, eTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
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
            event.setInvitees(new ArrayList<ObjectId>());
            event.setDate(dateText.getText().toString());
            event.setStart(startTimeText.getText().toString());
            event.setEnd(endTimeText.getText().toString());

            // Check if all event fields are filled out
            if (!event.isValid()) {
                // TODO: Provide some user feedback with an error message
                // For example: "You must fill out all form fields to create an event!"
                return;
            }

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

}