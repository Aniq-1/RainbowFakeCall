package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.material.snackbar.Snackbar;
import com.strapes.android.addams.fake.call.R;


import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.AppLovinLoading;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.BannarApplovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.NativeAppLovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class SettingScreen extends AppCompatActivity {
    private final String TAG = SettingScreen.class.getSimpleName();

    private LinearLayout share,privacy,moreApps,rateUs;

    private FrameLayout frameLayoutBanner,frameLayoutNative;
    private Button back_button;
    int i=1;

    private RelativeLayout rl_native_ad;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeAd nativeAd;

    Ad adfacebook;
    private android.app.AlertDialog loadind_dialog;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_screen);

        BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(this);

        share = findViewById(R.id.share_button);
        privacy = findViewById(R.id.privacy_button);
        moreApps = findViewById(R.id.more_apps_btn);
        rateUs = findViewById(R.id.rate_us_button);
        back_button = findViewById(R.id.back_button);
        rl_native_ad = findViewById(R.id.rl_native_ad);
        rl_native_ad.setVisibility(View.GONE);




        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Constant.shareApp(SettingScreen.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingScreen.this, PrivacyScreen.class));
                finish();
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
                Intent intent = new  Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        moreApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.moreApps(SettingScreen.this,"Straps App Studio");
            }
        });
        initInterstitial();
        loadNativeAd();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(Constants.isNetworkAvailable(this)){

            ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Alert");
            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    AppLovinLoading.loadingApplovinBannar(findViewById(R.id.bannerFrameSettings), SettingScreen.this);
                    if(!isFinishing()){
                        progress.dismiss();
                    }
                }
            }, 3000);

        }

    }

    private void initInterstitial() {

        mInterstitialAd = new InterstitialAd(this, getString(R.string.facebook_interstitial));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
                startActivity(new Intent(SettingScreen.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
                finish();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                adfacebook = ad;
//                mInterstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        mInterstitialAd.loadAd(
                mInterstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */

    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).

        nativeAd = new NativeAd(this, getString(R.string.facebook_native_large));

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                rl_native_ad.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private void inflateAd(NativeAd nativeAd) {
        rl_native_ad.setVisibility(View.VISIBLE);
        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(SettingScreen.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_fb_large, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(SettingScreen.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isAdLoaded()) {
            mInterstitialAd.show();
        } else {
            startActivity(new Intent(this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
            finish();

        }

    }

}