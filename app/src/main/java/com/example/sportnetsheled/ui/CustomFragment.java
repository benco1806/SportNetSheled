package com.example.sportnetsheled.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CustomFragment extends Fragment {

    protected View thisView = null;
    protected int layout;
    protected Context context;
    protected boolean isSynchronized = true;

    public CustomFragment(@LayoutRes int layout, Context context) {
        this.layout = layout;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(thisView == null){
            thisView = inflater.inflate(layout, container, false);
            intilaze();
        }

        return thisView;
    }

    protected void intilaze(){
        //throw new Exception("you must override it!!");
    }

    public void onUserDataHasSynchronized(){
        //throw new Exception("you must override it!!");
    }

    public void destroy(){
        thisView = null;
        onDestroy();
    }

    protected void errorLoadingPostsAlert(){
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("We couldn't load posts :-(\nplease try again later")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setNeutralButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
