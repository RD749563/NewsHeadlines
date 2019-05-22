package com.example.newsheadlines.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.newsheadlines.BuildConfig;
import com.example.newsheadlines.R;
import com.example.newsheadlines.Utils.ApplicationInstance;
import com.example.newsheadlines.adapter.Mainarticleadapter;
import com.example.newsheadlines.models.Article;
import com.example.newsheadlines.models.responsemodel;
import com.example.newsheadlines.rests.APIinterface;
import com.example.newsheadlines.rests.Apiclient;
import com.example.newsheadlines.rests.network.OfflineResponseCacheInterceptor;
import com.example.newsheadlines.rests.network.ResponseCacheInterceptor;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String API_KEY= BuildConfig.NEWS_API_KEY;
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
        loadJSON();
    }



    public void loadJSON(){

        gethttpclient();
        APIinterface apIinterface= Apiclient.getClient(gethttpclient()).create(APIinterface.class);
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

    //httpclientbuilder

    public OkHttpClient.Builder gethttpclient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(new ResponseCacheInterceptor());
        httpClient.addInterceptor(new OfflineResponseCacheInterceptor());
        httpClient.cache(new Cache(new File(ApplicationInstance.getApplicationInstance().getCacheDir(), "ResponsesCache"), 10 * 1024 * 1024));
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);
        return httpClient;
    }

}
