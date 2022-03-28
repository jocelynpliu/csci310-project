package csci310.team53.easyteamup.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import csci310.team53.easyteamup.R;

public class TimeSlotDialog extends AppCompatDialogFragment {

    private EditText startTimeText;
    private EditText endTimeText;

    private Button startTimeButton;
    private Button endTimeButton;

    // dialog time pickers
    private TimePickerDialog.OnTimeSetListener d_sTime;
    private TimePickerDialog.OnTimeSetListener d_eTime;

    private DialogListener listener;

    private Calendar calendar;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view);
        builder.setTitle("Add Time Slot");
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

        });

        // ADD button: should add time slot
        builder.setPositiveButton("Add", (dialogInterface, i) -> {
            String start = startTimeText.getText().toString();
            String end = endTimeText.getText().toString();
            listener.addSlot(start, end);
        });

        calendar = Calendar.getInstance();

        startTimeText = view.findViewById(R.id.dialogStartTimeText);
        endTimeText = view.findViewById(R.id.dialogEndTimeText);

        startTimeButton = view.findViewById(R.id.dialogSTime);
        endTimeButton = view.findViewById(R.id.dialogETime);

        d_sTime= (v, hourOfDay, minute) -> {
            setTimePicker(v, hourOfDay, minute, startTimeText);
        };

        d_eTime= (v, hourOfDay, minute) -> {
            setTimePicker(v, hourOfDay, minute, endTimeText);
        };

        startTimeButton.setOnClickListener(view1 ->
                new TimePickerDialog(getActivity(), d_sTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show());

        endTimeButton.setOnClickListener(view12 ->
                new TimePickerDialog(getActivity(), d_eTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show());

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialog listener");
        }

    }

    public void setTimePicker(View view, int hourOfDay, int minute, EditText text) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String format = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        text.setText(sdf.format(calendar.getTime()));
    }

    public interface DialogListener {
        void addSlot(String start, String end);
    }
}
