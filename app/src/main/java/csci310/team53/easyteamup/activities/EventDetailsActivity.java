package csci310.team53.easyteamup.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import csci310.team53.easyteamup.R;

public class EventDetailsActivity extends AppCompatActivity {

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;

    private EditText dateText;
    private EditText startTimeText;
    private EditText endTimeText;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener sTime;
    private TimePickerDialog.OnTimeSetListener eTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent incomingIntent = getIntent();
        String cameFrom = incomingIntent.getStringExtra("from");

        setContentView(R.layout.activity_event);

        if(cameFrom != null) {
            if (cameFrom.equals("hosted")) {
                setContentView(R.layout.activity_myeventdetails);
            }
        }


        setTitle("A single event ");

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


        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //go to myEvents
        myEventsButton= (Button) findViewById(R.id.myEventsButton);
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

    }

    private void setTimePicker(View view, int hourOfDay, int minute, EditText text) {
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
}
