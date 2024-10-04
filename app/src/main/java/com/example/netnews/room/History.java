package com.example.netnews.room;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.Glide;

import org.checkerframework.checker.units.qual.C;

@Entity(tableName = "history_table")
public class History {

    @ColumnInfo(name = "news_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "news_title")
    private String title;

    @ColumnInfo(name = "news_photo")
    private String photo;
    @BindingAdapter("photo")
    public static void loadPhoto(ImageView imageView, String imageURL){
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }

    @ColumnInfo(name = "news_link")
    private String link;

    @ColumnInfo(name = "news_sourcePhoto")
    private String sourcePhoto;
    @BindingAdapter("sourcePhoto")
    public static void loadSourcePhoto(ImageView imageView, String imageURL){
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }

    @ColumnInfo(name = "news_time")
    private String publishTime;


    public History(String title, String photo, String link, String sourcePhoto, String publishTime) {
        this.title = title;
        this.photo = photo;
        this.link = link;
        this.sourcePhoto = sourcePhoto;
        this.publishTime = publishTime;
    }

    public History() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSourcePhoto() {
        return sourcePhoto;
    }

    public void setSourcePhoto(String sourcePhoto) {
        this.sourcePhoto = sourcePhoto;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }
}
