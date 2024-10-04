package com.example.netnews.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netnews.R;
import com.example.netnews.databinding.BookmarkListItemBinding;
import com.example.netnews.model.Data;
import com.example.netnews.model.user.Bookmark;
import com.example.netnews.ui.webview.NewsDetailsActivity;
import com.example.netnews.viewmodel.NewsViewModel;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {
    private Context context;
    private ArrayList<Bookmark> bookmarkArrayList;
    private NewsViewModel newsViewModel;

    public BookmarkAdapter(Context context, ArrayList<Bookmark> bookmarkArrayList, NewsViewModel newsViewModel) {
        this.context = context;
        this.bookmarkArrayList = bookmarkArrayList;
        this.newsViewModel = newsViewModel;
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        BookmarkListItemBinding bookmarkListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.bookmark_list_item,
                viewGroup,
                false
        );
        return new BookmarkViewHolder(bookmarkListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder bookmarkViewHolder, int i) {
        Bookmark bookmark = bookmarkArrayList.get(i);
        bookmarkViewHolder.bookmarkListItemBinding.setBookmark(bookmark);
    }

    @Override
    public int getItemCount() {
        if (bookmarkArrayList!=null){
            return bookmarkArrayList.size();
        }else{
            return 0;
        }
    }

    class BookmarkViewHolder extends RecyclerView.ViewHolder{
        private BookmarkListItemBinding bookmarkListItemBinding;

        public BookmarkViewHolder(@NonNull BookmarkListItemBinding bookmarkListItemBinding) {
            super(bookmarkListItemBinding.getRoot());
            this.bookmarkListItemBinding = bookmarkListItemBinding;

            bookmarkListItemBinding.newsTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bookmark bookmark = bookmarkArrayList.get(getBindingAdapterPosition());
                    String link = bookmark.getNews_link();
                    Intent i = new Intent(context, NewsDetailsActivity.class);
                    i.putExtra("link",link);
                    context.startActivity(i);
                }
            });

            bookmarkListItemBinding.deleteMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemID = getBindingAdapterPosition();
                    Bookmark bookmark = bookmarkArrayList.get(itemID);
                    newsViewModel.deleteBookmark(bookmark.getNews_title());
                    bookmarkArrayList.remove(itemID);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
