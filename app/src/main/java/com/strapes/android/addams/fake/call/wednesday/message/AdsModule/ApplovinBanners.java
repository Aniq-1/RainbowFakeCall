package com.strapes.android.addams.fake.call.wednesday.message.AdsModule;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.strapes.android.addams.fake.call.R;

public class ApplovinBanners implements MaxAdViewAdListener {


    public void createBannerAd(Context context, ViewGroup rootView) {
        //  MaxAdView adView = new MaxAdView(context.getResources().getString(R.string.applovin_banner), context);
        MaxAdView adView = new MaxAdView(context.getResources().getString(R.string.applovin_banner), context);
        adView.setListener(this);
        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = context.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.banner_height);
        //  int heightPx =50;


        try {
            adView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));

            rootView.addView(adView);
            // Load the ad
            adView.loadAd();
        }
        catch (Exception e){
            Log.e("Exception e", "createbannar ads "+e.getMessage());
        }
    }

    public void createBannerAd2(Context context) {
        //  MaxAdView adView = new MaxAdView(context.getResources().getString(R.string.applovin_banner), context);
        MaxAdView adView = new MaxAdView(context.getResources().getString(R.string.applovin_banner), context);
        adView.setListener(this);
        // Stretch to the width of the screen for banners to be fully functional
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        // Banner height on phones and tablets is 50 and 90, respectively
        int heightPx = context.getApplicationContext().getResources().getDimensionPixelSize(R.dimen.banner_height);
        //  int heightPx =50;


        try {
            adView.setLayoutParams(new FrameLayout.LayoutParams(width, heightPx));
            // Load the ad
            adView.loadAd();
            Constants.adView=adView;

        }
        catch (Exception e){
            Log.e("Exception e", "createbannar ads "+e.getMessage());
        }
    }

    // MAX Ad Listener
    @Override
    public void onAdLoaded(final MaxAd maxAd) {
        Log.e("TAG", "onAdLoadedmaxAd");
        BannarApplovinProrityAndAdmob.shimmerFrameLayoutBanner.removeAllViews();
        BannarApplovinProrityAndAdmob.shimmerFrameLayoutBanner.stopShimmer();
    }

    @Override
    public void onAdLoadFailed(final String adUnitId, final MaxError error) {
        Log.e("TAG", "onAdLoadFailedmaxAd" +ModuleModelClass.applovinbannar);
    /*    if (!ModuleModelClass.applovinbannar){
            BannarApplovinProrityAndAdmob.initBannerAdmob();
        }*/

        // AdmobLoading.BannarAdsLoadingAdmob(Constants.activity, Constants.banner );
        //

    }

    @Override
    public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {
        Log.e("TAG", "onAdLoadFailedmaxAd");
    }

    @Override
    public void onAdClicked(final MaxAd maxAd) {
    }

    @Override
    public void onAdExpanded(final MaxAd maxAd) {
    }

    @Override
    public void onAdCollapsed(final MaxAd maxAd) {
    }

    @Override
    public void onAdDisplayed(final MaxAd maxAd) {
    }

    @Override
    public void onAdHidden(final MaxAd maxAd) {
    }
}
