package com.strapes.android.addams.fake.call.wednesday.message.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.facebook.ads.Ad;

import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.material.snackbar.Snackbar;
import com.ncorti.slidetoact.SlideToActView;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.AppLovinLoading;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.BannarApplovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.NativeAppLovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class WelcomeScreen extends AppCompatActivity implements MaxAdListener {

    private android.app.AlertDialog loadind_dialog;
    private MaxInterstitialAd applovinInterstitialAd;
    private Boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(this);

        SlideToActView next_button = (SlideToActView) findViewById(R.id.next_btn);
        next_button.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                    checked = true;
                    applovinStartAct();
            }
        });
    }

    public void startAct() {
        startActivity(new Intent(WelcomeScreen.this, HomeScreen.class));
        finish();
    }

    @SuppressLint("CutPasteId")
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

                createInterstitialAd();
                Constants.layouttype = "bottom";
                Constants.frameLayout = findViewById(R.id.nativeFrameWelcome);
                Constants.nativeframe= findViewById(R.id.nativeFrameWelcome);
                NativeAppLovinProrityAndAdmob.loadingnativeadsWelcome(this,"");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        AppLovinLoading.loadingApplovinBannar(findViewById(R.id.bannerFrameWelcome), WelcomeScreen.this);
                        findViewById(R.id.nativeFrameWelcome).setVisibility(View.VISIBLE);
                        if(!isFinishing()){
                            progress.dismiss();
                        }
                    }
                }, 3000);

            }

        }

    }

    private void applovinStartAct(){
        if (Constants.isNetworkAvailable( this)) {
            if (applovinInterstitialAd.isReady()) {
                ProgressDialog progress = new ProgressDialog(WelcomeScreen.this);
                progress.setTitle("Alert");
                progress.setMessage("Please wait...");
                progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                progress.show();

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress.dismiss();
                        applovinInterstitialAd.showAd();
                    }
                }, 2000);


            } else {
                startAct();
            }
        } else {
            startAct();
        }
    }

    void createInterstitialAd() {
        applovinInterstitialAd = new MaxInterstitialAd(getResources().getString(R.string.applovin_inter_welcome), this);
        applovinInterstitialAd.setListener(WelcomeScreen.this);
        applovinInterstitialAd.loadAd();
    }

    @Override
    public void onAdLoaded(MaxAd ad) {

    }

    @Override
    public void onAdDisplayed(MaxAd ad) {

    }

    @Override
    public void onAdHidden(MaxAd ad) {
        checked = false;
        startAct();
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

}