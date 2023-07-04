package com.strapes.android.addams.fake.call.wednesday.message.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.AppLovinLoading;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.BannarApplovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.NativeAppLovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import androidx.appcompat.app.AppCompatActivity;

public class CharacterSelect extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd applovinInterstitialAd;
    private Boolean checked = false;

    private final String TAG = CharacterSelect.class.getSimpleName();
    Button back_button;
    RelativeLayout character_1, character_2, character_3, character_4, character_5, character_6, character_7, character_8;

    String str = "";
    Ad adfacebook;
    private android.app.AlertDialog loadind_dialog;
    private InterstitialAd mInterstitialAd;

    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(this);

        back_button = findViewById(R.id.back_button_a);

        initialization();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        character_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                str = "char1";
                showCharacterDialog("Wednesday Adams character selected!");
            }
        });
        character_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                str = "char2";
                showCharacterDialog("Christina Ricci character selected!");
            }
        });
        character_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                str = "char3";
                showCharacterDialog("Rachel Potter character selected!");
            }
        });
        character_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                str = "char4";
                showCharacterDialog("Jenna Ortega character selected!");
            }
        });
        character_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                str = "char5";
                showCharacterDialog("Lisa Loring character selected!");
            }
        });
        character_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                str = "char6";
                showCharacterDialog("Debbi Derryberry character selected!");
            }
        });
        initInterstitial();
    }

    private void showCharacterDialog(String characterName) {
        findViewById(R.id.overlay_view).setVisibility(View.VISIBLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.custom_popup, null);
        builder.setView(dialogView);
        TextView characterNameTextView = dialogView.findViewById(R.id.character_name);
        Button closeButton = dialogView.findViewById(R.id.close_button);
        characterNameTextView.setText(characterName);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Remove overlay when dialog is dismissed
                findViewById(R.id.overlay_view).setVisibility(View.GONE);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                applovinStartAct();
            }
        });
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
                startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
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

    private void initialization() {
        character_1 = findViewById(R.id.char_1);
        character_2 = findViewById(R.id.char_2);
        character_3 = findViewById(R.id.char_3);
        character_4 = findViewById(R.id.char_4);
        character_5 = findViewById(R.id.char_5);
        character_6 = findViewById(R.id.char_6);

    }


    @Override
    public void onBackPressed() {
        str = "selected";
        if (mInterstitialAd.isAdLoaded()) {
            mInterstitialAd.show();
        } else {
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
            finish();
        }

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
                Constants.frameLayout = findViewById(R.id.nativeFrameCharSelection);
                Constants.nativeframe= findViewById(R.id.nativeFrameCharSelection);
                NativeAppLovinProrityAndAdmob.loadingnativeads(this,"");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        AppLovinLoading.loadingApplovinBannar(findViewById(R.id.bannerFrameCharSelection), CharacterSelect.this);
                        findViewById(R.id.nativeFrameCharSelection).setVisibility(View.VISIBLE);
                        if(!isFinishing()){
                            progress.dismiss();
                        }
                    }
                }, 3000);

            }

        }

    }

    private void startAct(){

        if(str.contains("char1")){
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.new_1);
            Constant.CHAR_BITMAP = bitmap;
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
        }
        else if(str.contains("char2")){
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.new_2);
            Constant.CHAR_BITMAP = bitmap;
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
        }
        else if(str.contains("char3")){
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.new_3);
            Constant.CHAR_BITMAP = bitmap;
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
        }
        else if(str.contains("char4")){
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.new_4);
            Constant.CHAR_BITMAP = bitmap;
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
        }
        else if(str.contains("char5")){
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.new_5);
            Constant.CHAR_BITMAP = bitmap;
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
        }
        else if(str.contains("char6")){
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.new_6);
            Constant.CHAR_BITMAP = bitmap;
            startActivity(new Intent(CharacterSelect.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
        }
    }

    private void applovinStartAct(){
        if (Constants.isNetworkAvailable( this)) {
            if (applovinInterstitialAd.isReady()) {
                ProgressDialog progress = new ProgressDialog(CharacterSelect.this);
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
        applovinInterstitialAd = new MaxInterstitialAd(getResources().getString(R.string.applovin_inter), this);
        applovinInterstitialAd.setListener(CharacterSelect.this);
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

