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
import com.example.netnews.databinding.FragmentBusinessBinding;
import com.example.netnews.model.Data;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;


public class BusinessFragment extends Fragment {
    private FragmentBusinessBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;
    private ArrayList<Data> dataArrayList;
    private RecyclerView businessRV;


    public BusinessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBusinessBinding.inflate(inflater,container,false);
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
                getBusinessNews();
            }
        };

        handler.postDelayed(delayedRunnable,4000);


    }

    private void getBusinessNews() {
        newsViewModel.getSectionNews("BUSINESS").observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                dataArrayList = (ArrayList<Data>) data;
                displayBusinessNews();
            }
        });
    }

    private void displayBusinessNews() {
        binding.progressBar.setVisibility(View.GONE);
        binding.businessRecyclerView.setVisibility(View.VISIBLE);
        businessRV = binding.businessRecyclerView;
        businessRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), dataArrayList, newsViewModel,getViewLifecycleOwner());
        businessRV.setAdapter(adapter);
    }
}