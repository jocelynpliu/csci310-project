package csci310.team53.easyteamup.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.activities.dialogs.TimeSlotDialog;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.util.TimeSlot;

/**
 * Activity that allows users to enter in details to create an event.
 *
 * @author Jocelyn Liu, Thomas Peters
 */
public class CreateEventActivity extends AppCompatActivity implements TimeSlotDialog.DialogListener {

    private EasyTeamUp app;
    private Button createEventButton;
    private Button inviteButton;
    private Button addTimeSlotButton;

    private EditText dateText;
    private EditText startTimeText;
    private EditText endTimeText;
    private EditText votingTimeText;

    // date/time pickers
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener sTime;
    private TimePickerDialog.OnTimeSetListener eTime;
    private TimePickerDialog.OnTimeSetListener vTime;

    // time slot layout
    private LinearLayout timeSlotLayout;
    private List<TimeSlot> timeSlots;
    private Calendar calendar;
    private String voteTimeString;

    // users
    private List<String> users;
    private List<String> invitedUsers;
    private ArrayList<ObjectId> userIDs;

    private boolean votingAllowed;
    LocalTime voteLocalTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createevent);

        timeSlots = new ArrayList<>();
        invitedUsers = new ArrayList<String>();

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

        // toggle button for showing voting layout
        Switch toggle = (Switch) findViewById(R.id.votingSwitch);
        votingAllowed = false;
        ConstraintLayout voting = (ConstraintLayout) findViewById(R.id.votingLayout);
        ConstraintLayout startEnd = (ConstraintLayout) findViewById(R.id.startEndLayout);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                voting.setVisibility(View.VISIBLE);
                startEnd.setVisibility(View.GONE);
                votingAllowed = true;
            } else {
                voting.setVisibility(View.GONE);
                startEnd.setVisibility(View.VISIBLE);
                votingAllowed = false;
            }
        });

        // add time slots button
        addTimeSlotButton = (Button) findViewById(R.id.timeSlotButton);
        addTimeSlotButton.setOnClickListener(view -> openDialog());

        // getting the LinearLayout where all the time slots will be added to
        timeSlotLayout = (LinearLayout) findViewById(R.id.timeSlotLayout);
    }

    // Date and Time Picker code
    private void setTimePicker(View view, int hourOfDay, int minute, EditText text) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String format = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        text.setText(sdf.format(calendar.getTime()));

        // voting text needs to be in different format
        if (text == findViewById(R.id.votingTimeText)) {
            String voteFormat = "HH:mm";
            SimpleDateFormat votesdf = new SimpleDateFormat(voteFormat, Locale.US);

            // voting end time
            voteTimeString = votesdf.format(calendar.getTime());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            voteLocalTime = LocalTime.parse(voteTimeString, formatter);
        }
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

    // so MenuActivity can return the invited users
    ActivityResultLauncher<Intent> menuResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        users = (ArrayList<String>) data.getSerializableExtra("users");
                        invitedUsers = (ArrayList<String>) data.getSerializableExtra("invitedUsers");
                        userIDs = (ArrayList<ObjectId>) data.getSerializableExtra("userIDs");
                    }
                }
            });

    /**
     * Opens menu to search for users to invite
     */
    public void openMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        menuResultLauncher.launch(intent);
    }

    /**
     * Opens dialog for adding time slots
     */
    public void openDialog() {
        TimeSlotDialog dialog = new TimeSlotDialog();
        dialog.show(getSupportFragmentManager(), "Add time slot");
    }

    /**
     * Adds time slot
     */
    @Override
    public void addSlot(String start, String end) {
        // adding in the layout
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView timeSlotTextView = new TextView(this);
        timeSlotTextView.setLayoutParams(lparams);
        timeSlotTextView.setText(start + " to " + end);
        timeSlotLayout.addView(timeSlotTextView);

        // adding to the Event class
        timeSlots.add(new TimeSlot(start, end));
    }

    /**
     * Grabs data from form fields and creates a new Event object in database.
     */
    private void createEvent() {

        final EditText nameBox = (EditText) findViewById(R.id.eventName);
        final EditText locationBox = (EditText) findViewById(R.id.eventAddress);
        final EditText descriptionBox = (EditText) findViewById(R.id.description);
        final CheckBox isPrivateBox = (CheckBox) findViewById(R.id.checkBox);
        final List<ObjectId> invitees = new ArrayList<ObjectId>();
        for (String user : invitedUsers) {
            int index = users.indexOf(user);
            Log.v("ID", user + " - " + userIDs.get(index).toString());
            invitees.add(userIDs.get(index));
        }

        // Create new event
        final Event event = new Event();
        ObjectId id = new ObjectId();
        event.setId(id);
        event.setName(nameBox.getText().toString());
        event.setLocation(locationBox.getText().toString());
        event.setDescription(descriptionBox.getText().toString());
        event.setHost(new ObjectId(app.getRealm().currentUser().getId()));
        event.setPrivate(isPrivateBox.isChecked());
        event.setInvitees(invitees);
        event.setDate(dateText.getText().toString());
        event.setStart(startTimeText.getText().toString());
        event.setEnd(endTimeText.getText().toString());
        event.setTimeSlots(timeSlots);

        // Check if all event fields are filled out
        if (!event.isValid()) {
            // Provide some user feedback with an error message
            Toast.makeText(CreateEventActivity.this, "You must fill out all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Add event to database
        app.getEventHandler().createEvent(event).getAsync(task -> {
            if (task.isSuccess()) {
                app.getMessageHandler().sendInviteMessage(invitees, id);
                if (votingAllowed) {
                    //startVotingTimer();
                    app.getVotingHandler().startVote(id, voteLocalTime);
                }
                Intent intent = new Intent(CreateEventActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Log.v("Event", task.getError().getErrorMessage() + task.getError().getErrorType() + task.getError().getErrorCode());
            }
        });
    }

}