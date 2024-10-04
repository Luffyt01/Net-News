package com.example.netnews.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    @BindingAdapter("photoUrl")
    public static void loadImage(ImageView imageView, String imageURL){
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }


    @SerializedName("published_datetime_utc")
    @Expose
    private String publishedDatetimeUtc;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    @SerializedName("source_logo_url")
    @Expose
    private String sourceLogoUrl;
    @SerializedName("source_favicon_url")
    @Expose
    private String sourceFaviconUrl;
    @BindingAdapter("sourceIcon")
    public static void loadIcon(ImageView imageView, String imageURL){
        Glide.with(imageView.getContext())
                .load(imageURL)
                .into(imageView);
    }
    @SerializedName("sub_articles")
    @Expose
    private List<SubArticle> subArticles;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPublishedDatetimeUtc() {
        String dateTimeString = publishedDatetimeUtc;
        if(dateTimeString == null){
            return "";
        }
        Instant pastInstant = Instant.parse(dateTimeString);

        ZonedDateTime pastTime = pastInstant.atZone(ZoneOffset.UTC);
        ZonedDateTime currentTime = ZonedDateTime.now(ZoneOffset.UTC);

        Duration duration = Duration.between(pastTime, currentTime);

        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;


        String str = "";
        if(days == 0){
            if(hours == 0){
                if(minutes == 0){
                    str = new String(seconds+" seconds ago");
                }else{
                    if(minutes == 1){
                        str = new String(minutes+" minute ago");
                    }else{
                        str = new String(minutes+" minutes ago");
                    }
                }
            }else{
                if(hours == 1){
                    str = new String(hours+" hour ago");
                }else{
                    str = new String(hours+" hours ago");
                }
            }
        }else{
            if(days == 1){
                str = new String(days+" day ago");
            }else{
                str = new String(days+" days ago");
            }
        }
        return str;
    }

    public void setPublishedDatetimeUtc(String publishedDatetimeUtc) {
        this.publishedDatetimeUtc = publishedDatetimeUtc;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceLogoUrl() {
        return sourceLogoUrl;
    }

    public void setSourceLogoUrl(String sourceLogoUrl) {
        this.sourceLogoUrl = sourceLogoUrl;
    }

    public String getSourceFaviconUrl() {
        return sourceFaviconUrl;
    }

    public void setSourceFaviconUrl(String sourceFaviconUrl) {
        this.sourceFaviconUrl = sourceFaviconUrl;
    }

    public List<SubArticle> getSubArticles() {
        return subArticles;
    }

    public void setSubArticles(List<SubArticle> subArticles) {
        this.subArticles = subArticles;
    }

}