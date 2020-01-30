package com.example.newsheadlines.Utils;

import android.app.Application;

public class ApplicationInstance extends Application {
    public static ApplicationInstance applicationInstance;

    //Provides the application Instance


    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance=this;
    }

    public static ApplicationInstance getApplicationInstance() {
        return applicationInstance;
    }
}
