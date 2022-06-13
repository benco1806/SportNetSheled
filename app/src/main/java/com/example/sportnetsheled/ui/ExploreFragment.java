package com.example.sportnetsheled.ui;

import static com.example.sportnetsheled.MainActivity.USER;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sportnetsheled.MainActivity;
import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.PostManager;
import com.example.sportnetsheled.R;
import com.example.sportnetsheled.MyAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ExploreFragment extends CustomFragment {

    private ArrayList<Post> posts;
    private MyAdapter adapter;

    public ExploreFragment(int layout, Context context) {
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

    private void setPosts(SwipeRefreshLayout pullToRefresh) {
        DatabaseReference postsRef = PostManager.getPostsRef();
        postsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful() && task.getResult().exists()) {
                    ArrayList<Post> posts = new ArrayList<>();
                    for (DataSnapshot shot : task.getResult().getChildren()) {
                        Post p = shot.getValue(Post.class);
                        p.setRefName(shot.getKey());
                        for (int i = 0; i < USER.getMuscles().size(); i++) {
                            if(p.getMuscles().contains(USER.getMuscles().get(i))){
                                posts.add(p);
                                break;
                            }
                        }
                    }
                    Log.d("@explorePage-Posts", "loading is done :-)");
                    ExploreFragment.this.posts = posts;
                    adapter.setPosts(ExploreFragment.this.posts);
                }else
                    errorLoadingPostsAlert();

                if (pullToRefresh != null)
                    pullToRefresh.setRefreshing(false);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setPosts(null);
    }
}


