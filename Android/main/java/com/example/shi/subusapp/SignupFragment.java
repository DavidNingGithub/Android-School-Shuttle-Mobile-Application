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



public class SignupFragment extends Fragment {
    //private Date mDate;
    private static final int REQUEST_DATE=0;
    private EditText result;
    public static SignupFragment newInstance(Date date){
        SignupFragment fragment=new SignupFragment();
        Bundle args = new Bundle();
        args.putSerializable(MyDialogFragment.DATE_ARGS, date);
        fragment.setArguments(args);
        return fragment;
    }

    public SignupFragment(){}
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
        final View rootview=inflater.inflate(R.layout.register,container,false);
        final EditText editText1=(EditText)rootview.findViewById(R.id.reg_fullname);
        final EditText editText2=(EditText)rootview.findViewById(R.id.reg_password);
        final EditText editText3=(EditText)rootview.findViewById(R.id.reg_password2);
        final EditText editText4=(EditText)rootview.findViewById(R.id.reg_briefD);
        final EditText editText5=(EditText)rootview.findViewById(R.id.reg_D);
        final EditText editText6=(EditText)rootview.findViewById(R.id.reg_Location);
        final EditText editText7=(EditText)rootview.findViewById(R.id.reg_icon);
        result=editText7;
        final Button done1=(Button)rootview.findViewById(R.id.btnRegister1);
        final Button done2=(Button)rootview.findViewById(R.id.btnRegister2);
        final Button done3=(Button)rootview.findViewById(R.id.btnRegister3);
        final Button done4=(Button)rootview.findViewById(R.id.btnRegister4);
        final Button done=(Button)rootview.findViewById(R.id.btnRegister);
        Button done5=(Button)rootview.findViewById(R.id.button);
        done5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Date date1=new Date(System.currentTimeMillis());
                Intent intent=new Intent(getActivity(),Activity_MasterDetail.class);
                //intent.putExtra(MyDialogFragment.DATE_ARGS,date1);
                startActivityForResult(intent, REQUEST_DATE);
            }
        });
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                 String userid=editText1.getText().toString();
                 String passwd=editText2.getText().toString();
                 String passwd2=editText3.getText().toString();
                 //String finaurl = MovieDataJson.FILE_SERVER + "Check/id/" + userid+ "/pw/" + passwd + "/Des/" + desc + "/BDes/" + bdesc + "/Loc/" + Loc + "/Icon/" + Icon;
                if(!passwd.matches(passwd2))
                    Toast.makeText(getActivity(), "The Passwords do not match", Toast.LENGTH_SHORT).show();
                //String url=MovieDataJson.FILE_SERVER+"Login/id/"+userid+"/pw/"+passwd;
                else {
                    if(userid.matches(""))
                        Toast.makeText(getActivity(), "The UseId can't be empty", Toast.LENGTH_SHORT).show();
                    else {
                        String url = MovieDataJson.FILE_SERVER + "Check/id/" + userid; //+ "/pw/" + passwd + "/Des/" + desc + "/BDes/" + bdesc + "/Loc/" + Loc + "/Icon/" + Icon;
                        //url=MovieDataJson.FILE_SERVER+"Users/";
                        Log.d("Mydebug", url);
                        //Toast.makeText(getActivity(),url, Toast.LENGTH_SHORT).show();
                        MydownloadJsonAsynTask downloadJson = new MydownloadJsonAsynTask(done1, done2);
                        downloadJson.execute(url);
                    }
                    //done.callOnClick();
                }
            }
        });
        done4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userid=editText1.getText().toString();

                //String finaurl = MovieDataJson.FILE_SERVER + "Check/id/" + userid+ "/pw/" + passwd + "/Des/" + desc + "/BDes/" + bdesc + "/Loc/" + Loc + "/Icon/" + Icon;


                        String url = MovieDataJson.FILE_SERVER + "Users/id/" + userid; //+ "/pw/" + passwd + "/Des/" + desc + "/BDes/" + bdesc + "/Loc/" + Loc + "/Icon/" + Icon;
                        //url=MovieDataJson.FILE_SERVER+"Users/";
                        Log.d("Mydebug", url);
                        //Toast.makeText(getActivity(),url, Toast.LENGTH_SHORT).show();
                MydownloadJsonAsynTask3 downloadJson= new MydownloadJsonAsynTask3(done3);
                //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                downloadJson.execute(url);

                    //done.callOnClick();
                }

        });
        done1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(getActivity(), "The UserId already exists", Toast.LENGTH_SHORT).show();
            }
        });
        done2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userid=editText1.getText().toString();
                userid=userid.replace(" ","+");
                String passwd=editText2.getText().toString();
                String passwd2=editText3.getText().toString();
                String bdesc=editText4.getText().toString();
                bdesc=bdesc.replace(" ","+");
                String desc=editText5.getText().toString();
                desc=desc.replace(" ","+");
                String Loc=editText6.getText().toString();
                Loc=Loc.replace(" ","+");
                String Icon=editText7.getText().toString();
                String finaurl = MovieDataJson.FILE_SERVER + "Signup/id/" + userid+ "/pw/" + passwd + "/Des/" + desc + "/BDes/" + bdesc + "/Loc/" + Loc + "/Icon/" + Icon;
                Log.d("done2 :",finaurl);
                if(userid.matches("")||passwd.matches("")||bdesc.matches("")||desc.matches("")||Loc.matches("")||Icon.matches(""))
                    Toast.makeText(getActivity(), "Please fill up the information", Toast.LENGTH_SHORT).show();
                else {
                    MydownloadJsonAsynTask2 downloadJson = new MydownloadJsonAsynTask2(done4);
                    //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                    downloadJson.execute(finaurl);
                }
            }
        });
        done3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                String input="Sign up successfully";//editText1.getText().toString();
                //intent.putExtra(MyDialogFragment.DATE_ARGS,mDate);
                intent.putExtra(MyDialogFragment.TEXT_ARGS,input);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
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
                threadMovieData.UserIDcheck(url);
            }
            return threadMovieData;
        }
        @Override
        protected void onPostExecute (MovieDataJson threadMovieData){
            final Button button1=buttonWeakReference.get();
            final Button button2=buttonWeakReference2.get();
            if(threadMovieData.userresult){
                button2.callOnClick();
            }
            else
            {
                button1.callOnClick();
            }
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
