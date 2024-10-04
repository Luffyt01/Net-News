package com.example.netnews.retrofit;

import com.example.netnews.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<News> getLatestNews(
            @Query("limit") int limit,
            @Query("country") String country,
            @Query("lang") String lang
    );

    @GET("topic-headlines")
    Call<News> getTopicHeadlines(
            @Query("topic") String topic,
            @Query("limit") int limit,
            @Query("country") String country,
            @Query("lang") String lang
    );

    @GET("local-headlines")
    Call<News> getLocalNews(
            @Query("query") String query,
            @Query("country") String country,
            @Query("lang") String lang,
            @Query("limit") int limit
    );
    @GET("search")
    Call<News> searchNews(
            @Query("query") String query,
            @Query("limit") int limit,
            @Query("country") String country,
            @Query("lang") String lang
    );
}
