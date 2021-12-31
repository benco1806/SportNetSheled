package com.example.sportnetsheled.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportnetsheled.Post;
import com.example.sportnetsheled.R;
import com.example.sportnetsheled.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ArrayList<Post> posts;
    private Adapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        posts = new ArrayList<>();

        Post p = new Post("1111", "gs://todolistbenfirebase.appspot.com/20211229_094441.mp4");
        Post p1 = new Post("kfv;~~dl", "gs://todolistbenfirebase.appspot.com/20211229_094450.mp4");

        posts.add(p);
        posts.add(p1);

        ListView lv = binding.lv;
        adapter = new Adapter(getActivity(), posts);
        lv.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}