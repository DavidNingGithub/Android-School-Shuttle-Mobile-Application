package com.example.shi.subusapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Tommy on 3/5/15.
 */
public class DateFragment extends Fragment {
    private Date mDate;
    public static DateFragment newInstance(Date date){
        DateFragment fragment=new DateFragment();
        Bundle args = new Bundle();
        args.putSerializable(MyDialogFragment.DATE_ARGS, date);
        fragment.setArguments(args);
        return fragment;
    }

    public DateFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        final View rootview=inflater.inflate(R.layout.activity_date,container,false);
        if(MainActivity.BackColor != 0){
            rootview.setBackgroundColor(MainActivity.BackColor);
        }
        if(MainActivity.grayDrawable) {
            Resources resources = getActivity().getResources();
            Drawable btnDrawable = resources.getDrawable(R.drawable.gray_gradient);
            rootview.setBackground(btnDrawable);
        }
        mDate=(Date)getArguments().getSerializable(MyDialogFragment.DATE_ARGS);
        //TextView textView=(TextView)rootview.findViewById(R.id.current);
        //textView.setText(mDate.toString());
        //final EditText editText1=(EditText)rootview.findViewById(R.id.edit2);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mDate);
        int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        //Toast.makeText(getActivity(),"Selected"+item, Toast.LENGTH_SHORT).show();
        //DatePicker datePicker=(DatePicker)rootview.findViewById(R.id.datePicker2);
//        datePicker.init(year,month,day,new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                mDate=new GregorianCalendar(year,monthOfYear,dayOfMonth).getTime();
//                getArguments().putSerializable(MyDialogFragment.DATE_ARGS,mDate);
//            }
//        });

        final Button done=(Button)rootview.findViewById(R.id.button);
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                String input="Login successfully";//editText1.getText().toString();
                intent.putExtra(MyDialogFragment.DATE_ARGS,mDate);
                intent.putExtra(MyDialogFragment.TEXT_ARGS,input);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
        final Button done3=(Button)rootview.findViewById(R.id.button6);
        final Button done2=(Button)rootview.findViewById(R.id.button5);
        Button done1=(Button)rootview.findViewById(R.id.button2);
        done1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editText= (EditText) rootview.findViewById(R.id.ID);
                EditText editText2= (EditText) rootview.findViewById(R.id.Password);
                String userid=editText.getText().toString();
                String passwd=editText2.getText().toString();
                String url=MovieDataJson.FILE_SERVER+"Login/id/"+userid+"/pw/"+passwd;
                //String url=MovieDataJson.FILE_SERVER+"Users/id/"+userid;
                //editText.setText(url);
                //Toast.makeText(getActivity(),url, Toast.LENGTH_SHORT).show();
                MydownloadJsonAsynTask downloadJson= new MydownloadJsonAsynTask(done3,done2);
                //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                downloadJson.execute(url);
                //done.callOnClick();
            }
        });
        done3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText editText= (EditText) rootview.findViewById(R.id.ID);
                EditText editText2= (EditText) rootview.findViewById(R.id.Password);
                String userid=editText.getText().toString();
                String passwd=editText2.getText().toString();
                //String url=MovieDataJson.FILE_SERVER+"Login/id/"+userid+"/pw/"+passwd;
                String url=MovieDataJson.FILE_SERVER+"Users/id/"+userid;
                //editText.setText(url);
                //Toast.makeText(getActivity(),url, Toast.LENGTH_SHORT).show();
                MydownloadJsonAsynTask2 downloadJson= new MydownloadJsonAsynTask2(done);
                //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                downloadJson.execute(url);
                //done.callOnClick();
            }
        });
        //Button done2=(Button)rootview.findViewById(R.id.button5);
        done2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Toast.makeText(getActivity(),"not Permitted", Toast.LENGTH_SHORT).show();
                //MydownloadJsonAsynTask downloadJson= new MydownloadJsonAsynTask(done);
                //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                //downloadJson.execute(url);
                //done.callOnClick();
            }
        });
        final TextView register=(TextView)rootview.findViewById(R.id.textView18);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent=new Intent(getActivity(),SignUpActivity.class);
                //intent.putExtra(MyDialogFragment.DATE_ARGS,date1);
                startActivityForResult(intent,FirstPage.REQUEST_DATE);
            }
        });
        return rootview;
    }
    private class MydownloadJsonAsynTask extends AsyncTask<String,Void,MovieDataJson> {
        private final WeakReference<Button> buttonWeakReference;
        private final WeakReference<Button> buttonWeakReference2;
        public MydownloadJsonAsynTask(Button button,Button button2){
            buttonWeakReference=new WeakReference<Button>(button);
            buttonWeakReference2=new WeakReference<Button>(button2);

        }
        @Override
        protected MovieDataJson doInBackground(String... urls){
            MovieDataJson threadMovieData=new MovieDataJson();
            for(String url:urls){
                threadMovieData.Logincheck(url);
            }
            return threadMovieData;
        }
        @Override
        protected void onPostExecute (MovieDataJson threadMovieData){
        final Button button1=buttonWeakReference.get();
        final Button button2=buttonWeakReference2.get();
        if(threadMovieData.loginresult){
            button1.callOnClick();
        }
        else
        {
            button2.callOnClick();
        }
        }
    }
    private class MydownloadJsonAsynTask2 extends AsyncTask<String,Void,MovieDataJson2> {
        private final WeakReference<Button> buttonWeakReference;

        public MydownloadJsonAsynTask2(Button button){
            buttonWeakReference=new WeakReference<Button>(button);


        }
        @Override
        protected MovieDataJson2 doInBackground(String... urls){
            MovieDataJson2 threadMovieData=new MovieDataJson2(getActivity());
            for(String url:urls){
                threadMovieData.UserInfo(url);
            }
            return threadMovieData;
        }
        @Override
        protected void onPostExecute (MovieDataJson2 threadMovieData){
            final Button button1=buttonWeakReference.get();
            /*final Button button2=buttonWeakReference2.get();
            if(threadMovieData.loginresult){
                button1.callOnClick();
            }
            else
            {
                button2.callOnClick();
            }*/
            button1.callOnClick();
        }
    }

}
