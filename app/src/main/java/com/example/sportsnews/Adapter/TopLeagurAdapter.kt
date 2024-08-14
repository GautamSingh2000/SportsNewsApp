package com.mindgeeks.sportsnews.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindgeeks.sportsnews.Model.ResponseModel.League
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Activity.LeagueActivity
import com.squareup.picasso.Picasso

class TopLeagurAdapter(val context : Context, val list: ArrayList<League>):RecyclerView.Adapter<TopLeagurAdapter.TopLeagueViewHolder>() {

    class TopLeagueViewHolder(itemView: View):ViewHolder(itemView)
    {
        val iv = itemView.findViewById<ImageView>(R.id.iv)
        val card : com.google.android.material.card.MaterialCardView = itemView.findViewById(R.id.cardview_league)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopLeagueViewHolder {
         val viewType =  LayoutInflater.from(parent.context).inflate(R.layout.single_league_layout,
             parent,false)
         return TopLeagueViewHolder(viewType)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: TopLeagueViewHolder, position: Int) {
        val data = list.get(position)
        Picasso.get().load(data.image).into(holder.iv)
        holder.card.setOnClickListener {
            val intent = Intent(context, LeagueActivity::class.java)
            intent.putExtra("LeagueId",data.tournamentId)
 
 
            intent.putExtra("LeagueName",data.tournamentName)
            intent.putExtra("LeagueImage",data.image)
  
            context.startActivity(intent)
        }
    }

}