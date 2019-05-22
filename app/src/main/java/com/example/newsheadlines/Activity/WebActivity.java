package com.example.newsheadlines.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.example.newsheadlines.R;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webactivity);
        final String url = getIntent().getStringExtra("url");
        WebView webView = findViewById(R.id.webview);
        webView.loadUrl(url);
    }
}
