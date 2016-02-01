package com.example.shi.subusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shi on 4/14/2015.
 */
public class TimePickerFragment extends DialogFragment{
    private String hour;
    private String minute;
    public static String TIME_ARGS = "TIME";
    public TimePickerFragment() {}

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dateView = getActivity().getLayoutInflater().inflate(R.layout.timepicker_dialog,null);
        TimePicker timePicker = (TimePicker) dateView.findViewById(R.id.time_picker);
        Date date = new Date(System.currentTimeMillis());
       // hour = timePicker.getCurrentHour();
       // minute = timePicker.getCurrentMinute();
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {

            @Override
            public void onTimeChanged(TimePicker view,int hourOfDay, int min) {
                if(hourOfDay > 9)
                    hour = Integer.toString(hourOfDay);
                else
                    hour = "0"+Integer.toString(hourOfDay);
                if(min > 9)
                    minute = Integer.toString(min);
                else
                    minute = "0"+Integer.toString(min);
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(dateView)
                .setTitle("Pick a time")
                .setMessage("Do as follows")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(getTargetFragment() != null) {
                            String time = hour + ":" + minute + ":00";
                            Intent intent = new Intent();
                            intent.putExtra(TIME_ARGS,time);
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);

                        }else
                            Toast.makeText(getActivity(), "Fail to return time", Toast.LENGTH_LONG).show();
                    }
                });
        return alertDialogBuilder.create();
    }
}
