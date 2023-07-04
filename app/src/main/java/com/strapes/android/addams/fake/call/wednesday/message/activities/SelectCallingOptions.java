package com.strapes.android.addams.fake.call.wednesday.message.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.material.snackbar.Snackbar;
import com.strapes.android.addams.fake.call.R;


import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.AppLovinLoading;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.BannarApplovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants;
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.NativeAppLovinProrityAndAdmob;
import com.strapes.android.addams.fake.call.wednesday.message.utils.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelectCallingOptions<Int> extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd applovinInterstitialAd;
    private Boolean checked = false;
    private String str = "";

    protected static final String TAG = "SelectCallingOption";
    public static String status_time = "Wait for 2 seconds";
    public static int rd_form = 1;
    public static int rd_time = 1;
    public static int rd_vid = 1;
    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 134;
    private Button back_button;

    TextView tittle,callType,callType_2;
    ImageView image_type,image_type_2;
    LinearLayout start_call_button,callButton;
        int i=1;
    private RadioGroup list_template;
    private RadioGroup list_time;
    private RadioButton radio_facebook_btn,radio_system_call_btn;
    RadioGroup radioGroup2,radioGroup3;

    String [] arrCallType = new String[]{"Whatsapp","Facebook"};
    String [] arrSetTimer = new String[]{"Now","10","30","60","300"};



    private RelativeLayout rl_native_ad;
    private NativeAdLayout nativeAdLayout;
    private LinearLayout adView;
    private NativeAd nativeAd;

    Ad adfacebook;
    private android.app.AlertDialog loadind_dialog;
    private InterstitialAd mInterstitialAd;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_calling_options);

        BannarApplovinProrityAndAdmob.initBannarPreloadingApplovin(this);

        // Getting all the ids
        findIds();

        // Back Button Adds loading stuff
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Checking weather VideoCall or Voice call was selected from home screen
        checkingCallType();

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_facebook_button:
                        counter ++;
                        str = "facebookCall";
                        if(counter == 2){
                            counter = 0;
                            applovinStartAct();
                        }
                        else{
                            startAct();
                        }
                        break;
                    case R.id.radio_whatsapp_button:
                        counter ++;
                        str = "whatsappCall";
                        if(counter == 2){
                            counter = 0;
                            applovinStartAct();
                        }
                        else{
                            startAct();
                        }
                        break;
                    default:
                        return;
                }
            }
        });
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_1:
                        SelectCallingOptions.rd_time = 1;
                        SelectCallingOptions.status_time = "Wait for 2 seconds";
                        return;
                    case R.id.radio_10:
                        SelectCallingOptions.rd_time = Constant.TIMER_A;
                        SelectCallingOptions.status_time = "Wait for 10 seconds";
                        return;
                    case R.id.radio_30:
                        SelectCallingOptions.rd_time = Constant.TIMER_B;
                        SelectCallingOptions.status_time = "Wait for 30 seconds";
                        return;
                    case R.id.radio_300:
                        SelectCallingOptions.rd_time = Constant.TIMER_D;
                        SelectCallingOptions.status_time = "Wait for 5 minutes";
                        return;
                    case R.id.radio_60:
                        SelectCallingOptions.rd_time = Constant.TIMER_C;
                        SelectCallingOptions.status_time = "Wait for 1 minutes";
                        return;
                    default:
                        return;
                }
            }
        });


        //Passing Intent to next Activity to schedule a call
        this.pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE, new Intent(this, com.strapes.android.addams.fake.call.wednesday.message.receiver.CallingReceiver.class), PendingIntent.FLAG_IMMUTABLE);
        start_call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioGroup2.getCheckedRadioButtonId() == -1 && radioGroup3.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SelectCallingOptions.this, "Please select Template & Timer", Toast.LENGTH_SHORT).show();
                }
                else if(radioGroup2.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SelectCallingOptions.this, "Please select Template", Toast.LENGTH_SHORT).show();
                }else if(radioGroup3.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SelectCallingOptions.this, "Please select Timer", Toast.LENGTH_SHORT).show();
                }
                else{
                    str = "startCall";
                    checked = true;
                    applovinStartAct();
                }
            }
        });
        initInterstitial();
        loadNativeAd();
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
                startActivity(new Intent(SelectCallingOptions.this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
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
        nativeAdLayout = findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(SelectCallingOptions.this);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_fb_large, nativeAdLayout, false);
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(SelectCallingOptions.this, nativeAd, nativeAdLayout);
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




    private void checkingCallType() {

        int imageResourceVideo = getResources().getIdentifier("@drawable/new_video_call", null, getPackageName());
        int imageResourceVoice = getResources().getIdentifier("@drawable/new_voice_call", null, getPackageName());
        Drawable resVideo = getResources().getDrawable(imageResourceVideo);
        Drawable resVoice = getResources().getDrawable(imageResourceVoice);

        if (Constant.IS_VIDEO){
            Constant.IS_VIDEO = false;
            SelectCallingOptions.rd_vid = 1;
            tittle.setText("Video Call");
            callType.setText("Video Call");
            callType_2.setText("Start Video Call");
            image_type.setImageDrawable(resVideo);
            image_type_2.setImageDrawable(resVideo);
//            callButton.setText(" Start Video Call");
        } else if (Constant.IS_VOICE) {
            Constant.IS_VOICE = false;
            SelectCallingOptions.rd_vid = 2;
            tittle.setText("Voice Call");
            callType.setText("Voice Call");
            callType_2.setText("Start Voice Call");
            image_type.setImageDrawable(resVoice);
            image_type_2.setImageDrawable(resVoice);
//            callButton.setText(" Start Voice Call");
        }else {}

    }

    public void findIds() {
        callType = findViewById(R.id.call_type);
        callType_2 = findViewById(R.id.selected_call_text);
        back_button = findViewById(R.id.back_button);
        start_call_button = findViewById(R.id.start_call_btn);
        tittle = (TextView) findViewById(R.id.text_Tittle);
        image_type = findViewById(R.id.calling_image);
        image_type_2 = findViewById(R.id.img_type_call);
        rl_native_ad = findViewById(R.id.rl_native_ad);
        rl_native_ad.setVisibility(View.GONE);

        radioGroup2 = findViewById(R.id.list_Template);
        list_template = radioGroup2;

        radioGroup3 = findViewById(R.id.list_time);
        list_time = radioGroup3;
    }

    private void CheckForNetwork() {

    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isAdLoaded()) {
            mInterstitialAd.show();
        } else {
            startActivity(new Intent(this, com.strapes.android.addams.fake.call.wednesday.message.activities.HomeScreen.class));
            finish();
        }

    }

    private void startAct(){

        if(str.contains("facebookCall")){
            SelectCallingOptions.rd_form = 2;
        }
        else if(str.contains("whatsappCall")){
            SelectCallingOptions.rd_form = 1;
        }
        else if(str.contains("startCall")){

            if (radioGroup2.getCheckedRadioButtonId() != -1 && radioGroup3.getCheckedRadioButtonId() != -1) {
                if (SelectCallingOptions.rd_time == 1) {
                    if (SelectCallingOptions.rd_form == 1) {
                        if (SelectCallingOptions.rd_vid == 2) {
                            Intent intent = new Intent(SelectCallingOptions.this, WhatsAppVoiceCallScreen.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent);
                            SelectCallingOptions.this.finish();
                            Log.d("CHK1", "onClick: " + "voice call");
                            return;
                        } else if (SelectCallingOptions.rd_vid == 1) {
                            Log.d("CHK1", "onClick: " + "video call");
                            Intent intent2 = new Intent(SelectCallingOptions.this, WhatsAppVideoCallScreen.class);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent2);
                            SelectCallingOptions.this.finish();
                            return;
                        } else {
                            Intent intent3 = new Intent(SelectCallingOptions.this, WhatsAppVideoCallScreen.class);
                            intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent3);
                            SelectCallingOptions.this.finish();
                            return;
                        }
                    } else if (SelectCallingOptions.rd_form == 2) {
                        if (SelectCallingOptions.rd_vid == 2) {
                            Intent intent4 = new Intent(SelectCallingOptions.this, FaceBookVoiceCallScreen.class);
                            intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent4);
                            SelectCallingOptions.this.finish();
                            return;
                        } else if (SelectCallingOptions.rd_vid == 1) {
                            Intent intent5 = new Intent(SelectCallingOptions.this, FaceBookVideoCallScreen.class);
                            intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent5);
                            SelectCallingOptions.this.finish();
                            return;
                        } else {
                            Intent intent6 = new Intent(SelectCallingOptions.this, FaceBookVideoCallScreen.class);
                            intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent6);
                            SelectCallingOptions.this.finish();
                            return;
                        }
                    } else if (SelectCallingOptions.rd_form != 3) {
                        return;
                    } else {
                        if (SelectCallingOptions.rd_vid == 2) {
                            Intent intent7 = new Intent(SelectCallingOptions.this, SystemCallScreen.class);
                            intent7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent7);
                            SelectCallingOptions.this.finish();
                            return;
                        } else if (SelectCallingOptions.rd_vid == 1) {
                            Intent intent8 = new Intent(SelectCallingOptions.this, FaceBookVideoCallScreen.class);
                            intent8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent8);
                            SelectCallingOptions.this.finish();
                            return;
                        } else {
                            Intent intent9 = new Intent(SelectCallingOptions.this, FaceBookVideoCallScreen.class);
                            intent9.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            SelectCallingOptions.this.startActivity(intent9);
                            SelectCallingOptions.this.finish();
                            return;
                        }
                    }
                }

                Calendar calendar = Calendar.getInstance();
                calendar.add(13, SelectCallingOptions.rd_time);
                //((AlarmManager) MainActivity.this.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(0, calendar.getTimeInMillis(), MainActivity.this.pendingIntent);
                ((AlarmManager) SelectCallingOptions.this.getSystemService(Context.ALARM_SERVICE)).set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), SelectCallingOptions.this.pendingIntent);
                Toast.makeText(SelectCallingOptions.this, SelectCallingOptions.status_time, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                SelectCallingOptions.this.finish();
            }else {
                if(radioGroup2.getCheckedRadioButtonId() == -1 && radioGroup3.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SelectCallingOptions.this, "Please select Template & Timer", Toast.LENGTH_SHORT).show();
                }
                else if(radioGroup2.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SelectCallingOptions.this, "Please select Template", Toast.LENGTH_SHORT).show();
                }else if(radioGroup3.getCheckedRadioButtonId() == -1){
                    Toast.makeText(SelectCallingOptions.this, "Please select Timer", Toast.LENGTH_SHORT).show();
                }
            }

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
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        AppLovinLoading.loadingApplovinBannar(findViewById(R.id.bannerFrameCall), SelectCallingOptions.this);
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
                ProgressDialog progress = new ProgressDialog(SelectCallingOptions.this);
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
        applovinInterstitialAd.setListener(SelectCallingOptions.this);
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