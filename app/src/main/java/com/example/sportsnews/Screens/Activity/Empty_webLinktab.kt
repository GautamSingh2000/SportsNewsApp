package com.mindgeeks.sportsnews.Screens.Activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.databinding.ActivityEmptyWebLinktabBinding

class Empty_webLinktab : AppCompatActivity() {
    lateinit var binding: ActivityEmptyWebLinktabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmptyWebLinktabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link = intent.getStringExtra("Link")
        Log.e("EmptyLinkActivity", "inputlink is $link")
        val heading = intent.getStringExtra("Heading")
        binding.aoobar.titile.text = heading
        binding.aoobar.backBtn.setOnClickListener {
            onBackPressed()
        }

        if (link != null) {
            openTab(link)
//            binding.webView.settings.javaScriptEnabled = true
//            binding.webView.settings.loadWithOverviewMode = true
//            binding.webView.settings.useWideViewPort = true
//            binding.webView.loadUrl(link)
        } else {
            Toast.makeText(this, "Try Again Later !!", Toast.LENGTH_LONG).show()
        }
    }

    fun openTab(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(this, R.color.gradient_red))
            builder.setDefaultColorSchemeParams(params.build())
            // shows the title of web-page in toolbar
            builder.setShowTitle(true)

            // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
            builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

            // To modify the close button, use
            // builder.setCloseButtonIcon(bitmap)

            // to set weather instant apps is enabled for the custom tab or not, use
            builder.setInstantAppsEnabled(true)

            //  To use animations use -
            //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
            //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
            val customBuilder = builder.build()
            customBuilder.intent.setPackage("com.android.chrome")
            customBuilder.launchUrl(this, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }
    }
}

