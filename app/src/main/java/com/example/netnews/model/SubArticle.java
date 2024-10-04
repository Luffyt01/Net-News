package com.example.netnews.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubArticle {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
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
        return publishedDatetimeUtc;
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

}