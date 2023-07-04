package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.strapes.android.addams.fake.call.R;


import com.strapes.android.addams.fake.call.wednesday.message.utils.SharedPref;

public class SplashScreen extends AppCompatActivity {
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sharedPref = new SharedPref(this);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPref.getPolicyRead("yes").equals("yes")){
                    startActivity(new Intent(getApplicationContext(), com.strapes.android.addams.fake.call.wednesday.message.activities.WelcomeScreen.class));
                    finish();
                } else{
                    startActivity(new Intent(getApplicationContext(), PrivacyScreen.class));
                    finish();
                }
            }
        },3000);
    }
}