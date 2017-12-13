package com.arm07.android.darkcloud.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by rashmi on 11/4/2017.
 */

public class AlertDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context=getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Oops! sorry")
                .setMessage("there is an error.Ty again")
                .setPositiveButton("OK",null);
        AlertDialog dialog=builder.create();
        return dialog;
    }

}
