package com.mindgeeks.sportsnews.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.Model.ResponseModel.New
import com.mindgeeks.sportsnews.Model.ResponseModel.TrendingNew
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Activity.TrendingNewsActivity
import com.squareup.picasso.Picasso

class UpdateNewsAdapter(val context : Context, val list: List<New>): RecyclerView.Adapter<UpdateNewsAdapter.UpdateNewsViewHolder>() {

    class UpdateNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image : ImageView = itemView.findViewById(R.id.NewsImage)
        val title : TextView =itemView.findViewById(R.id.NewHeading)
        val sub_title : TextView =itemView.findViewById(R.id.NewsSubTitle)
        val time : TextView =itemView.findViewById(R.id.lastTime)
        val readmore : TextView = itemView.findViewById(R.id.readfull_tv)
        val share : TextView = itemView.findViewById(R.id.share_tv)
        val shareImage = itemView.findViewById<ImageView>(R.id.imageView4)
        val moreImage = itemView.findViewById<ImageView>(R.id.imageView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateNewsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_update_news, parent,false)
        return UpdateNewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UpdateNewsViewHolder, position: Int) {

        val data = list.get(position)

        holder.time.text = data.publishedAt
        holder.title.text = data.title
        holder.sub_title.text = data.content.toString()
       if(!data.image.isNullOrEmpty()) Picasso.get().load(data.image).placeholder(R.drawable.img).into(holder.image)

        holder.readmore.setOnClickListener{
            val news = TrendingNew(
                author = data.author,
                content = data.content.toString(),
                desc = data.desc,
                image = data.image,
                name = data.name,
                publishedAt = data.publishedAt,
                title = data.title,
                url = data.url
            )
            val intent = Intent(context, TrendingNewsActivity::class.java)
            intent.putExtra("url",news)
            context.startActivity(intent)
        }
  holder.moreImage.setOnClickListener{
      val news = TrendingNew(
          author = data.author,
          content = data.content.toString(),
          desc = data.desc,
          image = data.image,
          name = data.name,
          publishedAt = data.publishedAt,
          title = data.title,
          url = data.url
      )

            val intent = Intent(context, TrendingNewsActivity::class.java)
            intent.putExtra("url",news)
            context.startActivity(intent)
        }

        holder.shareImage.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, data.url)
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, "Share link via")
            if (shareIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(chooser)
            }
        }

        holder.share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, data.url)
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, "Share link via")
            if (shareIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(chooser)
            }
        }
    }

    fun openTab(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(context, R.color.gradient_red))
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
            //  builder.setStartAnimations(context, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
            //  builder.setExitAnimations(context, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
            val customBuilder = builder.build()
            customBuilder.intent.setPackage("com.android.chrome")
            customBuilder.launchUrl(context, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }


}