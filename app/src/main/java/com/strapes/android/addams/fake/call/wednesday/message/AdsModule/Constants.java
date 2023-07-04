package com.strapes.android.addams.fake.call.wednesday.message.AdsModule;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.FrameLayout;

import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdView;

public class Constants {

    public  static MaxNativeAdView nativeAdView;
    public static String layouttype = "";
    public static FrameLayout frameLayout;
    public static FrameLayout nativeframe;
    public  static MaxAdView adView ;

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
