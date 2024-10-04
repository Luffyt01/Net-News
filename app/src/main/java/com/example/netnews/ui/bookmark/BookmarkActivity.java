package com.example.netnews.ui.bookmark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netnews.R;
import com.example.netnews.adapter.BookmarkAdapter;
import com.example.netnews.databinding.ActivityBookmarkBinding;
import com.example.netnews.model.user.Bookmark;
import com.example.netnews.ui.MainActivity;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private ActivityBookmarkBinding binding;
    private BookmarkAdapter adapter;
    private NewsViewModel newsViewModel;
    private RecyclerView bookmarkRV;
    private ArrayList<Bookmark> bookmarkArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_bookmark);
        bookmarkArrayList = new ArrayList<>();

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        binding.tabTitle.setText("Bookmark");
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getBookmarks();


    }

    private void getBookmarks() {
        newsViewModel.getBookmarks().observe(this, new Observer<List<Bookmark>>() {
            @Override
            public void onChanged(List<Bookmark> bookmarks) {
                bookmarkArrayList = (ArrayList<Bookmark>) bookmarks;
                displayBookmarks();
            }
        });
    }

    private void displayBookmarks() {
        bookmarkRV = binding.bookmarkRecyclerView;
        bookmarkRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookmarkAdapter(this, bookmarkArrayList, newsViewModel);
        bookmarkRV.setAdapter(adapter);

    }
}