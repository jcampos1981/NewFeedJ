package com.crdesigns.newfeedj;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    String BASE_URL = "https://newsapi.org/v2/";

    //Home by default
    @GET("top-headlines")
    Call<mainNews> getNews(
            @Query("country") String country,
            @Query("pageSize") int pagesize,
            @Query("apikey") String apikey
    );

    //Filtered by Search
    @GET("everything")
    Call<mainNews> getNewsEverything(
            @Query("qInTitle") String qInTitle,
            @Query("pageSize") int pagesize,
            @Query("apikey") String apikey
    );

    //Filtered by Channel
    @GET("top-headlines")
    Call<mainNews> getNewsChannel(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pagesize,
            @Query("apikey") String apikey
    );
}