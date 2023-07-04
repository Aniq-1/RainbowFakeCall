package com.strapes.android.addams.fake.call.wednesday.message.activities

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.strapes.android.addams.fake.call.R
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.Constants
import com.strapes.android.addams.fake.call.wednesday.message.AdsModule.NativeAppLovinProrityAndAdmob

class ExitScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit_screen)
        ClickListeners()
    }

    private fun ClickListeners(){

        findViewById<MaterialCardView>(R.id.noBtn).setOnClickListener {
            finish()
        }

        findViewById<MaterialCardView>(R.id.yesBtn).setOnClickListener {
            finishAffinity()
        }

        findViewById<MaterialCardView>(R.id.rateUss).setOnClickListener {
            val uri = Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    @SuppressLint("CutPasteId")
    override fun onResume() {
        super.onResume()
        if (Constants.isNetworkAvailable(this)) {
            val progress = ProgressDialog(this)
            progress.setTitle("Alert")
            progress.setMessage("Please wait...")
            progress.setCancelable(false)
            progress.show()

            Constants.frameLayout = findViewById(R.id.nativeFrameExit)
            Constants.nativeframe = findViewById(R.id.nativeFrameExit)
            NativeAppLovinProrityAndAdmob.loadingnativeads(this, "")
            val handler = Handler()
            handler.postDelayed({
                findViewById<View>(R.id.nativeFrameExit).visibility = View.VISIBLE
                if (!isFinishing) {
                    progress.dismiss()
                }
            }, 3000)
        }
    }

}