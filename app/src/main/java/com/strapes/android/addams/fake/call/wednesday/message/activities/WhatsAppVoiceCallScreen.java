package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhatsAppVoiceCallScreen extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd applovinInterstitialAd;
    private String str = "";

    int MilliSeconds;
    long MillisecondTime;
    int Minutes;
    int Seconds;
    long StartTime;
    long TimeBuff;
    private ImageView adduser;
    private LinearLayout atas;
    private LinearLayout bawah;
    private TextView calling;
    private RelativeLayout cancel;
    CircleImageView circleImageView;
    Handler handler;
    int hours;
    private ImageView imguser2;
    MediaPlayer mp;
    private RelativeLayout pesan;
    private RelativeLayout terima;
    private RelativeLayout rlCancelCall;
    long UpdateTime = 0;
    //ads

    private LinearLayout mLyAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.activity_whats_app_voice_call_screen);
        //getSupportActionBar().hide();


        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {

       /*     getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);*/
        }


        mLyAds = findViewById(R.id.llShowAdsProgress);



        this.atas = findViewById(R.id.atas);
        this.bawah = findViewById(R.id.bawah);
        this.calling = findViewById(R.id.txtcall);
        this.imguser2 = findViewById(R.id.imguser2);
        ImageView imageView = findViewById(R.id.adduser);
        this.adduser = imageView;
        imageView.setVisibility(View.INVISIBLE);
        this.cancel = findViewById(R.id.layclose2);
        this.rlCancelCall = findViewById(R.id.layclose);
        RelativeLayout relativeLayout = findViewById(R.id.laypesan);
        this.pesan = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatsAppVoiceCallScreen.this.mp.stop();
                WhatsAppVoiceCallScreen.this.startActivity(new Intent(WhatsAppVoiceCallScreen.this, HomeScreen.class));
                WhatsAppVoiceCallScreen.this.finish();
            }
        });
        this.handler = new Handler();
        this.rlCancelCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "secondEnd";
                WhatsAppVoiceCallScreen.this.mp.stop();
                applovinStartAct();
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "firstEnd";
                WhatsAppVoiceCallScreen.this.mp.stop();
                applovinStartAct();
            }
        });
        MediaPlayer create = MediaPlayer.create(getApplicationContext(), RingtoneManager.getDefaultUri(1));
        this.mp = create;
        create.start();
        this.mp.setLooping(true);
        this.circleImageView = findViewById(R.id.ivUser);

        if (Constant.CHAR_BITMAP != null) {
            this.circleImageView.setImageBitmap(Constant.CHAR_BITMAP);
            imguser2.setImageBitmap(Constant.CHAR_BITMAP);
        } else {
            this.circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_splach_new));
            imguser2.setImageDrawable(getResources().getDrawable(R.drawable.what_icon_img));
        }

        RelativeLayout relativeLayout2 = findViewById(R.id.layterima);
        this.terima = relativeLayout2;
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatsAppVoiceCallScreen.this.atas.setVisibility(View.GONE);
                WhatsAppVoiceCallScreen.this.rlCancelCall.setVisibility(View.VISIBLE);
                WhatsAppVoiceCallScreen.this.bawah.setVisibility(View.VISIBLE);
                WhatsAppVoiceCallScreen.this.imguser2.setVisibility(View.VISIBLE);
                WhatsAppVoiceCallScreen.this.StartTime = SystemClock.uptimeMillis();
                WhatsAppVoiceCallScreen.this.handler.postDelayed(WhatsAppVoiceCallScreen.this.runnable, 0L);
                String str = null;
                WhatsAppVoiceCallScreen.this.mp.stop();
                WhatsAppVoiceCallScreen.this.mp = new MediaPlayer();
                WhatsAppVoiceCallScreen.this.mp = MediaPlayer.create(WhatsAppVoiceCallScreen.this, R.raw.burno_voice);
                WhatsAppVoiceCallScreen.this.mp.setLooping(true);
                WhatsAppVoiceCallScreen.this.mp.start();

            }
        });
    }

    public Runnable runnable = new Runnable() {
        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        @Override
        public void run() {
            WhatsAppVoiceCallScreen.this.MillisecondTime = SystemClock.uptimeMillis() - WhatsAppVoiceCallScreen.this.StartTime;
            WhatsAppVoiceCallScreen wAVoiceCallActivity = WhatsAppVoiceCallScreen.this;
            wAVoiceCallActivity.UpdateTime = wAVoiceCallActivity.TimeBuff + WhatsAppVoiceCallScreen.this.MillisecondTime;
            WhatsAppVoiceCallScreen wAVoiceCallActivity2 = WhatsAppVoiceCallScreen.this;
            wAVoiceCallActivity2.Seconds = (int) (wAVoiceCallActivity2.UpdateTime / 1000);
            WhatsAppVoiceCallScreen wAVoiceCallActivity3 = WhatsAppVoiceCallScreen.this;
            wAVoiceCallActivity3.Minutes = wAVoiceCallActivity3.Seconds / 60;
            WhatsAppVoiceCallScreen.this.Seconds %= 60;
            WhatsAppVoiceCallScreen wAVoiceCallActivity4 = WhatsAppVoiceCallScreen.this;
            wAVoiceCallActivity4.hours = wAVoiceCallActivity4.Minutes / 60;
            WhatsAppVoiceCallScreen wAVoiceCallActivity5 = WhatsAppVoiceCallScreen.this;
            wAVoiceCallActivity5.MilliSeconds = (int) (wAVoiceCallActivity5.UpdateTime % 1000);
            WhatsAppVoiceCallScreen.this.calling.setText(String.format("%02d", Integer.valueOf(WhatsAppVoiceCallScreen.this.hours)) + ":" + String.format("%02d", Integer.valueOf(WhatsAppVoiceCallScreen.this.Minutes)) + ":" + String.format("%02d", Integer.valueOf(WhatsAppVoiceCallScreen.this.Seconds)));
            WhatsAppVoiceCallScreen.this.handler.postDelayed(this, 0L);
        }
    };

    private void startAct(){
        if(str.contains("firstEnd")){
            WhatsAppVoiceCallScreen.this.startActivity(new Intent(WhatsAppVoiceCallScreen.this, HomeScreen.class));
            WhatsAppVoiceCallScreen.this.finish();
        }
        else if(str.contains("secondEnd")){
            WhatsAppVoiceCallScreen.this.startActivity(new Intent(WhatsAppVoiceCallScreen.this, HomeScreen.class));
            WhatsAppVoiceCallScreen.this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        createInterstitialAd();
    }

    private void applovinStartAct(){
        if (Constants.isNetworkAvailable( this)) {
            if (applovinInterstitialAd.isReady()) {
                ProgressDialog progress = new ProgressDialog(WhatsAppVoiceCallScreen.this);
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
        applovinInterstitialAd.setListener(WhatsAppVoiceCallScreen.this);
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