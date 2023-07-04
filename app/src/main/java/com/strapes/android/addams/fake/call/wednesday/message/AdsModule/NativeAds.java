package com.strapes.android.addams.fake.call.wednesday.message.AdsModule;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdRevenueListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.facebook.ads.AdView;
import com.strapes.android.addams.fake.call.R;

public class NativeAds implements MaxAdListener, MaxAdRevenueListener {
    public static MaxAdView adView;
    public static AdView adViewBanner;
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd loadedNativeAd;
    public  static ViewGroup nativeAdContainerView;
    public static View view;
    public static int counter=0;

    public NativeAds() {
    }

    public static  void loadMaxNativeAd() {
        nativeAdLoader.loadAd(createNativeAdView());
    }

    public  void  createNativeAdLoaderWelcome() {
        nativeAdLoader = new MaxNativeAdLoader(view.getResources().getString(R.string.applovin_native_welcome), view.getContext());
        nativeAdLoader.setRevenueListener(this);
        nativeAdLoader.setNativeAdListener(new NativeAdListener());

    }

    public  void  createNativeAdLoader() {
        nativeAdLoader = new MaxNativeAdLoader(view.getResources().getString(R.string.applovin_native), view.getContext());
        nativeAdLoader.setRevenueListener(this);
        nativeAdLoader.setNativeAdListener(new NativeAdListener());

    }

    private static MaxNativeAdView createNativeAdView() {
        MaxNativeAdViewBinder binder;
        Log.d("NTV1", "callingAdmobnative: " + Constants.layouttype);
        if (Constants.layouttype.equals("bottom")){

            binder = new MaxNativeAdViewBinder.Builder(R.layout.applovin_native_ad_bottom)
                    .setTitleTextViewId(R.id.title_text_view)
                    .setBodyTextViewId(R.id.body_text_view)
                    .setAdvertiserTextViewId(R.id.advertiser_textView)
                    .setIconImageViewId(R.id.icon_image_view)
                    .setMediaContentViewGroupId(R.id.media_view_container)
                    .setOptionsContentViewGroupId(R.id.options_view)
                    .setCallToActionButtonId(R.id.cta_button)
                    .build();
            Constants.layouttype="";
        }else{
            binder = new MaxNativeAdViewBinder.Builder(R.layout.applovin_native_ad)
                    .setTitleTextViewId(R.id.title_text_view)
                    .setBodyTextViewId(R.id.body_text_view)
                    .setAdvertiserTextViewId(R.id.advertiser_textView)
                    .setIconImageViewId(R.id.icon_image_view)
                    .setMediaContentViewGroupId(R.id.media_view_container)
                    .setOptionsContentViewGroupId(R.id.options_view)
                    .setCallToActionButtonId(R.id.cta_button)
                    .build();
        }


        return new MaxNativeAdView(binder,view.getContext());
    }

    @Override
    public void onAdRevenuePaid(MaxAd ad) {

    }

    @Override
    public void onAdLoaded(MaxAd ad) {
        //  NativeAppLovinProrityAndAdmob.shimmerFrameLayoutNative.stopShimmer();
    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {

    }

    @Override
    public void onAdClicked(MaxAd ad) {

    }

    @Override
    public void onAdLoadFailed(String adUnitId, MaxError error) {


    }

    @Override
    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

    }


    private static  class  NativeAdListener extends MaxNativeAdListener {
        @Override
        public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd nativeAd) {
            // Clean up any pre-existing native ad to prevent memory leaks.
            Log.d("LOAD1", "Ad loaded ");
/*            if (loadedNativeAd != null) {
                nativeAdLoader.destroy(loadedNativeAd);
            }
            if (ModuleModelClass.nameofcalss.equals("NativeAppLovinProrityAndAdmob")){
                NativeAppLovinProrityAndAdmob.shimmerFrameLayoutNative.stopShimmer();
                NativeAppLovinProrityAndAdmob.frameLayout.removeAllViews();
            }else if(ModuleModelClass.nameofcalss.equals("NativeAdmobProrityAndApplovin")){
                NativeAdmobProrityAndApplovin.shimmerFrameLayoutNative.stopShimmer();
                NativeAdmobProrityAndApplovin.frameLayouts.removeAllViews();
            }*/

            // Save ad for cleanup.
            loadedNativeAd = nativeAd;
            Constants.nativeAdView = nativeAdView;
            if(Constants.nativeAdView.getParent() != null) {
                ((ViewGroup)    Constants.nativeAdView.getParent()).removeView( Constants.nativeAdView); // <- fix
            }
       /*     try {
                NativeAds.nativeAdContainerView.removeAllViews();
            }catch (Exception e){

            }*/
            // NativeAds.nativeAdContainerView.removeView(NativeAds.nativeAdContainerView);

            NativeAds.nativeAdContainerView.addView(Constants.nativeAdView);
        }

        @Override
        public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
            // Native ad load failed.
            // AppLovin recommends retrying with exponentially higher delays up to a maximum delay.
           /* if (ModuleModelClass.Admobshowing){
                NativeAppLovinProrityAndAdmob.callingAdmobnative();
                NativeAppLovinProrityAndAdmob.shimmerFrameLayoutNative.stopShimmer();
            }*/
            //AdmobLoading.NativeAdsLoadingAdmob(Constants.activity, Constants.frameLayout, R.layout.native_large);

        }

        @Override
        public void onNativeAdClicked(final MaxAd nativeAd) {
        }
    }

}
