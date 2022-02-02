package com.example.sportnetsheled.ui;

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

    public CustomFragment(@LayoutRes int layout, Context context) {
        this.layout = layout;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(thisView == null){
            thisView = inflater.inflate(layout, container, false);
            intilaize();
        }

        return thisView;
    }

    protected void intilaize(){
        //throw new Exception("you must override it!!");
    }


}