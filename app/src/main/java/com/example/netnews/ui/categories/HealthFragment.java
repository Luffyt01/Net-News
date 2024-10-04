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

import com.example.netnews.R;
import com.example.netnews.adapter.NewsAdapter;
import com.example.netnews.authentication.AuthViewModel;
import com.example.netnews.databinding.FragmentHealthBinding;
import com.example.netnews.model.Data;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;


public class HealthFragment extends Fragment {

    private FragmentHealthBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;
    private ArrayList<Data> dataArrayList;
    private RecyclerView healthRV;



    public HealthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHealthBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        dataArrayList = new ArrayList<>();
        Handler handler = new Handler(Looper.getMainLooper());

        Runnable delayedRunnable = new Runnable() {
            @Override
            public void run() {
                getHealthNews();
            }
        };

        handler.postDelayed(delayedRunnable,9000);


    }

    private void getHealthNews() {
        newsViewModel.getSectionNews("HEALTH").observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                dataArrayList = (ArrayList<Data>) data;
                displayHealthNews();
            }
        });
    }

    private void displayHealthNews() {
        healthRV = binding.healthRecyclerView;
        healthRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), dataArrayList,newsViewModel,getViewLifecycleOwner());
        healthRV.setAdapter(adapter);
    }
}