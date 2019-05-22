package com.example.newsheadlines.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.newsheadlines.Utils.ApplicationInstance;
import com.example.newsheadlines.adapter.Mainarticleadapter;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.example.newsheadlines.R;
import com.example.newsheadlines.models.Article;
import com.example.newsheadlines.models.responsemodel;
import com.example.newsheadlines.rests.APIinterface;
import com.example.newsheadlines.rests.Apiclient;
import com.example.newsheadlines.rests.network.OfflineResponseCacheInterceptor;
import com.example.newsheadlines.rests.network.ResponseCacheInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY="991f610ab7dc4e349d2315de5c8ce7be";
    private RecyclerView recyclerView;
    private String TAG=this.getClass().getSimpleName();
    private LinearLayoutManager layoutManager;
    private Mainarticleadapter mainarticleadapter;
    private Context mcontext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.activity_main_rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mcontext=this;

        APIinterface apIinterface= Apiclient.getClient(this).create(APIinterface.class);
        Call<responsemodel> call= apIinterface.getHeadlines("the-times-of-india",API_KEY);
        call.enqueue(new Callback<responsemodel>() {
            @Override
            public void onResponse(Call<responsemodel> call, Response<responsemodel> response) {
                if (response.isSuccessful() && response.body().getArticles()!= null){
                    ArrayList<Article> articleLists=response.body().getArticles();
                    Log.i(TAG,"list"+articleLists);
                    if(articleLists.size()>0){
                        mainarticleadapter=new Mainarticleadapter(mcontext,articleLists);
                        recyclerView.setAdapter(mainarticleadapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<responsemodel> call, Throwable t) {
                Log.e("out",t.toString());
            }
        });

    }
}
