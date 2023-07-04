package com.strapes.android.addams.fake.call.wednesday.message.activities


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.strapes.android.addams.fake.call.R
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.AppLovinLoading
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants
import com.strapes.android.addams.fake.call.wednesday.message.adapter.MyViewPagerAdapter
import com.strapes.android.addams.fake.call.wednesday.message.utils.SharedPref
import me.relex.circleindicator.CircleIndicator3


class PrivacyScreen : AppCompatActivity(), MaxAdListener {

    private var applovinInterstitialAd: MaxInterstitialAd? = null
    private var checked = false

    var user: SharedPref? = null

    var viewpager2: ViewPager2? = null
    var circleIndicator3: CircleIndicator3? = null
    private var sharedPref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    private var str:String = ""

    companion object {
        var skip: TextView? = null
        var accept: TextView? = null



        public fun buttonenable() {
            skip!!.visibility = View.GONE
            accept!!.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_screen)
        supportActionBar!!.hide()

        user = SharedPref(this)
        // dm Stands for Detect Metal
        viewpager2 = findViewById(R.id.view_pager)
        circleIndicator3 = findViewById(R.id.circle_indicator)
        //checkBox = findViewById(R.id.checkbox)
        skip = findViewById(R.id.skip)
        accept = findViewById(R.id.btn_accept)
        sharedPref = getSharedPreferences("privacy_prefrs", Context.MODE_PRIVATE)
        editor = sharedPref!!.edit()

        var adapter = MyViewPagerAdapter(this)
        viewpager2!!.adapter = adapter
        val indicator = findViewById<CircleIndicator3>(R.id.circle_indicator)
        indicator.setViewPager(viewpager2)

        skip!!.setOnClickListener {
            str = "skip"
            applovinStartAct()
        }

        accept!!.setOnClickListener {
            checked = true
            str = "accept"
            applovinStartAct()
        }

    }

    private fun started() {
        if (user!!.getStoreValue().equals("true")) {
            startActivity(Intent(this, SettingScreen::class.java))
            finish()
        } else {
            user?.setStoreValue("true")
            editor!!.putBoolean("PrivacySelection", true);
            editor!!.apply();
            sharedPref!!.edit().putBoolean("firstTimeclog_prefrs", false).apply()
            startActivity(Intent(this, WelcomeScreen::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Toast.makeText(this,"Accept privacy first", Toast.LENGTH_LONG).show();

        if (user!!.getStoreValue().equals("true")) {
            startActivity(Intent(this, SettingScreen::class.java))
            finish()
        } else {
            finish()
        }
    }

    @SuppressLint("CutPasteId")
    override fun onResume() {
        super.onResume()
        if (checked) {
        } else {
            if (Constants.isNetworkAvailable(this)) {
                val progress = ProgressDialog(this)
                progress.setTitle("Alert")
                progress.setMessage("Please wait...")
                progress.setCancelable(false)
                progress.show()
                createInterstitialAd()
                val handler = Handler()
                handler.postDelayed({
                    if (!isFinishing) {
                        progress.dismiss()
                    }
                }, 3000)
            }
        }
    }

    private fun startAct(){

        if(str.contains("skip")){
            accept!!.visibility = View.VISIBLE
            viewpager2!!.currentItem = viewpager2!!.currentItem + 2
            skip!!.visibility = View.GONE
        }
        else if(str.contains("accept")){
            started()
            user!!.isPolicyRead("yes", "yes")
        }
    }

    private fun applovinStartAct() {
        if (Constants.isNetworkAvailable(this)) {
            if (applovinInterstitialAd!!.isReady) {
                val progress = ProgressDialog(this@PrivacyScreen)
                progress.setTitle("Alert")
                progress.setMessage("Please wait...")
                progress.setCancelable(false) // disable dismiss by tapping outside of the dialog
                progress.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    progress.dismiss()
                    applovinInterstitialAd!!.showAd()
                }, 2000)
            } else {
                startAct()
            }
        } else {
            startAct()
        }
    }

    fun createInterstitialAd() {
        applovinInterstitialAd =
            MaxInterstitialAd(resources.getString(R.string.applovin_inter), this)
        applovinInterstitialAd!!.setListener(this@PrivacyScreen)
        applovinInterstitialAd!!.loadAd()
    }

    override fun onAdLoaded(ad: MaxAd?) {}

    override fun onAdDisplayed(ad: MaxAd?) {}

    override fun onAdHidden(ad: MaxAd?) {
        checked = false
        startAct()
    }

    override fun onAdClicked(ad: MaxAd?) {}

    override fun onAdLoadFailed(adUnitId: String?, error: MaxError?) {}

    override fun onAdDisplayFailed(ad: MaxAd?, error: MaxError?) {}

}