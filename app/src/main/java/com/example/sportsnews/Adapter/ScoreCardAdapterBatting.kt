package com.mindgeeks.sportsnews.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindgeeks.sportsnews.Model.ResponseModel.Batsmen
import com.mindgeeks.sportsnews.R


class ScoreCardAdapterBatting(val data : List<Batsmen>): RecyclerView.Adapter<ScoreCardAdapterBatting.ScoreCardViewHolder>() {
     lateinit var Scorelist : List<Batsmen>

    init {
      Scorelist = data
    }

    class ScoreCardViewHolder(itemView: View) : ViewHolder(itemView)
    { val tableView = itemView.findViewById<TableLayout>(R.id.tableView)
     val e1 = itemView.findViewById<TextView>(R.id.e1)
     val e2 = itemView.findViewById<TextView>(R.id.e2)
     val e3 = itemView.findViewById<TextView>(R.id.e3)
     val e4 = itemView.findViewById<TextView>(R.id.e4)
     val e5 = itemView.findViewById<TextView>(R.id.e5)
     val e6 = itemView.findViewById<TextView>(R.id.e6)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_score_row_layout, parent,false)
        return ScoreCardViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return Scorelist.size
    }

    override fun onBindViewHolder(holder: ScoreCardViewHolder, position: Int) {
        val data = Scorelist.get(position)
        holder.e1.text = formatName(data.name)
        holder.e2.text = data.runs
        holder.e3.text = data.balls_faced
        holder.e4.text = data.fours
        holder.e5.text = data.sixes
        holder.e6.text = data.strike_rate
    }
    fun formatName(fullName: String): String {
        val parts = fullName.split(" ")
        if (parts.size < 2) {
            // Handle cases where there might not be a full name provided
            return fullName
        }

        val firstName = parts.first()
        val lastName = parts.last()

        // Construct the formatted name
        val formattedName = "${firstName.first().toUpperCase()}. $lastName"

        return formattedName
    }
}