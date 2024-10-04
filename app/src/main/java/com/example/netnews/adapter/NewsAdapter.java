package com.example.netnews.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netnews.R;
import com.example.netnews.authentication.AuthViewModel;
import com.example.netnews.databinding.NewsListItemBinding;
import com.example.netnews.model.Data;
import com.example.netnews.model.user.Bookmark;
import com.example.netnews.room.History;
import com.example.netnews.ui.webview.NewsDetailsActivity;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private ArrayList<Data> dataArrayList;
    private NewsViewModel newsViewModel;
    private LifecycleOwner lifecycleOwner;

    public NewsAdapter(Context context, ArrayList<Data> dataArrayList, NewsViewModel newsViewModel, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.dataArrayList = dataArrayList;
        this.newsViewModel = newsViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        NewsListItemBinding newsListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.news_list_item,
                viewGroup,
                false
        );
        return new NewsViewHolder(newsListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        final Data[] currentData = new Data[1];
        ArrayList<String> titleList = new ArrayList<>();
        newsViewModel.getBookmarks().observe(lifecycleOwner, new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {
                for(Bookmark bookmark: bookmarks){
                    titleList.add(bookmark.getNews_title());
                }
                currentData[0] = dataArrayList.get(i);
                newsViewHolder.newsListItemBinding.bookmarkIcon.setChecked(titleList.contains(currentData[0].getTitle()));
                newsViewHolder.newsListItemBinding.setNews(currentData[0]);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataArrayList!=null){
            return dataArrayList.size();
        }else{
            return 0;
        }
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{
        private NewsListItemBinding newsListItemBinding;

        public NewsViewHolder(@NonNull NewsListItemBinding newsListItemBinding) {
            super(newsListItemBinding.getRoot());
            this.newsListItemBinding = newsListItemBinding;

            newsListItemBinding.newsTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data data = dataArrayList.get(getBindingAdapterPosition());

                    History history = new History(data.getTitle(),
                            data.getPhotoUrl(),
                            data.getLink(),
                            data.getSourceFaviconUrl(),
                            data.getPublishedDatetimeUtc());
                    newsViewModel.addHistory(history);

                    String link = data.getLink();
                    Intent i = new Intent(context, NewsDetailsActivity.class);
                    i.putExtra("link",link);
                    context.startActivity(i);
                }
            });

            newsListItemBinding.bookmarkIcon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked){
                        newsListItemBinding.bookmarkIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Data data = dataArrayList.get(getBindingAdapterPosition());
                                newsViewModel.deleteBookmark(data.getTitle());
                            }
                        });
                    }else{
                        newsListItemBinding.bookmarkIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Data data = dataArrayList.get(getBindingAdapterPosition());
                                newsViewModel.saveBookmark(data.getTitle(),
                                        data.getPhotoUrl(),
                                        data.getSourceFaviconUrl(),
                                        data.getPublishedDatetimeUtc(),
                                        data.getLink());
                            }
                        });

                    }
                }
            });

            newsListItemBinding.shareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data data = dataArrayList.get(getBindingAdapterPosition());
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, data.getLink());
                    context.startActivity(Intent.createChooser(i,"Choose a platform"));
                }
            });




        }

        public void updateBookmark(List<String> titleList){
            for(Data data: dataArrayList){
                for(String title: titleList){
                    if(!title.equals(data.getTitle())){
                        newsListItemBinding.bookmarkIcon.setChecked(false);
                        break;
                    }
                }
            }
        }
    }


}


