package com.example.newsheadlines.rests;

import com.example.newsheadlines.models.responsemodel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIinterface {
    @GET("top-headlines")
    Call<responsemodel> getHeadlines(@Query("sources") String sources,
                                    @Query("apiKey") String apiKey);
}
