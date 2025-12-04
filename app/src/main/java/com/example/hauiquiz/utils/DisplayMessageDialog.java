package com.example.hauiquiz.utils;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class DisplayMessageDialog {
    public static void displayMessage(Context context, String titile, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titile);
        builder.setMessage(msg);
        builder.create().show();
    }
}
