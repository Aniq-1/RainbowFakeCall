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
import android.widget.VideoView;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

public class FaceBookVideoCallScreen extends AppCompatActivity  implements SurfaceHolder.Callback, MaxAdListener {

    private MaxInterstitialAd applovinInterstitialAd;
    private String str = "";

    LinearLayout atas;
    LinearLayout bawah;
    TextView calling;
    Camera camera;
    ImageView gambrB;
    CircleImageView gambrH;
    Handler handler;
    ImageView imgback;
    MediaPlayer mp;
    SurfaceHolder surfaceHolder;
    SurfaceView surfaceView;
    RelativeLayout terima;
    RelativeLayout tolak;
    RelativeLayout tolak2;
    VideoView videoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.activity_face_book_video_call_screen);

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

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        this.surfaceView = surfaceView;
        surfaceView.setVisibility(View.GONE);
        SurfaceHolder holder = this.surfaceView.getHolder();
        this.surfaceHolder = holder;
        holder.addCallback(this);
        this.surfaceHolder.setFormat(-1);
        this.surfaceHolder.setType(0);
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        this.videoView = videoView;
        videoView.setMediaController(null);
        this.videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rainbow_video));
        this.videoView.requestFocus();

        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override // android.media.MediaPlayer.OnPreparedListener
            public void onPrepared(MediaPlayer mediaPlayer) {
                float videoWidth = mediaPlayer.getVideoWidth() / mediaPlayer.getVideoHeight();
                float width = FaceBookVideoCallScreen.this.videoView.getWidth() / FaceBookVideoCallScreen.this.videoView.getHeight();
                FaceBookVideoCallScreen.this.surfaceView.getHeight();
                mediaPlayer.setLooping(true);

            }
        });
        this.handler = new Handler();
        this.atas = findViewById(R.id.layutama);
        this.bawah = findViewById(R.id.laybawah2);
        this.calling = (TextView) findViewById(R.id.txtwaktu);
        MediaPlayer create = MediaPlayer.create(FaceBookVideoCallScreen.this, RingtoneManager.getDefaultUri(R.raw.facebook_tune));
        this.mp = create;
        create.start();
        this.mp.setLooping(true);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.laytolak);
        this.tolak = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "firstEnd";
                FaceBookVideoCallScreen.this.mp.stop();
                applovinStartAct();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.imgback2);
        this.imgback = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceBookVideoCallScreen.this.startActivity(new Intent(FaceBookVideoCallScreen.this, HomeScreen.class));
                FaceBookVideoCallScreen.this.finish();
                FaceBookVideoCallScreen.this.mp.stop();
            }
        });
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.laytolak2);
        this.tolak2 = relativeLayout2;
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = "secondEnd";
                FaceBookVideoCallScreen.this.mp.stop();
                applovinStartAct();
            }
        });
        RelativeLayout relativeLayout3 = (RelativeLayout) findViewById(R.id.layterima);
        this.terima = relativeLayout3;
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FaceBookVideoCallScreen.this.mp.stop();
                FaceBookVideoCallScreen.this.surfaceView.setVisibility(View.VISIBLE);
                FaceBookVideoCallScreen.this.atas.setVisibility(View.GONE);
                FaceBookVideoCallScreen.this.bawah.setVisibility(View.VISIBLE);
                FaceBookVideoCallScreen.this.gambrB.setVisibility(View.GONE);
                FaceBookVideoCallScreen.this.videoView.start();
            }
        });
        //((TextView) findViewById(R.id.txtfbname)).setText(FakeAdapter.judul);
        this.gambrH = (CircleImageView) findViewById(R.id.fbimguser);
        this.gambrB = (ImageView) findViewById(R.id.imgback);

        if (Constant.CHAR_BITMAP != null) {
            this.gambrH.setImageBitmap(Constant.CHAR_BITMAP);
            gambrB.setImageBitmap(Constant.MAIN_CHAR_BITMAP);
        } else {
            this.gambrH.setImageDrawable(getResources().getDrawable(R.drawable.icon_splach_new));
            //imguser2.setImageDrawable(getResources().getDrawable(R.drawable.char1));
        }

    }

    @Override
    public void onBackPressed() {
        this.mp.stop();
        startActivity(new Intent(this, SelectCallingOptions.class));
        this.finish();
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

    private void startAct(){
        if(str.contains("firstEnd")){
            FaceBookVideoCallScreen.this.startActivity(new Intent(FaceBookVideoCallScreen.this, HomeScreen.class));
            FaceBookVideoCallScreen.this.finish();
        }
        else if(str.contains("secondEnd")){
            FaceBookVideoCallScreen.this.startActivity(new Intent(FaceBookVideoCallScreen.this, HomeScreen.class));
            FaceBookVideoCallScreen.this.finish();
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
                ProgressDialog progress = new ProgressDialog(FaceBookVideoCallScreen.this);
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
        applovinInterstitialAd.setListener(FaceBookVideoCallScreen.this);
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