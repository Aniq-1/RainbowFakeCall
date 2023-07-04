package com.strapes.android.addams.fake.call.wednesday.message


import android.app.Application
import com.applovin.sdk.AppLovinSdk
import com.facebook.ads.AdSettings
import com.facebook.ads.AudienceNetworkAds
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.ApplovinAppOpen


class MainApplication: Application() {

    private val appOpenManager: ApplovinAppOpen? = null

    override fun onCreate() {
        AppLovinSdk.initializeSdk(this)
        AudienceNetworkAds.initialize(this)
       AdSettings.setTestMode(true)
        super.onCreate()
    }

}