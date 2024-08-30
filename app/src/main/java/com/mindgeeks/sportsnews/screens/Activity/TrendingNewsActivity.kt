package com.mindgeeks.sportsnews.screens.Activity

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.mindgeeks.sportsnews.models.ResponseModel.TrendingNew
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.adapters.adapter_TrendingNews
import com.mindgeeks.sportsnews.adapters.trending_newsrv_Adapter
import com.mindgeeks.sportsnews.databinding.ActivityTrendingNewsBinding
import com.squareup.picasso.Picasso


class TrendingNewsActivity : AppCompatActivity() {

    lateinit var binding : ActivityTrendingNewsBinding

    private var list = ArrayList<TrendingNew>()
            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                binding = ActivityTrendingNewsBinding.inflate(layoutInflater)
                val view = binding.root
                setContentView(view)
                window.apply {
                    decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    statusBarColor = Color.TRANSPARENT
                }
                var link:TrendingNew? = intent.getParcelableExtra<TrendingNew>("url")

                if (link != null) {
                    binding.link.setOnClickListener {
                        openTab(link.url)
                    }
                    if(!link.image.isNullOrEmpty()) {
                        Picasso.get().load(link.image).placeholder(R.drawable.img)
                            .into(binding.newsImage)
                    }
                    binding.body.text = link.content
                    binding.title.text = link.title
                    binding.time.text = link.publishedAt
                    binding.srcName.text = link.name
                    binding.link.text = link.url
                }else{
                    binding.nodatafound.visibility = View.VISIBLE
                }

                binding.backbtn.setOnClickListener{
                    onBackPressed()
                }

                val parcelableArray = intent.getParcelableArrayExtra("simillarList")
                if (parcelableArray != null) {
                    val trendingNewsList = parcelableArray.filterIsInstance<TrendingNew>()
                    list.addAll(trendingNewsList.shuffled())
                }
                if(list.size > 0) {
                    binding.rv.adapter = trending_newsrv_Adapter(this,list)
                }else{
                    binding.simTv.visibility = View.GONE
                    Toast.makeText(this,"No Similar News!!",Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
        }
    }
    }