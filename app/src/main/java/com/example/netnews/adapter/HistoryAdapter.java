package com.example.netnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netnews.R;
import com.example.netnews.databinding.HistoryListItemBinding;
import com.example.netnews.room.History;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private ArrayList<History> historyArrayList;
    private NewsViewModel newsViewModel;

    public HistoryAdapter(Context context, ArrayList<History> historyArrayList, NewsViewModel newsViewModel) {
        this.context = context;
        this.historyArrayList = historyArrayList;
        this.newsViewModel = newsViewModel;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        HistoryListItemBinding historyListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.history_list_item,
                viewGroup,
                false
        );
        return new HistoryViewHolder(historyListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        History history = historyArrayList.get(i);
        historyViewHolder.historyListItemBinding.setHistory(history);
    }

    @Override
    public int getItemCount() {
        if (historyArrayList!= null){
            return historyArrayList.size();
        }else{
            return 0;
        }
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{
        private HistoryListItemBinding historyListItemBinding;

        public HistoryViewHolder(@NonNull HistoryListItemBinding historyListItemBinding) {
            super(historyListItemBinding.getRoot());
            this.historyListItemBinding = historyListItemBinding;
        }
    }
}
