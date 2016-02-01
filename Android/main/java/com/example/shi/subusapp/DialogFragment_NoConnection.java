package com.example.shi.subusapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import com.example.shi.subusapp.AnimatedGifImageView;
import android.widget.ImageView;

public class DialogFragment_NoConnection extends DialogFragment {
    public DialogFragment_NoConnection(){
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_noconnection, null);
        AnimatedGifImageView img = (AnimatedGifImageView) v.findViewById(R.id.dialog_img);
        img.setAnimatedGif(R.raw.dd, AnimatedGifImageView.TYPE.FIT_CENTER);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(v)
                .setTitle("No Data Available Now")
                .setMessage("Please try again later...")
                .setPositiveButton("OK",positiveClick);

        return  alertDialogBuilder.create();
    }
}
