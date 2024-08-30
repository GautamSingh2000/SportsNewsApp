package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.models.ResponseModel.TrendingNew
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.screens.Activity.TrendingNewsActivity
import com.squareup.picasso.Picasso

class trending_newsrv_Adapter(val context: Context, val list: List<TrendingNew>) :
    RecyclerView.Adapter<trending_newsrv_Adapter.UpcomingMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMatchViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_trending_news_ll, parent, false)
        return UpcomingMatchViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UpcomingMatchViewHolder, position: Int) {
        val data = list.get(position)

        if (!data.image.isNullOrEmpty()) {
            Picasso.get().load(data.image).placeholder(R.drawable.img).into(holder.image)
        }
        holder.type.text = data.name
        holder.title.text = data.title

        holder.card.setOnClickListener {
            val intent = Intent(context, TrendingNewsActivity::class.java)
            intent.putExtra("url", data)
            if(list.size>1) {
                intent.putParcelableArrayListExtra("simillarList", list as ArrayList<TrendingNew>)
            }
            context.startActivity(intent)
        }

    }

    class UpcomingMatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: com.google.android.material.card.MaterialCardView =
            itemView.findViewById(R.id.card)
        val image = itemView.findViewById<ImageView>(R.id.iv)
        val type = itemView.findViewById<TextView>(R.id.new_title)
        val title = itemView.findViewById<TextView>(R.id.news_second_title)
    }
}