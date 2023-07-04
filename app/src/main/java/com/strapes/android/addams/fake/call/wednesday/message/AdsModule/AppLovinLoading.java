package com.strapes.android.addams.fake.call.wednesday.message.AdsModule;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class AppLovinLoading {
    public static void LoadingApplovinNative(FrameLayout frameLayout){
        if (Constants.nativeAdView!=null) {
            NativeAds.nativeAdContainerView=null;
            frameLayout.setVisibility(View.VISIBLE);
            //    NativeAds.nativeAdContainerView.removeView(NativeAds.nativeAdContainerView.getRootView());
            // NativeAds.nativeAdContainerView.removeAllViews();
            NativeAds.nativeAdContainerView = frameLayout;
            NativeAds.view = frameLayout.getRootView();

            if(Constants.nativeAdView.getParent() != null) {
                ((ViewGroup)    Constants.nativeAdView.getParent()).removeView( Constants.nativeAdView); // <- fix
            }
       /*     try {
                NativeAds.nativeAdContainerView.removeAllViews();
            }catch (Exception e){

            }*/
            // NativeAds.nativeAdContainerView.removeView(NativeAds.nativeAdContainerView);

            NativeAds.nativeAdContainerView.addView(Constants.nativeAdView);
            NativeAppLovinProrityAndAdmob.Preloadingnativeads(frameLayout.getRootView());
        }else{
            frameLayout.setVisibility(View.GONE);
            NativeAppLovinProrityAndAdmob.Preloadingnativeads(frameLayout.getRootView());
            //AdmobLoading.NativeAdsLoadingAdmob(Constants.activityWelcome, frameLayout, R.layout.native_large);
        }
    }

    public static void loadingApplovinBannar(ViewGroup rootView, Activity activity){
        if (Constants.adView!=null) {
            rootView.setVisibility(View.VISIBLE);
            rootView.addView(Constants.adView);
            BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(activity);
        }else{
            rootView.setVisibility(View.GONE);
            BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(activity);
        }
    }


}

