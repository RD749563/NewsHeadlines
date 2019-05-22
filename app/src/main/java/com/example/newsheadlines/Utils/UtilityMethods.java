package com.example.newsheadlines.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class UtilityMethods  {

    /**
     * Method to detect network connection on the device
     */


    public static boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) ApplicationInstance.getApplicationInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
