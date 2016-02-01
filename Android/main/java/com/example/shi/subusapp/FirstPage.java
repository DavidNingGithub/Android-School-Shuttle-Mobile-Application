package com.example.shi.subusapp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class FirstPage extends Fragment {

    public FirstPage() {
    }
    private OnItemSelectedListener mListener;
    public static final int REQUEST_DATE=0;
    private static final int PICK_CONTACT_REQUEST=1;
    private TextView textView_result;
    public interface OnItemSelectedListener{
        public void onItemSelected();
        public void onItemSelected1();
        public void onItemSelected2();
        public void onItemSelected3();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mListener=(OnItemSelectedListener)activity;

        }catch (ClassCastException e){
        throw new ClassCastException(activity.toString()
            +"must implement OnItemSelectedListener");

            }
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        if(data==null)
        {
            return;
        }
        Uri contactUri = data.getData();
        Log.d("MydebugMessage:","fdsafsdafsadfsdafsadfasdf");
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Log.d("MydebugMessage:","f111111111fasdf");
        if (requestCode == REQUEST_DATE) {
            Log.d("MydebugMessage:","f222222222");
            Date date = (Date) data.getSerializableExtra(MyDialogFragment.DATE_ARGS);
            String input = (String) data.getSerializableExtra(MyDialogFragment.TEXT_ARGS);
            //textView_result.setText(input);
            Toast.makeText(getActivity(), input, Toast.LENGTH_SHORT).show();
        }

        if(requestCode==PICK_CONTACT_REQUEST){
            String[] protection={
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor=getActivity().getContentResolver()
                    .query(contactUri,protection,null,null,null);
            cursor.moveToFirst();
            int column=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number=cursor.getString(column);
            column=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String name=cursor.getString(column);

            textView_result.setText("Name: "+ name+"\nTelephone: "+ number);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);
        Button task1 = (Button) rootView.findViewById(R.id.button);
        textView_result=(TextView)rootView.findViewById(R.id.DateInf);
        task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MyRecyclerView.newInstance(0))
                        .addToBackStack(null)
                        .commit();
//                mListener.onItemSelected();
                /*Date date=new Date(System.currentTimeMillis());
                MyDialogFragment dialog=MyDialogFragment.newInstance(date);
                dialog.setTargetFragment(FirstPage.this,REQUEST_DATE);
                dialog.show(getFragmentManager(),"DatePicker Dialog:Get Result");*/
            }
        });

        Button task12 = (Button) rootView.findViewById(R.id.button2);
        task12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AboutMeFragment())
                        .addToBackStack(null)
                        .commit();*/
               // mListener.onItemSelected1();
                //Date date1=new Date(System.currentTimeMillis());
                Intent intent=new Intent(getActivity(),SignUpActivity.class);
                //intent.putExtra(MyDialogFragment.DATE_ARGS,date1);
                startActivityForResult(intent,REQUEST_DATE);
            }
        });
//        Button task13 = (Button) rootView.findViewById(R.id.button3);
//        task13.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.container,new AboutMeFragment())
//                        .addToBackStack(null)
//                        .commit();*/
//                mListener.onItemSelected2();
//
//            }
//        });
        Button task14 = (Button) rootView.findViewById(R.id.button4);
        task14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AboutMeFragment())
                        .addToBackStack(null)
                        .commit();*/
                //mListener.onItemSelected3();
                Date date1=new Date(System.currentTimeMillis());
                Intent intent=new Intent(getActivity(),SecondActivity.class);
                intent.putExtra(MyDialogFragment.DATE_ARGS,date1);
                startActivityForResult(intent,REQUEST_DATE);
            }
        });
        Button task15 = (Button) rootView.findViewById(R.id.button5);
        task15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AboutMeFragment())
                        .addToBackStack(null)
                        .commit();*/
                //mListener.onItemSelected3();
                if(FrontPage.CurrentUser.matches(""))
                    Toast.makeText(getActivity(),"Please Login first",Toast.LENGTH_SHORT).show();
                else {
                    System.out.println((Integer) FrontPage.CurrentUserInf.get("Image"));
                    Log.d("Mydebugmessage1234", (String) FrontPage.CurrentUserInf.get("passwd"));
                    FrontPage.CurrentUserInf.clear();
                    FrontPage.CurrentPermission = false;
                    FrontPage.CurrentUser = "";
                }
                /*System.out.println((Integer)FrontPage.CurrentUserInf.get("Image"));
                Log.d("Mydebugmessage1234",(String)FrontPage.CurrentUserInf.get("passwd"));
                //System.out.println((Integer)FrontPage.CurrentUserInf.get("Image"));
                Log.d("Mydebugmessage1234",(String)FrontPage.CurrentUser);*/
            }
        });
        Button task16 = (Button) rootView.findViewById(R.id.button6);
        task16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,new AboutMeFragment())
                        .addToBackStack(null)
                        .commit();*/
                //mListener.onItemSelected3();
                if(FrontPage.CurrentUser.matches(""))
                    Toast.makeText(getActivity(),"Please Login first",Toast.LENGTH_SHORT).show();
                else {
                    Intent intent=new Intent(getActivity(),ViewPageActivity.class);
                    intent.putExtra(FrontPage.DATE_ARGS,FrontPage.CurrentUserInf);
                    startActivity(intent);
                }
                /*System.out.println((Integer)FrontPage.CurrentUserInf.get("Image"));
                Log.d("Mydebugmessage1234",(String)FrontPage.CurrentUserInf.get("passwd"));
                //System.out.println((Integer)FrontPage.CurrentUserInf.get("Image"));
                Log.d("Mydebugmessage1234",(String)FrontPage.CurrentUser);*/
            }
        });
        return rootView;
    }
}