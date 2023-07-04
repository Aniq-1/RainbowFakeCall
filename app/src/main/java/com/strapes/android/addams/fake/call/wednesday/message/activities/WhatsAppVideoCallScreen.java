package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

public class WhatsAppVideoCallScreen extends AppCompatActivity implements SurfaceHolder.Callback, MaxAdListener {

    private MaxInterstitialAd applovinInterstitialAd;
    private String str = "";

    private ImageView adduser;
    private LinearLayout atas;
    private LinearLayout bawah;
    private TextView calling;
    Camera camera;
    private RelativeLayout cancel;
    Handler handler;
    private CircleImageView circleImageView;
    MediaPlayer mp;
    private TextView nameuser;
    private RelativeLayout pesan;
    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView;
    SurfaceView surfaceView2;
    private RelativeLayout terima;
    private RelativeLayout tolak;
    VideoView videoView;

    //ads

    private LinearLayout mLyAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.activity_whats_app_video_call_screen);

        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {

        }

        mLyAds = findViewById(R.id.llShowAdsProgress);


        MediaPlayer create = MediaPlayer.create(getApplicationContext(), RingtoneManager.getDefaultUri(1));
        this.mp = create;
        create.start();
        this.mp.setLooping(true);
        this.atas = findViewById(R.id.atas);
        this.bawah = findViewById(R.id.bawah);
        this.videoView = findViewById(R.id.videoView);
        this.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rainbow_video));
        this.videoView.requestFocus();

        this.surfaceView = findViewById(R.id.surfaceView);
        SurfaceView surfaceView = findViewById(R.id.surfaceView2);
        this.surfaceView2 = surfaceView;
        surfaceView.setVisibility(View.GONE);
        SurfaceHolder holder = this.surfaceView.getHolder();
        this.surfaceHolder = holder;
        holder.addCallback(this);
        this.surfaceHolder.setFormat(-1);
        this.surfaceHolder.setType(View.VISIBLE);
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                WhatsAppVideoCallScreen.this.surfaceView.setVisibility(View.VISIBLE);
                float videoWidth = mediaPlayer.getVideoWidth() / mediaPlayer.getVideoHeight();
                float width = WhatsAppVideoCallScreen.this.videoView.getWidth() / WhatsAppVideoCallScreen.this.videoView.getHeight();
                WhatsAppVideoCallScreen.this.surfaceView.getHeight();
                WhatsAppVideoCallScreen.this.surfaceView2.getHeight();
                mediaPlayer.setLooping(true);
                float f = videoWidth / width;
            }
        });
        this.handler = new Handler();
        this.calling = findViewById(R.id.txtcall);
        this.nameuser = findViewById(R.id.txtname);
        this.circleImageView = findViewById(R.id.imguser);
        if (Constant.CHAR_BITMAP != null) {
            this.circleImageView.setImageBitmap(Constant.CHAR_BITMAP);
        } else {
            this.circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.icon_splach_new));
        }

        ImageView imageView = findViewById(R.id.adduser);
        this.adduser = imageView;
        imageView.setVisibility(View.INVISIBLE);
        RelativeLayout relativeLayout = findViewById(R.id.layclose2);
        this.cancel = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "firstEnd";
                WhatsAppVideoCallScreen.this.mp.stop();
                applovinStartAct();
            }
        });
        RelativeLayout relativeLayout2 = findViewById(R.id.laypesan);
        this.pesan = relativeLayout2;
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatsAppVideoCallScreen.this.startActivity(new Intent(WhatsAppVideoCallScreen.this, HomeScreen.class));
                WhatsAppVideoCallScreen.this.finish();
                WhatsAppVideoCallScreen.this.mp.stop();

            }
        });
        RelativeLayout relativeLayout3 = findViewById(R.id.layclose);
        this.tolak = relativeLayout3;
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "secondEnd";
                WhatsAppVideoCallScreen.this.mp.stop();
                applovinStartAct();
            }
        });
        RelativeLayout relativeLayout4 = findViewById(R.id.layterima);
        this.terima = relativeLayout4;
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhatsAppVideoCallScreen.this.mp.stop();
                WhatsAppVideoCallScreen.this.mp.stop();
                WhatsAppVideoCallScreen.this.calling.setVisibility(View.GONE);
                WhatsAppVideoCallScreen.this.nameuser.setVisibility(View.GONE);
                WhatsAppVideoCallScreen.this.circleImageView.setVisibility(View.GONE);
                WhatsAppVideoCallScreen.this.adduser.setVisibility(View.VISIBLE);
                WhatsAppVideoCallScreen.this.surfaceView.setVisibility(View.GONE);
                WhatsAppVideoCallScreen.this.surfaceView2.setVisibility(View.VISIBLE);
                WhatsAppVideoCallScreen wAVideoCallActivity = WhatsAppVideoCallScreen.this;
                wAVideoCallActivity.surfaceHolder = wAVideoCallActivity.surfaceView2.getHolder();
                WhatsAppVideoCallScreen.this.surfaceHolder.addCallback(WhatsAppVideoCallScreen.this);
                WhatsAppVideoCallScreen.this.surfaceHolder.setFormat(-1);
                WhatsAppVideoCallScreen.this.surfaceHolder.setType(View.VISIBLE);
                WhatsAppVideoCallScreen.this.videoView.start();
                WhatsAppVideoCallScreen.this.atas.setVisibility(View.GONE);
                WhatsAppVideoCallScreen.this.bawah.setVisibility(View.VISIBLE);
                WhatsAppVideoCallScreen.this.tolak.setVisibility(View.VISIBLE);
            }
        });
    }



    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Camera open = Camera.open(1);
        this.camera = open;
        this.camera.setParameters(open.getParameters());
        this.camera.setDisplayOrientation(90);
        try {
            this.camera.setPreviewDisplay(surfaceHolder);
            this.camera.startPreview();
        } catch (Exception unused) {
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.camera.stopPreview();
        this.camera.release();
        this.camera = null;
    }

    @Override
    public void onBackPressed() {

        this.mp.stop();
        startActivity(new Intent(this, SelectCallingOptions.class));
        finish();
    }

    private void startAct(){
        if(str.contains("firstEnd")){
            WhatsAppVideoCallScreen.this.startActivity(new Intent(WhatsAppVideoCallScreen.this, HomeScreen.class));
            WhatsAppVideoCallScreen.this.finish();
        }
        else if(str.contains("secondEnd")){
            WhatsAppVideoCallScreen.this.startActivity(new Intent(WhatsAppVideoCallScreen.this, HomeScreen.class));
            WhatsAppVideoCallScreen.this.finish();
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
                ProgressDialog progress = new ProgressDialog(WhatsAppVideoCallScreen.this);
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
        applovinInterstitialAd.setListener(WhatsAppVideoCallScreen.this);
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