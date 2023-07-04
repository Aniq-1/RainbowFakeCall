package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.strapes.android.addams.fake.call.R;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import de.hdodenhof.circleimageview.CircleImageView;

public class SystemCallScreen extends AppCompatActivity {

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
    private ImageView imguser;
    private ImageView imguser2;
    MediaPlayer mp;
    private TextView nameuser;
    private RelativeLayout pesan;
    private RelativeLayout terima;
    private RelativeLayout tolak;
    private RelativeLayout rlMain;
    long UpdateTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(1140850688);
        setContentView(R.layout.activity_system_call_screen);

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

        this.atas = (LinearLayout) findViewById(R.id.atas);
        this.rlMain =  findViewById(R.id.rlMain);
        this.bawah = (LinearLayout) findViewById(R.id.bawah);
        this.calling = (TextView) findViewById(R.id.txtcall);
        this.imguser2 = (ImageView) findViewById(R.id.imguser2);
        ImageView imageView = (ImageView) findViewById(R.id.adduser);
        this.adduser = imageView;
        imageView.setVisibility(View.INVISIBLE);
        this.cancel = (RelativeLayout) findViewById(R.id.layclose2);
        this.tolak = (RelativeLayout) findViewById(R.id.layclose);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.laypesan);
        this.pesan = relativeLayout;
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemCallScreen.this.mp.stop();
                SystemCallScreen.this.startActivity(new Intent(SystemCallScreen.this, SelectCallingOptions.class));
                SystemCallScreen.this.finish();
            }
        });
        this.handler = new Handler();
        this.tolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemCallScreen.this.mp.stop();
                SystemCallScreen.this.startActivity(new Intent(SystemCallScreen.this, SelectCallingOptions.class));
                SystemCallScreen.this.finish();
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemCallScreen.this.mp.stop();
                SystemCallScreen.this.startActivity(new Intent(SystemCallScreen.this, SelectCallingOptions.class));
                SystemCallScreen.this.finish();

            }
        });
        MediaPlayer create = MediaPlayer.create(getApplicationContext(), RingtoneManager.getDefaultUri(1));
        this.mp = create;
        create.start();
        this.mp.setLooping(true);
        this.circleImageView = (CircleImageView) findViewById(R.id.imguser);
        //this.circleImageView = findViewById(R.id.ivUser);

        if(Constant.CHAR_BITMAP!=null){
            this.circleImageView.setImageBitmap(Constant.CHAR_BITMAP);
            this.imguser2.setImageBitmap(Constant.MAIN_CHAR_BITMAP);
        }else{
            this.circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.rainbow_icon_addiloss));
            //this.imguser2.setImageDrawable(getResources().getDrawable(R.drawable.char1));
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.layterima);
        this.terima = relativeLayout2;
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemCallScreen.this.atas.setVisibility(View.GONE);
                SystemCallScreen.this.tolak.setVisibility(View.VISIBLE);
                SystemCallScreen.this.bawah.setVisibility(View.VISIBLE);
                SystemCallScreen.this.imguser2.setVisibility(View.VISIBLE);
                SystemCallScreen.this.StartTime = SystemClock.uptimeMillis();
                SystemCallScreen.this.handler.postDelayed(SystemCallScreen.this.runnable, 0L);

                SystemCallScreen.this.mp.stop();
                try {
                    SystemCallScreen.this.mp = new MediaPlayer();
                    SystemCallScreen.this.mp.stop();
                    SystemCallScreen.this.mp = new MediaPlayer();
                    SystemCallScreen.this.mp = MediaPlayer.create(SystemCallScreen.this, R.raw.burno_voice);
                    SystemCallScreen.this.mp.setLooping(true);
                    SystemCallScreen.this.mp.start();
                } catch (IllegalArgumentException | IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SystemCallScreen.this.MillisecondTime = SystemClock.uptimeMillis() - SystemCallScreen.this.StartTime;
            SystemCallScreen SystemCallActivity = SystemCallScreen.this;
            SystemCallActivity.UpdateTime = SystemCallActivity.TimeBuff + SystemCallScreen.this.MillisecondTime;
            SystemCallScreen SystemCallActivity2 = SystemCallScreen.this;
            SystemCallActivity2.Seconds = (int) (SystemCallActivity2.UpdateTime / 1000);
            SystemCallScreen SystemCallActivity3 = SystemCallScreen.this;
            SystemCallActivity3.Minutes = SystemCallActivity3.Seconds / 60;
            SystemCallScreen.this.Seconds %= 60;
            SystemCallScreen SystemCallActivity4 = SystemCallScreen.this;
            SystemCallActivity4.hours = SystemCallActivity4.Minutes / 60;
            SystemCallScreen SystemCallActivity5 = SystemCallScreen.this;
            SystemCallActivity5.MilliSeconds = (int) (SystemCallActivity5.UpdateTime % 1000);
            SystemCallScreen.this.calling.setText(String.format("%02d", Integer.valueOf(SystemCallScreen.this.hours)) + ":" + String.format("%02d", Integer.valueOf(SystemCallScreen.this.Minutes)) + ":" + String.format("%02d", Integer.valueOf(SystemCallScreen.this.Seconds)));
            SystemCallScreen.this.handler.postDelayed(this, 0L);
        }
    };
}