package com.strapes.android.addams.fake.call.wednesday.message.activities;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.material.snackbar.Snackbar;
import com.strapes.android.addams.fake.call.R;

import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.AppLovinLoading;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.BannarApplovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.NativeAppLovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.HowToUse;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeScreen extends AppCompatActivity {

    ImageView imageView;
    private final String TAG = HomeScreen.class.getSimpleName();
    private Boolean checked = false;

    LinearLayout voice_call_button, video_call_button, characterSelect, settings;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    private static final int MY_REQUEST_CODE = 17326;
    private boolean isBackPressed = false;
    private View bottom_sheet;
    private TextView exit;
    private BottomSheetBehavior mBehavior;

    String str = null;


    private RelativeLayout rl_native_ad, rl_native_ad_bottom;
    private NativeAd nativeAd, nativeAdBottom;

    Ad adfacebook;
    private android.app.AlertDialog loadind_dialog;
    private InterstitialAd mInterstitialAd;
    private int adFlag = 0;

    private int i = 1;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onStart() {
        super.onStart();
        i = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(this);

        takePermission();
        //initialization of buttons
        initialization();
        initiate_bottoms_heet_dialog();

        imageView =findViewById(R.id.howtousebutton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, HowToUse.class);
                startActivity(intent);
            }
        });




        voice_call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                if (mInterstitialAd.isAdLoaded()) {
                    adFlag = 1;
                    mInterstitialAd.show();
                } else {
                    Constant.IS_VOICE = true;
                    str = "voiceCall";
                    disablebutton();
                    if (i == 1) {
                        i = 2;
                        enablebutton();
                        startAct();
                    }
                }


            }
        });

        video_call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                if (mInterstitialAd.isAdLoaded()) {
                    adFlag = 2;
                    mInterstitialAd.show();
                } else {
                    Constant.IS_VIDEO = true;
                    str = "videoCall";
                    disablebutton();
                    if (i == 1) {
                        i = 2;
                        enablebutton();
                        startAct();
                    }
                }

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                if (mInterstitialAd.isAdLoaded()) {
                    adFlag = 3;
                    mInterstitialAd.show();
                } else {
                    str = "settings";
                    startActivity(new Intent(HomeScreen.this, SettingScreen.class));
                    finish();
                }
            }
        });

        characterSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                if (mInterstitialAd.isAdLoaded()) {
                    adFlag = 4;
                    mInterstitialAd.show();
                } else {
                    str = "character";
                    disablebutton();
                    if (i == 1) {
                        i = 2;
                        enablebutton();
                        startAct();
                    }
                }

            }
        });
        initInterstitial();
        refreshAd();
        loadNativeAdBottom();

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
//                checked = false;
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
                if (adFlag == 1) {
                    Constant.IS_VOICE = true;
                    str = "voiceCall";
                    disablebutton();
                    if (i == 1) {
                        i = 2;
                        enablebutton();
                        startAct();
                    }
                } else if (adFlag == 2) {
                    Constant.IS_VIDEO = true;
                    str = "videoCall";
                    disablebutton();
                    if (i == 1) {
                        i = 2;
                        enablebutton();
                        startAct();
                    }
                } else if (adFlag == 3) {
                    str = "settings";
                    startActivity(new Intent(HomeScreen.this, SettingScreen.class));
                    finish();
                } else if (adFlag == 4) {
                    str = "character";
                    disablebutton();
                    if (i == 1) {
                        i = 2;
                        enablebutton();
                        startAct();
                    }
                }
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
     * Populates a {@link NativeAdView} object with data from a given {@link NativeAd}.
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView   the view to be populated
     */
    private void populateNativeAdView(com.google.android.gms.ads.nativead.NativeAd nativeAd, NativeAdView adView) {
        // Set the media view.
        adView.setMediaView((com.google.android.gms.ads.nativead.MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every NativeAd.
        ((TextView) Objects.requireNonNull(adView.getHeadlineView())).setText(nativeAd.getHeadline());
        Objects.requireNonNull(adView.getMediaView()).setMediaContent(Objects.requireNonNull(nativeAd.getMediaContent()));

        // These assets aren't guaranteed to be in every NativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getBodyView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getCallToActionView()).setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            Objects.requireNonNull(adView.getIconView()).setVisibility(View.GONE);
        } else {
            ((ImageView) Objects.requireNonNull(adView.getIconView())).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getPriceView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.INVISIBLE);
        } else {
            Objects.requireNonNull(adView.getStoreView()).setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            Objects.requireNonNull(adView.getStarRatingView()).setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) Objects.requireNonNull(adView.getStarRatingView()))
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            Objects.requireNonNull(adView.getAdvertiserView()).setVisibility(View.INVISIBLE);
        } else {
            ((TextView) Objects.requireNonNull(adView.getAdvertiserView())).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getMediaContent().getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {


            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
//                    refresh.setEnabled(true);
//                    videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd();
                }
            });
        } else {
//            videoStatus.setText("Video status: Ad does not contain a video asset.");
//            refresh.setEnabled(true);
        }
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */
    private void refreshAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.admob_native_large));
//        AdLoader.Builder builder = new AdLoader.Builder(activity, "ca-app-pub-3940256099942544/2247696110");

        builder.forNativeAd(
                new com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull com.google.android.gms.ads.nativead.NativeAd nativeAd) {
                        // If this callback occurs after the activity is destroyed, you must call
                        // destroy and return or you may get a memory leak.

                        // You must call destroy on old ads when you are done with them,
                        // otherwise you will have a memory leak.
//                        if (nativeAd_admob != null) {
//                            nativeAd_admob.destroy();
//                        }
//                        nativeAd_admob = nativeAd;
                        @SuppressLint("InflateParams") NativeAdView adView =
                                (NativeAdView) getLayoutInflater().inflate(R.layout.native_admob_large, null);
                        populateNativeAdView(nativeAd, adView);
//                        rl_native_ad.removeAllViews();
                        rl_native_ad.setVisibility(View.VISIBLE);
                        rl_native_ad.addView(adView);
                    }
                });

        VideoOptions videoOptions =
                new VideoOptions.Builder().setStartMuted(true).build();

        NativeAdOptions adOptions =
                new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader =
                builder
                        .withAdListener(
                                new com.google.android.gms.ads.AdListener() {
                                    @Override
                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                                        rl_native_ad.removeAllViews();
//                                        rl_native_ad.setVisibility(View.GONE);
                                        loadNativeAd();
                                    }
                                })
                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());
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
        NativeAdLayout nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(HomeScreen.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.native_ad_fb_large, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(HomeScreen.this, nativeAd, nativeAdLayout);
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

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     */

    private void loadNativeAdBottom() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).

        nativeAdBottom = new NativeAd(this, getString(R.string.facebook_native_large_exit));

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                rl_native_ad_bottom.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Race condition, load() called again before last ad was displayed
                if (nativeAdBottom == null || nativeAdBottom != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAdBottom(nativeAdBottom);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // Request an ad
        nativeAdBottom.loadAd(
                nativeAdBottom.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private void inflateAdBottom(NativeAd nativeAdBottom) {
        rl_native_ad_bottom.setVisibility(View.VISIBLE);
        nativeAdBottom.unregisterView();

        // Add the Ad view into the ad container.
        NativeAdLayout nativeAdLayoutBottom = bottomSheetDialog.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(HomeScreen.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        LinearLayout adViewBottom = (LinearLayout) inflater.inflate(R.layout.native_ad_fb_large, nativeAdLayoutBottom, false);
        nativeAdLayoutBottom.addView(adViewBottom);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = bottomSheetDialog.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(HomeScreen.this, nativeAdBottom, nativeAdLayoutBottom);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adViewBottom.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adViewBottom.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adViewBottom.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adViewBottom.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adViewBottom.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adViewBottom.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adViewBottom.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAdBottom.getAdvertiserName());
        nativeAdBody.setText(nativeAdBottom.getAdBodyText());
        nativeAdSocialContext.setText(nativeAdBottom.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAdBottom.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAdBottom.getAdCallToAction());
        sponsoredLabel.setText(nativeAdBottom.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAdBottom.registerViewForInteraction(
                adViewBottom, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    private void initialization() {

        voice_call_button = findViewById(R.id.voiceCall);
        video_call_button = findViewById(R.id.videoCall);
        settings = findViewById(R.id.settings);
        characterSelect = findViewById(R.id.characterSelect);
        rl_native_ad = findViewById(R.id.rl_native_ad);
        rl_native_ad.setVisibility(View.GONE);


    }

    void enablebutton() {
        voice_call_button.setEnabled(true);
        video_call_button.setEnabled(true);
        settings.setEnabled(true);
        characterSelect.setEnabled(true);
    }

    void disablebutton() {
        voice_call_button.setEnabled(false);
        video_call_button.setEnabled(false);
        settings.setEnabled(false);
        characterSelect.setEnabled(false);
    }

    void startAct() {


        if (str.contains("videoCall")) {
            startActivity(new Intent(HomeScreen.this, SelectCallingOptions.class));
            //finish();
        } else if (str.contains("voiceCall")) {
            startActivity(new Intent(HomeScreen.this, SelectCallingOptions.class));
            //finish();
        } else if (str.contains("settings")) {
            startActivity(new Intent(HomeScreen.this, SettingScreen.class));
            finish();
        } else if (str.contains("character")) {
            startActivity(new Intent(HomeScreen.this, CharacterSelect.class));
            finish();
        }
    }

    private void takePermission() {
        if (Build.VERSION.SDK_INT >= 28 && !Settings.canDrawOverlays(this)) {
            checkPermission();
        }
        if (Build.VERSION.SDK_INT < 26) {
            new WindowManager.LayoutParams(-2, -2, 2002, 40, -2);
        }
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this)) {
            return;
        }
        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE && !Settings.canDrawOverlays(this)) {
            checkPermission();
        }
        if (i == MY_REQUEST_CODE) {
            if (i2 == -1) {
                if (i2 == -1) {
                    return;
                }
                Log.d("RESULT_OK  :", "" + i2);
            } else if (i2 == 0) {
                if (i2 == 0) {
                    return;
                }
                Log.d("RESULT_CANCELED  :", "" + i2);
            } else if (i2 != 1 || i2 == 1) {
            } else {
                Log.d("RESULT_IN_APP_FAILED:", "" + i2);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(checked){

        }
        else{

            if(Constants.isNetworkAvailable(this)){

                ProgressDialog progress = new ProgressDialog(this);
                progress.setTitle("Alert");
                progress.setMessage("Please wait...");
                progress.setCancelable(false);
                progress.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        AppLovinLoading.loadingApplovinBannar(findViewById(R.id.bannerFrameMain), HomeScreen.this);
                        if(!isFinishing()){
                            progress.dismiss();
                        }
                    }
                }, 3000);

            }

        }


    }

    @Override
    public void onBackPressed() {
        if(Constants.isNetworkAvailable(this)){
            startActivity(new Intent(this,ExitScreen.class));
        }
        else {
            finishAffinity();
        }
    }

    private void initiate_bottoms_heet_dialog() {
        //isBackPressed = true;
        bottomSheetDialog = new BottomSheetDialog(HomeScreen.this);


        bottomSheetDialog.setCancelable(true);
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // isBackPressed = false;

            }
        });
        bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet);
        rl_native_ad_bottom = bottomSheetDialog.findViewById(R.id.rl_native_ad);
    }

    private void show_bottoms_heet_dialog() {

        bottomSheetDialog.show();
        exit = bottomSheetDialog.findViewById(R.id.txt_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                finishAffinity();
            }
        });
    }

}