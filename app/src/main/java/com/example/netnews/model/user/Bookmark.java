package com.example.netnews.model.user;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Bookmark {
    private String news_title;
    private String news_photo;
    @BindingAdapter("newsPhoto")
    public static void loadImage(ImageView imageView, String imageURL){
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }
    private String news_link;
    private String source_logo;
    @BindingAdapter("sourceIcon")
    public static void loadSourceIcon(ImageView imageView, String imageURL){
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }
    private String news_time;

    public Bookmark() {
    }

    public Bookmark(String news_title, String news_photo, String news_link, String source_logo, String news_time) {
        this.news_title = news_title;
        this.news_photo = news_photo;
        this.news_link = news_link;
        this.source_logo = source_logo;
        this.news_time = news_time;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_photo() {
        return news_photo;
    }

    public void setNews_photo(String news_photo) {
        this.news_photo = news_photo;
    }

    public String getNews_link() {
        return news_link;
    }

    public void setNews_link(String news_link) {
        this.news_link = news_link;
    }

    public String getSource_logo() {
        return source_logo;
    }

    public void setSource_logo(String source_logo) {
        this.source_logo = source_logo;
    }

    public String getNews_time() {
       return news_time;
    }

    public void setNews_time(String news_time) {
        this.news_time = news_time;
    }
}
