package com.strapes.android.addams.fake.call.wednesday.message.AdsModule;

import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.facebook.ads.AdView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.strapes.android.addams.fake.call.R;

public class BannarApplovinProrityAndAdmob {
    public static com.google.android.gms.ads.AdView madView;
    private  static RelativeLayout shimmerBanner;
    static ShimmerFrameLayout shimmerFrameLayoutBanner;
    private  static AdView adViewBanner;
    static FrameLayout frameLayout;
    static Activity activity;

    public static void initBannarPreloadingApplovin(Activity activityy){
        //    initShimmerBanner(activityy,admobContainer);

        ApplovinBanners applovinBanners = new ApplovinBanners();
        applovinBanners.createBannerAd2(activityy.getApplicationContext().getApplicationContext());
   /*    // BannerAd.adsshowing();
        BannerAd.adsloaded();*/
    }


    private static  AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    private static void initShimmerBanner(Activity activity, FrameLayout admobContainer) {
        LayoutInflater layoutInflater = null;
        if (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) != null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        assert layoutInflater != null;
        shimmerBanner = (RelativeLayout) layoutInflater.inflate(R.layout.shimmer_layout_banner, null);
        shimmerFrameLayoutBanner = shimmerBanner.findViewById(R.id.shimmer_layout);
        admobContainer.removeAllViews();
        admobContainer.addView(shimmerBanner);
        shimmerFrameLayoutBanner.startShimmer();
    }


}
