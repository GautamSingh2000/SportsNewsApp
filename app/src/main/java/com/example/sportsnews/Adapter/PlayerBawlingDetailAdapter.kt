package com.mindgeeks.sportsnews.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Model.ResponseModel.Bowling

class PlayerBawlingDetailAdapter() :
    RecyclerView.Adapter<PlayerBawlingDetailAdapter.ViewHolder>() {

    var list : ArrayList<Bowling> =  ArrayList<Bowling>()

    fun setData( list : ArrayList<Bowling>)
    {
        this.list.clear()
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val title = itemView.findViewById<TextView>(R.id.titile)
    val Matches = itemView.findViewById<TextView>(R.id.Matches)
    val Innings = itemView.findViewById<TextView>(R.id.Innings)
    val Balls = itemView.findViewById<TextView>(R.id.Balls)
    val Overs = itemView.findViewById<TextView>(R.id.Overs)
    val Runs = itemView.findViewById<TextView>(R.id.Runs)
    val Wickets = itemView.findViewById<TextView>(R.id.Wickets)
    val Econ = itemView.findViewById<TextView>(R.id.Econ)
    val Average = itemView.findViewById<TextView>(R.id.Average)
    val Strike = itemView.findViewById<TextView>(R.id.Strike)
    val Bestinning = itemView.findViewById<TextView>(R.id.Bestinning)
    val Bestmatch = itemView.findViewById<TextView>(R.id.Bestmatch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_player_bawling_stats_data,
            parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list.get(position)
        holder.title.text = data.title
        holder.Matches.text = data.matches .toString()
        holder.Innings.text = data.innings .toString()
        holder.Balls.text = data.balls .toString()
        holder.Overs.text = data.overs .toString()
        holder.Runs.text = data.runs .toString()
        holder.Wickets.text = data.wickets .toString()
        holder.Econ.text = data.econ
        holder.Average.text = data.average
        holder.Strike.text = data.strike
        holder.Bestinning.text = data.best_innings.toString()
        holder.Bestmatch.text = data.best_match.toString()
    }
}