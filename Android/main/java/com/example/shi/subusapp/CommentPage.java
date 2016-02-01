package com.example.shi.subusapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Date;
import java.util.HashMap;


public class CommentPage extends Fragment {
    private HashMap<String,?> movie;
    private static final String ARG_MOVIE = "movie";
    private int total=0;
    //private MenuItemCompat menuItemCompat;
    private ShareActionProvider shareActionProvider;


    public static CommentPage newInstance(HashMap<String,?>movie){
        CommentPage fragment=new CommentPage();
        Bundle args=new Bundle();
        args.putSerializable(ARG_MOVIE,movie);
        fragment.setArguments(args);
        return fragment;
    }

    public CommentPage(){

    }

    @Override
    public void onCreate(Bundle savedInstateState){
        super.onCreate(savedInstateState);
        //setHasOptionsMenu(true);
        if(getArguments()!=null){
            movie = (HashMap<String,?>) getArguments().getSerializable(ARG_MOVIE);
        }
        if(savedInstateState!=null){
            total=savedInstateState.getInt("Total");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.detail_fragment_actionprovider,menu);
        MenuItem shareItem=menu.findItem(R.id.action_share);
        shareActionProvider=(ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intentshare=new Intent(Intent.ACTION_SEND);
        intentshare.setType("text/plain");
        intentshare.putExtra(Intent.EXTRA_TEXT,(String)movie.get("name"));
        if(shareActionProvider!=null)
            shareActionProvider.setShareIntent(intentshare);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_comment_page,container,false);
        final TextView name =(TextView) rootView.findViewById(R.id.textView7);
        final TextView cast =(TextView) rootView.findViewById(R.id.textView15);
        final EditText rating =(EditText) rootView.findViewById(R.id.textView13);
        final EditText description =(EditText) rootView.findViewById(R.id.textView17);
        final RatingBar ratingbar =(RatingBar) rootView.findViewById(R.id.ratingBar);
        final ImageView image =(ImageView) rootView.findViewById(R.id.imageView9);
        String userid=(String)movie.get("userid");
        userid=userid.replace("+"," ");
        name.setText(userid);
        String location=(String)movie.get("Location");
        location=location.replace("+"," ");
        cast.setText(location);
        image.setImageResource((Integer) movie.get("Image"));
        final Button done=(Button)rootView.findViewById(R.id.button6);
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(rating.getText().toString().matches(""))
                    return;
                ratingbar.setRating(Float.parseFloat(rating.getText().toString())/2);
            }
        });
        //final Button done1=(Button)rootView.findViewById(R.id.button7);
        final Button done2=(Button)rootView.findViewById(R.id.button8);
        /*done1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(FrontPage.CurrentUser.matches(""))
                {Toast.makeText(getActivity(),"Please Login first",Toast.LENGTH_SHORT).show();
                    Date date1=new Date(System.currentTimeMillis());
                    Intent intent=new Intent(getActivity(),SecondActivity.class);
                    intent.putExtra(MyDialogFragment.DATE_ARGS,date1);
                    startActivityForResult(intent,FirstPage.REQUEST_DATE);}
                else if(MainActivity.CurrentUser.matches((String)movie.get("userid")))
                    Toast.makeText(getActivity(),"You can comment yourself",Toast.LENGTH_SHORT).show();
                else {
                    String userid1 = (String) movie.get("userid");
                    userid1 = userid1.replace("+", " ");
                    String describe = description.getText().toString();
                    describe = describe.replace(" ", "+");
                    String url = MovieDataJson.FILE_SERVER + "Comment/id/" + userid1 + "/fid/" + FrontPage.CurrentUser + "/Des/" + describe + "/Rating/" + rating.getText().toString();
                    //editText.setText(url);
                    //Toast.makeText(getActivity(),url, Toast.LENGTH_SHORT).show();
                    MydownloadJsonAsynTask downloadJson = new MydownloadJsonAsynTask(done2);
                    Log.d("Mydebug", url);
                    //String url=MovieDataJson.FILE_SERVER+"/movies/";//rating/"+query;
                    downloadJson.execute(url);
                }
            }
        });*/
       /* done2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
                String input="Comment successfully";//editText1.getText().toString();
                String input2=rating.getText().toString();
                //intent.putExtra(MyDialogFragment.DATE_ARGS,mDate);
                intent.putExtra(MyDialogFragment.TEXT_ARGS,input);
                intent.putExtra(MyDialogFragment.TEXT_ARGS1,input2);
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });*/
        return rootView;
    }
    private class MydownloadJsonAsynTask extends AsyncTask<String,Void,MovieDataJson> {
        private final WeakReference<Button> buttonWeakReference;

        public MydownloadJsonAsynTask(Button button){
            buttonWeakReference=new WeakReference<Button>(button);


        }
        @Override
        protected MovieDataJson doInBackground(String... urls){
            MovieDataJson threadMovieData=new MovieDataJson();
            for(String url:urls){
                threadMovieData.SignUp(url);
            }
            return threadMovieData;
        }
        @Override
        protected void onPostExecute (MovieDataJson threadMovieData){

            final Button button1=buttonWeakReference.get();

            button1.callOnClick();
        }
    }
}