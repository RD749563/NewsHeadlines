package com.example.newsheadlines.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.newsheadlines.BuildConfig;
import com.example.newsheadlines.R;
import com.example.newsheadlines.Utils.ApplicationInstance;
import com.example.newsheadlines.Utils.UtilityMethods;
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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private final String API_KEY= BuildConfig.NEWS_API_KEY;
    private RecyclerView recyclerView;
    private String TAG=this.getClass().getSimpleName();
    private LinearLayoutManager layoutManager;
    private Mainarticleadapter mainarticleadapter;
    private Context mcontext;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onRefresh() {
        loadJSON();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        recyclerView=(RecyclerView)findViewById(R.id.activity_main_rv);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        onLoadingSwipeRefreshLayout();
    }



    public void loadJSON(){

        gethttpclient();
        swipeRefreshLayout.setRefreshing(true);
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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<responsemodel> call, Throwable t) {
                Log.e("out",t.toString());
                swipeRefreshLayout.setRefreshing(false);
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

    private void onLoadingSwipeRefreshLayout() {
        if (!UtilityMethods.isNetworkAvailable()) {
            Toast.makeText(MainActivity.this, "Could not load latest News. Please turn on the Internet.", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        loadJSON();
                    }
                }
        );
    }

}
