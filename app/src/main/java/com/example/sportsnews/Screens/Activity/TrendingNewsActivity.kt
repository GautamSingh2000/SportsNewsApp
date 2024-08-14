package com.mindgeeks.sportsnews.Screens.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mindgeeks.sportsnews.databinding.ActivityTrendingNewsBinding


class TrendingNewsActivity : AppCompatActivity() {

    lateinit var binding : ActivityTrendingNewsBinding
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityTrendingNewsBinding.inflate(layoutInflater)
                val view = binding.root
                setContentView(view)
                val link = intent.getStringExtra("url")
                if (link != null) {
                    binding.webView.apply {
                        visibility = View.VISIBLE
                        loadUrl(link) }
                }else{
                    binding.nodatafound.visibility = View.VISIBLE
                }

                binding.appbar.backBtn.setOnClickListener{
                    onBackPressed()
                }
        }
    }