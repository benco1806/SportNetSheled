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

import com.example.sportnetsheled.Post;

import java.util.ArrayList;

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
            intilaize();
        }

        return thisView;
    }

    protected void intilaize(){
        //throw new Exception("you must override it!!");
    }

    public void onUserDataHasSynchronized(){
    }

    public void destroy(){
        thisView = null;
        onDestroy();
    }

}
