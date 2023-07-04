package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.appcompat.app.AppCompatActivity;

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


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

public class FaceBookVoiceCallScreen extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd applovinInterstitialAd;
    private String str = "";

    int MilliSeconds;
    private long MillisecondTime;
    private int Minutes;
    private int Seconds;
    private long StartTime;
    long TimeBuff;
    private LinearLayout atas;
    private LinearLayout bawah;
    private TextView calling;
    private ImageView imageView;
    private CircleImageView circleImageView;
    private Handler handler;
    private int hours;
    private ImageView imgback;
    private MediaPlayer mp;
    private RelativeLayout terima;
    private RelativeLayout tolak;
    private RelativeLayout tolak2;
    private long UpdateTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.activity_face_book_voice_call_screen);

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

        this.handler = new Handler();
        this.atas = findViewById(R.id.laybawah1);
        this.bawah = findViewById(R.id.laybawah2);
        this.calling = findViewById(R.id.txtwaktu);
        MediaPlayer create = MediaPlayer.create(this, RingtoneManager.getDefaultUri(R.raw.facebook_tune));
        this.mp = create;
        create.start();
        this.mp.setLooping(true);
        RelativeLayout relativeLayout = findViewById(R.id.laytolak);
        this.tolak = relativeLayout;
        relativeLayout.setOnClickListener(view -> {
            str = "firstEnd";
            FaceBookVoiceCallScreen.this.mp.stop();
            applovinStartAct();
        });
        ImageView imageView = findViewById(R.id.imgback2);
        this.imgback = imageView;
        imageView.setOnClickListener(view -> {
            FaceBookVoiceCallScreen.this.startActivity(new Intent(FaceBookVoiceCallScreen.this, HomeScreen.class));
            FaceBookVoiceCallScreen.this.finish();
            FaceBookVoiceCallScreen.this.mp.stop();
        });
        RelativeLayout relativeLayout2 = findViewById(R.id.laytolak2);
        this.tolak2 = relativeLayout2;
        relativeLayout2.setOnClickListener(view -> {
            str = "secondEnd";
            FaceBookVoiceCallScreen.this.mp.stop();
            applovinStartAct();
        });
        RelativeLayout relativeLayout3 = findViewById(R.id.layterima);
        this.terima = relativeLayout3;
        relativeLayout3.setOnClickListener(view -> {
            FaceBookVoiceCallScreen.this.StartTime = SystemClock.uptimeMillis();
            FaceBookVoiceCallScreen.this.handler.postDelayed(FaceBookVoiceCallScreen.this.runnable, 0L);
            FaceBookVoiceCallScreen.this.atas.setVisibility(View.GONE);
            FaceBookVoiceCallScreen.this.bawah.setVisibility(View.VISIBLE);
            FaceBookVoiceCallScreen.this.mp.stop();
            FaceBookVoiceCallScreen.this.mp = new MediaPlayer();
            FaceBookVoiceCallScreen.this.mp = MediaPlayer.create(FaceBookVoiceCallScreen.this, R.raw.burno_voice);
            FaceBookVoiceCallScreen.this.mp.setLooping(true);
            FaceBookVoiceCallScreen.this.mp.start();
        });

        this.circleImageView = findViewById(R.id.fbimguser);
        this.imageView = findViewById(R.id.imgback);



        if (Constant.CHAR_BITMAP != null) {
            this.circleImageView.setImageBitmap(Constant.CHAR_BITMAP);
            this.imageView.setImageBitmap(Constant.CHAR_BITMAP);
        } else {
            this.circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_splach_new));
            //this.imageView.setImageDrawable(getResources().getDrawable(R.drawable.char1));
        }
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            FaceBookVoiceCallScreen.this.MillisecondTime = SystemClock.uptimeMillis() - FaceBookVoiceCallScreen.this.StartTime;
            FaceBookVoiceCallScreen fBVoiceCallActivity = FaceBookVoiceCallScreen.this;
            fBVoiceCallActivity.UpdateTime = fBVoiceCallActivity.TimeBuff + FaceBookVoiceCallScreen.this.MillisecondTime;
            FaceBookVoiceCallScreen fBVoiceCallActivity2 = FaceBookVoiceCallScreen.this;
            fBVoiceCallActivity2.Seconds = (int) (fBVoiceCallActivity2.UpdateTime / 1000);
            FaceBookVoiceCallScreen fBVoiceCallActivity3 = FaceBookVoiceCallScreen.this;
            fBVoiceCallActivity3.Minutes = fBVoiceCallActivity3.Seconds / 60;
            FaceBookVoiceCallScreen.this.Seconds %= 60;
            FaceBookVoiceCallScreen fBVoiceCallActivity4 = FaceBookVoiceCallScreen.this;
            fBVoiceCallActivity4.hours = fBVoiceCallActivity4.Minutes / 60;
            FaceBookVoiceCallScreen fBVoiceCallActivity5 = FaceBookVoiceCallScreen.this;
            fBVoiceCallActivity5.MilliSeconds = (int) (fBVoiceCallActivity5.UpdateTime % 1000);
            FaceBookVoiceCallScreen.this.calling.setText(String.format("%02d", Integer.valueOf(FaceBookVoiceCallScreen.this.hours)) + ":" + String.format("%02d", Integer.valueOf(FaceBookVoiceCallScreen.this.Minutes)) + ":" + String.format("%02d", Integer.valueOf(FaceBookVoiceCallScreen.this.Seconds)));
            FaceBookVoiceCallScreen.this.handler.postDelayed(this, 0L);
        }
    };

    @Override
    public void onBackPressed() {
        this.mp.stop();
        startActivity(new Intent(this, SelectCallingOptions.class));
        finish();
    }

    private void startAct(){
        if(str.contains("firstEnd")){
            FaceBookVoiceCallScreen.this.startActivity(new Intent(FaceBookVoiceCallScreen.this, HomeScreen.class));
            FaceBookVoiceCallScreen.this.finish();
        }
        else if(str.contains("secondEnd")){
            FaceBookVoiceCallScreen.this.startActivity(new Intent(FaceBookVoiceCallScreen.this, HomeScreen.class));
            FaceBookVoiceCallScreen.this.finish();
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
                ProgressDialog progress = new ProgressDialog(FaceBookVoiceCallScreen.this);
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
        applovinInterstitialAd.setListener(FaceBookVoiceCallScreen.this);
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