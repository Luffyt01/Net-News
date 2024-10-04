package com.example.netnews.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.netnews.model.Data;
import com.example.netnews.model.user.Bookmark;
import com.example.netnews.repository.Repository;
import com.example.netnews.room.History;
import com.example.netnews.room.HistoryDatabase;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private Repository repository;



    public NewsViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
    }

    public LiveData<List<Data>> getLatestNews(){
        return repository.getDataMutableData();
    }

    public LiveData<List<Data>> getSectionNews(String topic){
        return repository.getTopicMutableLiveData(topic);
    }

    public LiveData<List<Data>> getLocalNews(String query){
        return repository.getLocalMutableLD(query);
    }
    public LiveData<List<Data>> getSearchedNews(String query){
        return repository.getSearchMutableLD(query);
    }
    public void saveBookmark(String newsTitle,
                             String newsImage,
                             String newsSourceIcon,
                             String newsTime,
                             String newsLink){
        repository.saveBookmark(newsTitle,newsImage,newsSourceIcon,newsTime,newsLink);
    }

    public LiveData<List<Bookmark>> getBookmarks(){
        return repository.getBookmarkLD();
    }

    public void deleteBookmark(String title){
        repository.deleteBookmark(title);
    }

    public void addHistory(History history){
        repository.addHistory(history);
    }
    public void deleteHistory(History history){
        repository.deleteHistory(history);
    }

    public LiveData<List<History>> getHistory(){
        return repository.getHistory();
    }
}
