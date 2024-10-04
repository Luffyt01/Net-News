package com.example.netnews.ui.local;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.netnews.R;
import com.example.netnews.adapter.NewsAdapter;
import com.example.netnews.authentication.AuthViewModel;
import com.example.netnews.databinding.FragmentLocalBinding;
import com.example.netnews.model.Data;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocalFragment extends Fragment {

    private FragmentLocalBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;
    private ArrayList<Data> dataArrayList;
    private RecyclerView localRV;
    private Handler handler;
    private Runnable delayedRunnable;
    private static int lastPosition = 0;

    public LocalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLocalBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        dataArrayList = new ArrayList<>();

        handler = new Handler(Looper.getMainLooper());
        delayedRunnable = new Runnable() {
            @Override
            public void run() {
                if (isAdded() && getView() != null) {
                    getLocalNews();
                }
            }
        };

        handler.postDelayed(delayedRunnable, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handler != null && delayedRunnable != null) {
            handler.removeCallbacks(delayedRunnable);
        }
        binding = null; // Clear the binding to avoid memory leaks
    }

    private void getLocalNews() {
        if (getView() != null) { // Double-check if the view is still present
            newsViewModel.getLocalNews("Kharar").observe(getViewLifecycleOwner(), new Observer<List<Data>>() {
                @Override
                public void onChanged(List<Data> data) {
                    dataArrayList = (ArrayList<Data>) data;
                    displayLocalNews();
                }
            });
        }
    }

    private void displayLocalNews() {
        binding.progressBar.setVisibility(View.GONE);
        binding.localRecyclerView.setVisibility(View.VISIBLE);
        localRV = binding.localRecyclerView;
        localRV.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext(), dataArrayList, newsViewModel,getViewLifecycleOwner());
        localRV.setAdapter(adapter);
    }

}
