package com.example.netnews.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.netnews.model.Data;
import com.example.netnews.model.News;
import com.example.netnews.model.user.Bookmark;
import com.example.netnews.model.user.User;
import com.example.netnews.retrofit.ApiInterface;
import com.example.netnews.retrofit.RetrofitInstance;
import com.example.netnews.room.History;
import com.example.netnews.room.HistoryDAO;
import com.example.netnews.room.HistoryDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private Application application;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String userID;

    private final HistoryDAO historyDAO;
    ExecutorService executor;
    Handler handler;


    public Repository(Application application) {
        this.application = application;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userID = auth.getUid();

        HistoryDatabase historyDatabase = HistoryDatabase.getInstance(application);
        this.historyDAO = historyDatabase.getHistoryDAO();
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    private MutableLiveData<List<Data>> dataMutableData = new MutableLiveData<>();
    private MutableLiveData<List<Data>> topicMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Data>> localMutableLD = new MutableLiveData<>();
    private MutableLiveData<List<Data>> searchMutableLD = new MutableLiveData<>();
    private MutableLiveData<List<Bookmark>> bookmarkLD = new MutableLiveData<>();

    public MutableLiveData<List<Data>> getDataMutableData() {
        ApiInterface apiInterface = RetrofitInstance.getService();
        Call<News> newsCall = apiInterface.getLatestNews(10,"IN","en");
        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()){
                    List<Data> dataList = response.body().getData();
                    dataMutableData.setValue(dataList);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable throwable) {
                Log.e("Repository", "Failed to fetch data: " + throwable.getMessage());
            }
        });
        return dataMutableData;
    }

    public MutableLiveData<List<Data>> getTopicMutableLiveData(String topic) {
        ApiInterface apiInterface = RetrofitInstance.getService();
        Call<News> newsCall = apiInterface.getTopicHeadlines(
                topic,
                10,
                "IN",
                "en"
        );

        newsCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()){
                    List<Data> dataList = response.body().getData();
                    topicMutableLiveData.setValue(dataList);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable throwable) {
                Log.e("Repository", "Failed to fetch data: " + throwable.getMessage());
            }
        });

        return topicMutableLiveData;
    }

    public MutableLiveData<List<Data>> getLocalMutableLD(String query) {
        ApiInterface apiInterface = RetrofitInstance.getService();
        Call<News> localCall = apiInterface.getLocalNews(
                query,
                "IN",
                "en",
                50
        );
        localCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()){
                    List<Data> dataList = response.body().getData();
                    localMutableLD.setValue(dataList);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable throwable) {
                Log.e("Repository", "Failed to fetch data: " + throwable.getMessage());
            }
        });
        return localMutableLD;
    }

    public MutableLiveData<List<Data>> getSearchMutableLD(String query) {
        ApiInterface apiInterface = RetrofitInstance.getService();
        Call<News> searchCall = apiInterface.searchNews(
                query,
                50,
                "IN",
                "en"
        );

        searchCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful()){
                    List<Data> dataList = response.body().getData();
                    searchMutableLD.setValue(dataList);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable throwable) {
                Log.e("Repository", "Failed to fetch data: " + throwable.getMessage());
            }
        });
        return searchMutableLD;
    }

    public void saveBookmark(String newsTitle,
                             String newsImage,
                             String newsSourceIcon,
                             String newsTime,
                             String newsLink){

        String bookmarkID = databaseReference.child(userID).child("bookmarks").push().getKey();
        Bookmark bookmark = new Bookmark(newsTitle,newsImage,newsLink,newsSourceIcon,newsTime);
        databaseReference.child(userID).child("bookmarks").child(bookmarkID).setValue(bookmark)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(application, "Bookmarked", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public MutableLiveData<List<Bookmark>> getBookmarkLD() {
        DatabaseReference userData = databaseReference.child(userID);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                List<Bookmark> bookmarkList = new ArrayList<>();
                for(DataSnapshot bookmarkSnap: dataSnapshot.child("bookmarks").getChildren()){
                    Bookmark bookmark = bookmarkSnap.getValue(Bookmark.class);
                    bookmarkList.add(bookmark);
                }
                bookmarkLD.setValue(bookmarkList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(application, "Data not fetched", Toast.LENGTH_SHORT).show();
                Log.e("Data Fetching Error",databaseError.getDetails());
            }
        });
        return bookmarkLD;
    }

    public void deleteBookmark(String title){
        DatabaseReference bookmarkRef = databaseReference.child(userID).child("bookmarks");
        bookmarkRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Bookmark bookmark = snapshot.getValue(Bookmark.class);
                    if (bookmark!=null && bookmark.getNews_title().equals(title)){
                        snapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(application, "Bookmark deleted successfully", Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.e("Delete","Failed to delete bookmark: " + task.getException());
                                }
                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addHistory(History history){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                historyDAO.insert(history);
            }
        });
    }

    public void deleteHistory(History history){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                historyDAO.delete(history);
            }
        });
    }

    public LiveData<List<History>> getHistory(){
        return historyDAO.getHistory();
    }
}
