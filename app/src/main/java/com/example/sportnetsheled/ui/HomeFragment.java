package com.example.sportnetsheled.ui;

import static com.example.sportnetsheled.MainActivity.USER;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sportnetsheled.MyAdapter;
import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.PostManager;
import com.example.sportnetsheled.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class HomeFragment extends CustomFragment {

    private ArrayList<Post> posts;
    private MyAdapter adapter;

    public HomeFragment(int layout, Context context) {
        super(layout, context);
        posts = new ArrayList<>();

    }


    @Override
    protected void intilaze() {
        ListView lv = (ListView) thisView.findViewById(R.id.list);
        adapter = new MyAdapter(context, posts, MainActivity.postManager);
        lv.setAdapter(adapter);
        setPosts(null);
        final SwipeRefreshLayout pullToRefresh = thisView.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setPosts(pullToRefresh);
            }
        });
    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    private void setPosts(SwipeRefreshLayout pullToRefresh) {
        if (USER.getFollowing() != null) {
            DatabaseReference postsRef = PostManager.getPostsRef();
            postsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        ArrayList<Post> posts = new ArrayList<>();
                        for (DataSnapshot shot : task.getResult().getChildren()) {
                            Post p = shot.getValue(Post.class);
                            p.setRefName(shot.getKey());
                            for (int i = 0; i < USER.getFollowing().size(); i++) {
                                if(p.getUiduser().contains(USER.getFollowing().get(i))){
                                    posts.add(p);
                                }
                            }
                        }
                        Log.d("@homePage-Posts", "loading is done :-)");
                        HomeFragment.this.posts = posts;
                        adapter.setPosts(HomeFragment.this.posts);

                    }else
                        //alerting error
                        errorLoadingPostsAlert();

                    if(pullToRefresh != null)
                        pullToRefresh.setRefreshing(false);

                    if(posts.isEmpty())
                        alerting();

                }
            });
        }else{
            if(pullToRefresh != null){
                pullToRefresh.setRefreshing(false);
            }
            alerting();
        }

    }

    private void alerting(){
        new AlertDialog.Builder(context)
                .setTitle("Message")
                .setMessage("please find some users to follow after them on the explore page :-)")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setNeutralButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        //alerting user: find some users to follow!
        HomeFragment.this.posts = new ArrayList<>();
        adapter.setPosts(HomeFragment.this.posts);

    }
}
