package com.example.shi.subusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by Tommy on 3/5/15.
 */
public class MyDialogFragment extends DialogFragment {
    Date mDate;
    public static final String DATE_ARGS = "index";
    public static final String TEXT_ARGS = "text";
    public static final String TEXT_ARGS1 = "text1";
    public MyDialogFragment(){

    }

    public static MyDialogFragment newInstance(Date date){
        MyDialogFragment fragment=new MyDialogFragment();
        Bundle args=new Bundle();
        args.putSerializable(DATE_ARGS,date);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        mDate=(Date)getArguments().getSerializable(DATE_ARGS);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        View v=getActivity().getLayoutInflater().inflate(R.layout.dialog_date,null);
        DatePicker datePicker=(DatePicker)v.findViewById(R.id.datePicker);
        final EditText editText=(EditText)v.findViewById(R.id.edittext);
        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener(){
            public void onDateChanged(DatePicker view,int year,int month, int day){
                mDate=new GregorianCalendar(year,month,day).getTime();
                getArguments().putSerializable(DATE_ARGS,mDate);
            }
        });




        DialogInterface.OnClickListener positiveClick= new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getTargetFragment()!=null){
                    Intent i = new Intent();
                    String input=editText.getText().toString();
                    i.putExtra(DATE_ARGS,mDate);
                    i.putExtra(TEXT_ARGS,input);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                }
                else{
                    Toast.makeText(getActivity().getBaseContext(),"OK selected",Toast.LENGTH_LONG).show();
                }
            }
        };

        DialogInterface.OnClickListener negtiveClick=new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getBaseContext(),"OK selected",Toast.LENGTH_LONG).show();
            }
        };

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setTitle("Date Picker and Text")
                .setMessage("Please select a date and input several words!")
                .setPositiveButton("Ok",positiveClick )
                .setNegativeButton("Delete",negtiveClick);
        return alertDialogBuilder.create();






    }
}
