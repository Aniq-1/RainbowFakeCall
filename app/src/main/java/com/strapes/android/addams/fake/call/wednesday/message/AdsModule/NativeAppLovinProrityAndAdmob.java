package com.strapes.android.addams.fake.call.wednesday.message.AdsModule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.strapes.android.addams.fake.call.R;

public class NativeAppLovinProrityAndAdmob {
    private static RelativeLayout shimmerNative;
    public    static ShimmerFrameLayout shimmerFrameLayoutNative;
    public    static Activity activityy;
    public    static FrameLayout frameLayout;
    public static String check;

    public  static void loadingnativeadsWelcome(Activity activity,String str){
        ModuleModelClass.Admobshowing=true;
        check = str;
        ModuleModelClass.nameofcalss="NativeAppLovinProrityAndAdmob";
        frameLayout= Constants.nativeframe;
        activityy=activity;
        NativeAds nativeAds = new NativeAds();
        NativeAds.nativeAdContainerView = Constants.nativeframe;
        NativeAds.view = Constants.nativeframe.getRootView();
        nativeAds.createNativeAdLoaderWelcome();
        NativeAds.loadMaxNativeAd();
    }

    public  static void loadingnativeads(Activity activity,String str){
        ModuleModelClass.Admobshowing=true;
        check = str;
        ModuleModelClass.nameofcalss="NativeAppLovinProrityAndAdmob";
        frameLayout= Constants.nativeframe;
        activityy=activity;
        NativeAds nativeAds = new NativeAds();
        NativeAds.nativeAdContainerView = Constants.nativeframe;
        NativeAds.view = Constants.nativeframe.getRootView();
        nativeAds.createNativeAdLoader();
        NativeAds.loadMaxNativeAd();
    }

    public  static void Preloadingnativeads(View view){
        ModuleModelClass.Admobshowing=true;
        ModuleModelClass.nameofcalss="NativeAppLovinProrityAndAdmob";
        NativeAds nativeAds = new NativeAds();
        NativeAds.view=view;
        nativeAds.createNativeAdLoader();
        NativeAds.loadMaxNativeAd();
    }


    public  static void loadingNativeAds(FrameLayout frameLayouts,Activity activity){
        ModuleModelClass.Admobshowing=true;
        ModuleModelClass.nameofcalss="NativeAppLovinPriorityAndAdmob";
        ModuleModelClass.activity = activity;
        ModuleModelClass.frameLayout = frameLayouts;
        activityy=activity;
        frameLayout = Constants.nativeframe;
        initShimmerNative(activity,frameLayouts);
        NativeAds nativeAds = new NativeAds();
        NativeAds.nativeAdContainerView = frameLayouts;
        NativeAds.view = frameLayouts.getRootView();
        nativeAds.createNativeAdLoader();
        NativeAds.loadMaxNativeAd();
    }

    private static void initShimmerNative(Activity activity, FrameLayout frameLayout) {

        LayoutInflater layoutInflater = null;
        if (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) != null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        assert layoutInflater != null;
        shimmerNative = (RelativeLayout) layoutInflater.inflate(R.layout.shimmer_ly_native_media, null);
        shimmerFrameLayoutNative = shimmerNative.findViewById(R.id.shimmer_layout);
        frameLayout.removeAllViews();
        frameLayout.addView(shimmerNative);
        shimmerFrameLayoutNative.startShimmer();

    }


}
