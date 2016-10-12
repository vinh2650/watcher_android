package com.example.dell.myapplication.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Work on 10/12/2016.
 */

public class NDialog {

    ///////////////////////////Progress Dialog////////////////////////////////////
    public static ProgressDialog setProgressDialog(@NonNull Context uiContext) {
        return setProgressDialog(uiContext, null);
    }

    public static ProgressDialog setProgressDialog(@NonNull Context uiContext
            , @Nullable CharSequence title) {
        return setProgressDialog(uiContext, title, null);
    }

    public static ProgressDialog setProgressDialog(@NonNull Context uiContext
            , @Nullable CharSequence title
            , @Nullable CharSequence message) {
        ProgressDialog progressDialog = new ProgressDialog(uiContext);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    ///////////////////////////END Progress Dialog////////////////////////////////////
    ///////////////////////////Alert Dialog////////////////////////////////////
    public static AlertDialog setAlertDialog(@NonNull Context uiContext) {
        return setAlertDialog(uiContext, null);
    }

    public static AlertDialog setAlertDialog(@NonNull Context uiContext
            , @Nullable CharSequence title) {
        return setAlertDialog(uiContext, title, null);
    }

    public static AlertDialog setAlertDialog(@NonNull Context uiContext
            , @Nullable CharSequence title
            , @Nullable CharSequence message) {
        return setAlertDialog(uiContext, title, message, null);
    }

    public static AlertDialog setAlertDialog(@NonNull Context uiContext
            , @NonNull CharSequence title
            , @Nullable CharSequence message
            , @Nullable CharSequence neutralBtn) {
        return setAlertDialog(uiContext, title, message, neutralBtn, null);
    }

    public static AlertDialog setAlertDialog(@NonNull Context uiContext
            , @NonNull CharSequence title
            , @Nullable CharSequence message
            , @Nullable CharSequence neutralBtn
            , @Nullable CharSequence positiveBtn) {
        return setAlertDialog(uiContext, title, message, neutralBtn, positiveBtn, null);
    }

    public static AlertDialog setAlertDialog(@NonNull Context uiContext
            , @NonNull CharSequence title
            , @Nullable CharSequence message
            , @Nullable CharSequence neutralBtn
            , @Nullable CharSequence positiveBtn
            , @Nullable CharSequence negativeBtn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(uiContext);
        builder.setTitle(title);
        builder.setMessage(message);

        if (neutralBtn != null) {
            builder.setNeutralButton(neutralBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        if (positiveBtn != null) {
            builder.setPositiveButton(positiveBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
        if (negativeBtn != null) {
            builder.setNeutralButton(negativeBtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }
    ///////////////////////////END Alert Dialog////////////////////////////////////
}
