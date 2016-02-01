package com.example.shi.subusapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class EditPage extends Fragment {
    //private Date mDate;
    private static final int REQUEST_DATE=0;
    private EditText result;
    private int total=0;
    private HashMap<String,?> movie;
    private static final String ARG_MOVIE = "movie";
    private String NewUserID;
    private String NewPasswd;
    public static EditPage newInstance(HashMap<String,?> movie){
        EditPage fragment=new EditPage();
        Bundle args=new Bundle();
        args.putSerializable(ARG_MOVIE,movie);
        fragment.setArguments(args);
        return fragment;
    }

    public EditPage(){}

    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);
        setHasOptionsMenu(true);
        if(getArguments()!=null){
            movie = (HashMap<String,?>) getArguments().getSerializable(ARG_MOVIE);
        }
        if(savedInstateState!=null){
            total=savedInstateState.getInt("Total");
        }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(data==null)return;
        Uri contactUri=data.getData();
        Log.d("Mydebug", "111111111111");
        if(resultCode!=Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_DATE){
            Log.d("Mydebug", "1111222222");
            //Date date=(Date) data.getSerializableExtra(MyDialogFragment.DATE_ARGS);
            String input=(String) data.getSerializableExtra(MyDialogFragment.TEXT_ARGS);
            Log.d("Mydebug", input);
            Toast.makeText(getActivity(),input+" Selected",Toast.LENGTH_SHORT).show();
            input=input.replace(" ","+");
            result.setText(input);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){

        final View rootview=inflater.inflate(R.layout.editlayout,container,false);
        final EditText editText1=(EditText)rootview.findViewById(R.id.reg_fullname);
        final EditText editText2=(EditText)rootview.findViewById(R.id.reg_password);
        final EditText editText4=(EditText)rootview.findViewById(R.id.reg_briefD);
        final EditText editText5=(EditText)rootview.findViewById(R.id.reg_D);
        final EditText editText6=(EditText)rootview.findViewById(R.id.reg_Location);
        final EditText editText7=(EditText)rootview.findViewById(R.id.reg_icon);
        result=editText7;
        editText1.setText((String)movie.get("userid"));
        editText2.setText((String)movie.get("passwd"));
        editText4.setText((String)movie.get("BriefDP"));
        editText5.setText((String)movie.get("Description"));
        editText6.setText((String)movie.get("Location"));
        editText7.setText((String)movie.get("Icon"));
        final Button done1=(Button)rootview.findViewById(R.id.btnRegister1);
        final Button done2=(Button)rootview.findViewById(R.id.btnRegister2);
        final Button done3=(Button)rootview.findViewById(R.id.btnRegister3);
        final Button done=(Button)rootview.findViewById(R.id.btnRegister);
        final Button done5=(Button)rootview.findViewById(R.id.button);
        done5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                //Date date1=new Date(System.currentTimeMillis());
                Intent intent=new Intent(getActivity(),Activity_MasterDetail.class);
                //intent.putExtra(MyDialogFragment.DATE_ARGS,date1);
                startActivityForResult(intent, REQUEST_DATE);

            }
        });
        done2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String url=MovieDataJson.FILE_SERVER+"Users/id/"+NewUserID;
                //editText.setText(url);
                //Toast.makeText(getActivity(),url, Toast.LENGTH_SHORT).show();
                MydownloadJsonAsynTask3 downloadJson= new MydownloadJsonAsynTask3(done3);
                downloadJson.execute(url);
            }
        });

        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userid=editText1.getText().toString();
                userid=userid.replace(" ","+");
                NewUserID=userid;
                String passwd=editText2.getText().toString();
                NewPasswd=passwd;
                String bdesc=editText4.getText().toString();
                bdesc=bdesc.replace(" ","+");
                String desc=editText5.getText().toString();
                desc=desc.replace(" ","+");
                String Loc=editText6.getText().toString();
                Loc=Loc.replace(" ","+");
                String Icon=editText7.getText().toString();
                String finaurl = MovieDataJson.FILE_SERVER + "Update/oid/"+(String)movie.get("userid")+"/id/" + userid+ "/pw/" + passwd + "/Des/" + desc + "/BDes/" + bdesc + "/Loc/" + Loc + "/Icon/" + Icon;
                Log.d("done2 :",finaurl);
                if(userid.matches("")||passwd.matches("")||bdesc.matches("")||desc.matches("")||Loc.matches("")||Icon.matches(""))
                    Toast.makeText(getActivity(), "Please fill up the information", Toast.LENGTH_SHORT).show();
                else {
                    MydownloadJsonAsynTask2 downloadJson = new MydownloadJsonAsynTask2(done2);
                    //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                    downloadJson.execute(finaurl);
                }
            }
        });
        done3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                String input="Edit successfully";//editText1.getText().toString();
                //intent.putExtra(MyDialogFragment.DATE_ARGS,mDate);
                intent.putExtra(MyDialogFragment.TEXT_ARGS,input);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
        return rootview;
    }
    private class MydownloadJsonAsynTask3 extends AsyncTask<String,Void,MovieDataJson2> {
        private final WeakReference<Button> buttonWeakReference;

        public MydownloadJsonAsynTask3(Button button){
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

            button1.callOnClick();
        }
    }
    private class MydownloadJsonAsynTask2 extends AsyncTask<String,Void,String> {
        private final WeakReference<Button> buttonWeakReference;
        //private final WeakReference<Button> buttonWeakReference2;
        public MydownloadJsonAsynTask2(Button button){
            buttonWeakReference=new WeakReference<Button>(button);

        }
        @Override
        protected String doInBackground(String... urls){
            MovieDataJson threadMovieData=new MovieDataJson();
            for(String url:urls){
                threadMovieData.SignUp(url);
            }
            return "Success";
        }
        @Override
        protected void onPostExecute (String input){
            final Button button1=buttonWeakReference.get();
            //final Button button2=buttonWeakReference2.get();
            button1.callOnClick();
        }
    }
}