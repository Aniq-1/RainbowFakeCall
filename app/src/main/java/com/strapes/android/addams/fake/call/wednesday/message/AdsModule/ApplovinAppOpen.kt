package com.strapes.android.addams.fake.call.wednesday.message.AdsModule

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxAppOpenAd
import com.applovin.sdk.AppLovinSdk
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.strapes.android.addams.fake.call.R

class ApplovinAppOpen : Application() {
    private lateinit var appOpenManager: ExampleAppOpenManager

    override fun onCreate() {
        super.onCreate()

        AppLovinSdk.initializeSdk(this)
        AudienceNetworkAds.initialize(this)
//        AdSettings.setTestMode(true)

        AppLovinSdk.getInstance(this).initializeSdk {
            run {
                appOpenManager = ExampleAppOpenManager(applicationContext, getString(R.string.applovin_appopen))
            }
        }
    }

    class ExampleAppOpenManager(applicationContext: Context?, adUnitId: String) : LifecycleObserver, MaxAdListener {
        private var appOpenAd: MaxAppOpenAd? = null
        private var context: Context? = null
        private val adUnitId: String

        init {
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)

            context = applicationContext
            this.adUnitId = adUnitId

            appOpenAd = MaxAppOpenAd(adUnitId, applicationContext!!)
            appOpenAd?.setListener(this)
            appOpenAd?.loadAd()
        }

        private fun showAdIfReady() {
            if (appOpenAd == null || !AppLovinSdk.getInstance(context).isInitialized) return

            if (appOpenAd?.isReady == true) {
                appOpenAd?.showAd(adUnitId)
            } else {
                appOpenAd?.loadAd()
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun onStart() {
            showAdIfReady()
        }

        override fun onAdLoaded(ad: MaxAd) {}
        override fun onAdLoadFailed(adUnitId: String, error: MaxError) {}
        override fun onAdDisplayed(ad: MaxAd) {}
        override fun onAdClicked(ad: MaxAd) {}

        override fun onAdHidden(ad: MaxAd) {
            appOpenAd?.loadAd()
        }

        override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
            appOpenAd?.loadAd()
        }
    }
}