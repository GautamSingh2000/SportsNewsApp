package com.mindgeeks.sportsnews.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.models.ResponseModel.UpcomingMatche
import com.mindgeeks.sportsnews.R
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

class adapter_UpcomingMatch(val list:List<UpcomingMatche>): RecyclerView.Adapter<adapter_UpcomingMatch.UpcomingMatchViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingMatchViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_upcoming_match, parent,false)
        return UpcomingMatchViewHolder(itemView)
    }
    override fun getItemCount(): Int {
     return list.size
    }
    override fun onBindViewHolder(holder: UpcomingMatchViewHolder, position: Int) {
     val data = list.get(position)

        holder.teamA.text =formatNameForTextView(data.teama)
        holder.teamB.text =formatNameForTextView(data.teamb)
        holder.type.text = data.type
        holder.time.text = data.match_time

        Picasso.get().load(data.teama_logo).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).placeholder(R.drawable.img_1).into(holder.teamAflag)
        Picasso.get().load(data.teamb_logo).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).placeholder(R.drawable.img_1).into(holder.teamBflag)
    }
    class UpcomingMatchViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val type = itemView.findViewById<TextView>(R.id.match_state)
        val teamA = itemView.findViewById<TextView>(R.id.teamOneName)
        val teamB = itemView.findViewById<TextView>(R.id.teamTwoName)
        val time = itemView.findViewById<TextView>(R.id.match_time)


        val teamAflag = itemView.findViewById<ImageView>(R.id.teamAFlag)
        val teamBflag = itemView.findViewById<ImageView>(R.id.teamBFlag)
    }
    fun formatNameForTextView(name: String): String {
        return name.replace(" ", "\n")
    }
}