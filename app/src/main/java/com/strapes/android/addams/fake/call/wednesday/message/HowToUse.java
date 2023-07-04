package com.strapes.android.addams.fake.call.wednesday.message;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.viewpager.widget.ViewPager;


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen;

import java.util.ArrayList;
import java.util.List;

public class HowToUse extends AppCompatActivity {

    String TAG = "Tag";
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout ladView;

    TextView imgskip, backbuttonhtu;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_use);
        AudienceNetworkAds.initialize(this);

        loadNativeAd();


        viewPager = findViewById(R.id.htu_viewPager);

        List<Integer> livty1 = new ArrayList<>();
        livty1.add(R.drawable.todelectchsrscter);
        livty1.add(R.drawable.tomakeaudiocall);
        livty1.add(R.drawable.tomakevideocall);


        MyAdapter adapter = new MyAdapter(livty1);
        viewPager.setAdapter(adapter);
        imgskip = findViewById(R.id.skip_btn);

        imgskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowToUse.this, HomeScreen.class);
                startActivity(intent);
            }
        });
        backbuttonhtu=findViewById(R.id.back_button);

        backbuttonhtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowToUse.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    private void loadNativeAd() {
        // Instantiate a NativeAd object.
        // NOTE: the placement ID will eventually identify this as your App, you can ignore it for
        // now, while you are testing and replace it later when you have signed up.
        // While you are using this temporary code you will only get test ads and if you release
        // your code like this to the Google Play your users will not receive ads (you will get a no fill error).
        nativeAd = new NativeAd(this, getResources().getString(R.string.Facebook_Native_placement));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        };
        // Request an ad
        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(nativeAdListener).build());
    }

    private void inflateAd(NativeAd nativeAd) {
        nativeAd.unregisterView();
        // Add the Ad view into the ad container.
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(HowToUse.this);
        // Inflate the Ad view. The layout referenced should be the one you created in the last step.
        ladView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(ladView);
        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(HowToUse.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = ladView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = ladView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = ladView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = ladView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = ladView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = ladView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = ladView.findViewById(R.id.native_ad_call_to_action);
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
                ladView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews);

    }
}