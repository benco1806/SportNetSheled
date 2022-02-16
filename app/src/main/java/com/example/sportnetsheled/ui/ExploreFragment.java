package com.example.sportnetsheled.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.example.sportnetsheled.R;
import com.google.firebase.auth.FirebaseAuth;


public class ExploreFragment extends CustomFragment {



    public ExploreFragment(int layout, Context context) {
        super(layout, context);
    }


    @Override
    protected void intilaize() {
        ImageView imageView = thisView.findViewById(R.id.imageView);
        imageView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = view -> {
        view.setBackgroundColor(Color.YELLOW);
        FirebaseAuth.getInstance().signOut();
    };


}
