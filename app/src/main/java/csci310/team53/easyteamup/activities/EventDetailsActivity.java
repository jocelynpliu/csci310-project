package csci310.team53.easyteamup.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.util.TimeSlot;

public class EventDetailsActivity extends AppCompatActivity {

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;

    private Button myjoinButton;
    private Button myLeaveButton;
    private Button myConfirmButton;

    private EditText dateText;
    private EditText startTimeText;
    private EditText endTimeText;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener sTime;
    private TimePickerDialog.OnTimeSetListener eTime;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    List<TimeSlot> timeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasyTeamUp app = (EasyTeamUp) this.getApplication();


        String cameFrom = getIntent().getStringExtra("from");
        Log.d("cameFrom ", cameFrom + "!!!!");

        String eventID = getIntent().getStringExtra("eventID");
        Log.d("eventId ", eventID + "!!!!");

        setContentView(R.layout.activity_event);

        if (cameFrom.equals("hosted")) {
            setContentView(R.layout.activity_myeventdetails);
        }
        else if(cameFrom.equals("attending")){
            setContentView(R.layout.activity_attending_event);
        }


        // getting time slots
        app.getEventHandler().retrieveEvent(eventID).getAsync(task -> {
            if (task.isSuccess()) {
                Event event = task.get().next();
                timeSlots = event.getTimeSlots();

                ((EditText) findViewById(R.id.eventName)).setText(event.getName());
                ((EditText) findViewById(R.id.eventAddress)).setText(event.getLocation());
                ((EditText) findViewById(R.id.description)).setText(event.getDescription());
                ((EditText) findViewById(R.id.dateText)).setText(event.getDate());

                if (!cameFrom.equals("hosted")) {
                    if (timeSlots == null) {
                        ((EditText) findViewById(R.id.startTimeText)).setText(event.getStart());
                        ((EditText) findViewById(R.id.endTimeText)).setText(event.getEnd());
                       // ((ConstraintLayout) findViewById(R.id.votingConstraintLayout)).setVisibility(View.GONE);
                    }
                    else {
                        ((ConstraintLayout) findViewById(R.id.startEndLayout)).setVisibility(View.GONE);

                        List<String> stringTimeSlots = new ArrayList<String>();
                        for (int i = 0; i < timeSlots.size(); i++) {
                            TimeSlot curr = timeSlots.get(i);
                            stringTimeSlots.add(curr.getStart() + " to " + curr.getEnd());
                        }

                        Log.v("slots: ", stringTimeSlots.toString());

                        listView = findViewById(R.id.timeSlotListView);
                        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringTimeSlots);
                        listView.setAdapter(arrayAdapter);
                        listView.setItemsCanFocus(false);
                        listView.setOnItemClickListener((adapterView, view, i, l) -> view.setSelected(true));
                    }
                }

                if(cameFrom.equals("hosted")) {
                    ((CheckBox) findViewById(R.id.checkBox)).setChecked(event.isPrivate());
                }
                else if (cameFrom.equals("attending")) {
                    // TODO: Display each slot on activity view using recycler
                }

            } else {
                Log.v("EVENTS", "ERROR: " + task.getError().getErrorMessage());
            }
        });

        setTitle("Event");

        //button stuff---------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        myjoinButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton = (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //go to myEvents
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        // date/time pickers
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
            setTimePicker(view, hourOfDay, minute, startTimeText);
        };

        eTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, endTimeText);
        };

        Log.d("camefrom", "camefrom is " + cameFrom);
//      Log.d("eventID!! ", eventID);

        if (cameFrom.equals("home") || cameFrom.equals("map") ) {
            myjoinButton = (Button) findViewById(R.id.joinEventButton);
            myjoinButton.setOnClickListener(v -> {
                // Add this user to list of attendees for specified event
                app.getEventHandler().attendEvent(eventID).getAsync(task -> {

                    if (task.isSuccess()) {
                        Log.d("eventID!! ", eventID);


                        Intent intent = new Intent(EventDetailsActivity.this, InviteResultActivity.class);
                        intent.putExtra("isAttending", true);
                        intent.putExtra("hostID", task.get().getHost().toString());
                        intent.putExtra("eventID", task.get().getId().toString());

                        startActivity(intent);
                    }
                });

                // checked time slot
//                List<TimeSlot> updatedTimeSlots = checkedSlot();
//                app.getEventHandler().updateEvent();

            });
        }

        if (cameFrom.equals("attending") ) {
            myLeaveButton = (Button) findViewById(R.id.leaveEventButton);
            myLeaveButton.setOnClickListener(v -> {
                // Add this user to list of attendees for specified event
                app.getEventHandler().denyEvent(eventID).getAsync(task -> {

                    if (task.isSuccess()) {

                        Log.d("eventID!! ", eventID);
                        Intent intent = new Intent(EventDetailsActivity.this, InviteResultActivity.class);
                        intent.putExtra("isAttending", false);
                        intent.putExtra("hostID", task.get().getHost().toString());
                        intent.putExtra("eventID", task.get().getId().toString());
                        startActivity(intent);
                    }
                });
            });
        }

        if(cameFrom.equals("hosted")){
            myConfirmButton = (Button) findViewById(R.id.confirmButton);
            myConfirmButton.setOnClickListener(v -> {
                // Add this user to list of attendees for specified event
                EditText titleV = (EditText) findViewById(R.id.eventName);
                EditText locationV= (EditText) findViewById(R.id.eventAddress) ;
                EditText descripV = (EditText) findViewById(R.id.description);
                EditText dateV= (EditText) findViewById(R.id.dateText);
                EditText startV= (EditText) findViewById(R.id.startTimeText);
                EditText endV = (EditText) findViewById(R.id.endTimeText);
                CheckBox checkV = (CheckBox) findViewById(R.id.checkBox);

                String titleN = titleV.getText().toString();
                String locationN = locationV.getText().toString();
                String descripN = descripV.getText().toString();
                String dateN = dateV.getText().toString();
                String startN = startV.getText().toString();
                String endN = endV.getText().toString();
                boolean checkN = checkV.isChecked();

                app.getEventHandler().updateEvent(eventID, titleN, descripN, locationN,  dateN, startN, endN, checkN, null);

                Intent intent = new Intent(EventDetailsActivity.this, InviteResultActivity.class);
                        intent.putExtra("isAttending", true);
                        intent.putExtra("hostID", "NOTIFYattendees");
                        intent.putExtra("eventID", eventID);
                        startActivity(intent);


            });
        }
    }

    public void setTimePicker(View view, int hourOfDay, int minute, EditText text) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String format = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        text.setText(sdf.format(calendar.getTime()));
    }

    public void datePickerClick(View view) {
        new DatePickerDialog(EventDetailsActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void sTimePickerClick(View view) {
        new TimePickerDialog(EventDetailsActivity.this, sTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void eTimePickerClick(View view) {
        new TimePickerDialog(EventDetailsActivity.this, eTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

    }

    private List<TimeSlot> checkedSlot() {
        // getting all checked users
        TimeSlot checkedSlot;

        int checkedIndex = listView.getCheckedItemPosition();
        checkedSlot = timeSlots.get(checkedIndex);
        checkedSlot.setVotes(checkedSlot.getVotes() + 1);

        timeSlots.add(checkedIndex, checkedSlot);

        return timeSlots;
    }
}