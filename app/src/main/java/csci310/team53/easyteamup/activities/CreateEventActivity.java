package csci310.team53.easyteamup.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.TimeSlotDialog;
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
    private Button addTimeSlotButton;

    private EditText dateText;
    private EditText startTimeText;
    private EditText endTimeText;
    private EditText votingTimeText;
    private EditText dialogSTimeText;
    private EditText dialogETimeText;

    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener sTime;
    private TimePickerDialog.OnTimeSetListener eTime;
    private TimePickerDialog.OnTimeSetListener vTime;

    // dialog time pickers
    private TimePickerDialog.OnTimeSetListener d_sTime;
    private TimePickerDialog.OnTimeSetListener d_eTime;

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
        votingTimeText = findViewById(R.id.votingTimeText);
        dialogSTimeText = findViewById(R.id.dialogStartTimeText);
        dialogETimeText = findViewById(R.id.dialogEndTimeText);
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
            setTimePicker(view, hourOfDay, minute, startTimeText);
        };

        eTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, endTimeText);
        };

        vTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, votingTimeText);
        };

        d_sTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, dialogSTimeText);
        };

        d_eTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, dialogETimeText);
        };

        // toggle button for showing voting layout
        Switch toggle = (Switch) findViewById(R.id.votingSwitch);
        ConstraintLayout voting = (ConstraintLayout) findViewById(R.id.votingLayout);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    voting.setVisibility(View.VISIBLE);
                } else {
                    voting.setVisibility(View.GONE);
                }
            }
        });

        // add time slots button
        addTimeSlotButton = (Button) findViewById(R.id.timeSlotButton);
        addTimeSlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

    }

    public void setTimePicker(View view, int hourOfDay, int minute, EditText text) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String format = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        text.setText(sdf.format(calendar.getTime()));
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

    public void vTimePickerClick(View view) {
        new TimePickerDialog(CreateEventActivity.this, vTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void dialogSTimePickerClick(View view) {
        new TimePickerDialog(CreateEventActivity.this, d_sTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void dialogETimePickerClick(View view) {
        new TimePickerDialog(CreateEventActivity.this, d_eTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    /**
     * Opens menu to search for users to invite
     */
    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    /**
     * Opens dialog for adding time slots
     */

    public void openDialog() {
        TimeSlotDialog dialog = new TimeSlotDialog();
        dialog.show(getSupportFragmentManager(), "Add time slot");
    }

    /**
     * Grabs data from form fields and creates a new Event object in database.
     */
    private void createEvent() {
        final EditText name = (EditText) findViewById(R.id.eventName);
        final EditText location = (EditText) findViewById(R.id.eventAddress);
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