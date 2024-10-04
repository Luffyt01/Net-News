package com.example.netnews.ui.categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.netnews.R;
import com.example.netnews.adapter.NewsAdapter;
import com.example.netnews.authentication.AuthViewModel;
import com.example.netnews.databinding.FragmentAllBinding;
import com.example.netnews.model.Data;
import com.example.netnews.model.user.Bookmark;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;


public class AllFragment extends Fragment {

    private FragmentAllBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;
    private RecyclerView allRV;
    private ArrayList<Data> dataArrayList;


    public AllFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsViewModel = new ViewModelProvider(this)
                .get(NewsViewModel.class);
        dataArrayList = new ArrayList<>();

        Handler handler = new Handler(Looper.getMainLooper());

        Runnable delayedRunnable = new Runnable() {
            @Override
            public void run() {
                getAllNews();
            }
        };

        handler.postDelayed(delayedRunnable,1000);

    }

    private void getAllNews() {
        newsViewModel.getSectionNews("NATIONAL").observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                dataArrayList = (ArrayList<Data>) data;
                displayAllNews();
            }
        });
    }

    private void displayAllNews() {
        binding.progressBar.setVisibility(View.GONE);
        binding.allRecyclerView.setVisibility(View.VISIBLE);
        allRV = binding.allRecyclerView;
        allRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), dataArrayList, newsViewModel,getViewLifecycleOwner());
        allRV.setAdapter(adapter);
    }


}