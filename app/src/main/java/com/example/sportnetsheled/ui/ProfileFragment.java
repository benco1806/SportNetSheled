package com.example.sportnetsheled.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.ProfileGridAdapter;
import com.example.sportnetsheled.R;

import java.util.ArrayList;

public class ProfileFragment extends CustomFragment implements View.OnClickListener {

    private final int greyColor = Color.rgb(195, 198, 201), nonColor = 0x00000000;
    private TextView tvmyfav, tvmywork, tv;
    private boolean isMyfavSetted;
    private GridView gridView;
    private ProfileGridAdapter adapter;
    private ArrayList<Post> posts;

    public ProfileFragment(int layout, Context context) {
        super(layout, context);
    }

    @Override
    protected void intilaize() {
        tvmyfav = thisView.findViewById(R.id.tvmyfav);
        tvmywork = thisView.findViewById(R.id.tvmywork);

        tvmyfav.setBackgroundColor(greyColor);
        isMyfavSetted = true;

        tvmyfav.setOnClickListener(this);
        tvmywork.setOnClickListener(this);

        posts = new ArrayList<>();

        for(int i = 0; i < 12; i++)
            posts.add(new Post());

        adapter = new ProfileGridAdapter(context, posts);
        gridView = (GridView) thisView.findViewById(R.id.profileGrid);

        gridView.setAdapter(adapter);

        tv = (TextView)thisView.findViewById(R.id.textView);

        if (!isSynchronized){
            onUserDataHasSynchronized();
            isSynchronized = false;
        }

    }

    @Override
    public void onClick(View view) {
        if((!isMyfavSetted) && view == tvmyfav){
            tvmywork.setBackgroundColor(nonColor);
            tvmyfav.setBackgroundColor(greyColor);
            isMyfavSetted = true;
        }else
            if(isMyfavSetted && view == tvmywork){
                tvmywork.setBackgroundColor(greyColor);
                tvmyfav.setBackgroundColor(nonColor);
                isMyfavSetted = false;
        }
    }

    @Override
    public void onUserDataHasSynchronized() {
        if(thisView != null){
          tv.setText(MainActivity.user.getUserName());
        }else{
            isSynchronized = false;
        }
    }
}
