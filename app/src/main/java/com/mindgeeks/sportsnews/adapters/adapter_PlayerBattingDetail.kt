package com.mindgeeks.sportsnews.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.models.ResponseModel.Batting

class adapter_PlayerBattingDetail() : RecyclerView.Adapter<adapter_PlayerBattingDetail.ViewHolder>() {

    var list : ArrayList<Batting> =  ArrayList<Batting>()

   fun setData( list : ArrayList<Batting>)
    {
        this.list.clear()
        this.list = list
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {    val title = itemView.findViewById<TextView>(R.id.title)
        val Matches = itemView.findViewById<TextView>(R.id.Matches)
        val Innings = itemView.findViewById<TextView>(R.id.Innings)
        val Notout = itemView.findViewById<TextView>(R.id.Notout)
        val Runs = itemView.findViewById<TextView>(R.id.Runs)
        val Balls = itemView.findViewById<TextView>(R.id.Balls)
        val Catches = itemView.findViewById<TextView>(R.id.Catches)
        val Stumpings = itemView.findViewById<TextView>(R.id.Stumpings)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.single_player_batting_stats_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        Log.e("PLayerBattingdeatilAdapter","data at position $position $data")
        holder.Matches.text = data.matches
        holder.Innings.text = data.innings
        holder.Notout.text = data.notout
        holder.title.text = data.title
        holder.Runs.text = data.runs
        holder.Catches.text = data.catches
        holder.Balls.text = data.balls
        holder.Stumpings.text = data.stumpings
    }
}