package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindgeeks.sportsnews.models.ResponseModel.TeamPlayer
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.screens.Activity.Activity_Player_Detail

class adapter_TeamPlayerName(val context: Context, val list: List<TeamPlayer>) :
    RecyclerView.Adapter<adapter_TeamPlayerName.TeamPlayerNameViewHolder>() {
    class TeamPlayerNameViewHolder(itemView: View) : ViewHolder(itemView) {
        val ll = itemView.findViewById<RelativeLayout>(R.id.ll)
        val name: TextView = itemView.findViewById(R.id.player_name_tv)
        val nationality: TextView = itemView.findViewById(R.id.nationality)
        val profile: ImageView =
            itemView.findViewById(R.id.profile_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlayerNameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_team_member_name, parent, false)
        return TeamPlayerNameViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TeamPlayerNameViewHolder, position: Int) {
        val data = list.get(position)

        holder.ll.setOnClickListener{
            val intent = Intent(context, Activity_Player_Detail::class.java)
            intent.putExtra("playerId",data.pid.toString())
            Log.e("TeamPLayerAdapter","player id is ${data.pid}")
            context.startActivity(intent)
        }
        Log.e("TeamPlayerNameAdapter", "${data.first_name} ${data.middle_name} ${data.last_name}")
        holder.name.text = "${data.first_name} ${data.middle_name} ${data.last_name} "
        holder.nationality.text = "${data.nationality} "
    }
}