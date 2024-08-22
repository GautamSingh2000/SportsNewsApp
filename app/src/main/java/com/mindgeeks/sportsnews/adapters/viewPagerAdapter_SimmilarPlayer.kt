package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.models.ResponseModel.PlayerData
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.screens.Activity.Activity_Player_Detail
import com.squareup.picasso.Picasso

class viewPagerAdapter_SimmilarPlayer(val context: Context, val list : ArrayList<PlayerData>) : RecyclerView.Adapter<viewPagerAdapter_SimmilarPlayer.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val image = itemView.findViewById<ImageView>(R.id.profile_image)
        val playerName = itemView.findViewById<TextView>(R.id.player_name)
        val playerNationality = itemView.findViewById<TextView>(R.id.player_nationality)
        val card = itemView.findViewById<CardView>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_simmilar_player_layout,parent,false)
     return ViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
    return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        Picasso.get().load(R.drawable.player_image).into(holder.image)
        holder.playerName.text = "${data.firstName} ${data.lastName}"
        holder.playerNationality.text = data.nationality
        holder.card.setOnClickListener {
            val intent = Intent(context, Activity_Player_Detail::class.java)
            intent.putExtra("playerId",data.pId.toString())
            Log.e("TeamPLayerAdapter","player id is ${data.pId}")
            context.startActivity(intent)
        }
    }
}