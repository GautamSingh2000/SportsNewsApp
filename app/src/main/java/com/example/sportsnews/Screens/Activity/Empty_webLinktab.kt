package com.mindgeeks.sportsnews.Screens.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.settings.loadWithOverviewMode = true
            binding.webView.settings.useWideViewPort = true
            binding.webView.loadUrl(link)
        } else {
            Toast.makeText(this, "Try Again Later !!", Toast.LENGTH_LONG).show()
        }
    }
}
