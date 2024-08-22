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
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.models.ResponseModel.PlayerData
import com.mindgeeks.sportsnews.screens.Activity.Activity_Player_Detail

class adapter_PlayerSearch( ) :
    RecyclerView.Adapter<adapter_PlayerSearch.ViewHolder>() {

  private  lateinit var context : Context
  private  lateinit var list: ArrayList<PlayerData>

       fun setData(context : Context , list: ArrayList<PlayerData>)
       {
           this.context = context
           this.list = list
        }

    fun clearList()
    {
        list.clear()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ll = itemView.findViewById<RelativeLayout>(R.id.ll)
        val name: TextView = itemView.findViewById(R.id.player_name_tv)
        val nationality: TextView = itemView.findViewById(R.id.nationality)
        val profile: ImageView =
            itemView.findViewById(R.id.profile_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : ViewHolder{
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_team_member_name, parent, false)
        return ViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        holder.nationality.text = data.nationality

        holder.ll.setOnClickListener{
            val intent = Intent(context, Activity_Player_Detail::class.java)
            intent.putExtra("playerId",data.pId)
            Log.e("TeamPLayerAdapter","player id is ${data.pId}")
            context.startActivity(intent)
        }
        Log.e("TeamPlayerNameAdapter", "${data.firstName} ${data.lastName} ")
        holder.name.text ="${data.firstName} ${data.lastName} "
    }
}