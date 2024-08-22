package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.models.ResponseModel.New
import com.mindgeeks.sportsnews.models.ResponseModel.TrendingNew
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.screens.Activity.TrendingNewsActivity
import com.squareup.picasso.Picasso

class adapter_UpdateNews(val context : Context, val list: List<New>): RecyclerView.Adapter<adapter_UpdateNews.UpdateNewsViewHolder>() {

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



    override fun getItemCount(): Int {
        return list.size
    }


}