package com.example.netnews.ui.history;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netnews.R;
import com.example.netnews.adapter.HistoryAdapter;
import com.example.netnews.databinding.ActivityHistoryBinding;
import com.example.netnews.room.History;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ActivityHistoryBinding binding;
    private RecyclerView historyRV;
    private NewsViewModel newsViewModel;
    private HistoryAdapter adapter;
    private ArrayList<History> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        historyList = new ArrayList<>();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getAllHistory();
    }

    private void getAllHistory() {
        newsViewModel.getHistory().observe(this, new Observer<List<History>>() {
            @Override
            public void onChanged(List<History> histories) {
                historyList = (ArrayList<History>) histories;
                displayHistory();
            }
        });
    }

    private void displayHistory() {
        historyRV = binding.historyRecyclerView;
        historyRV.setLayoutManager(new LinearLayoutManager(this));
        historyRV.setHasFixedSize(true);
        adapter = new HistoryAdapter(this, historyList, newsViewModel);
        historyRV.setAdapter(adapter);
    }
}