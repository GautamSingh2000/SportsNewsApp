package com.mindgeeks.sportsnews.adapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.models.ResponseModel.LiveMatche
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Match_Live
import com.squareup.picasso.Picasso


class adapter_LiveMatches(
    val list: List<LiveMatche>,
    val fragmentManager: FragmentManager,
) : RecyclerView.Adapter<adapter_LiveMatches.LiveMatchesViewHolder>() {
    class LiveMatchesViewHolder(itemView: View) : ViewHolder(itemView) {
        val team1flag: com.google.android.material.imageview.ShapeableImageView =
            itemView.findViewById(R.id.teamOnelogo)
        val team2flag: com.google.android.material.imageview.ShapeableImageView =
            itemView.findViewById(R.id.teamTwologo)
        val team1name: TextView = itemView.findViewById(R.id.teamOneName)
        val team2name: TextView = itemView.findViewById(R.id.teamTwoName)
        val team1run: TextView = itemView.findViewById(R.id.teamOneRuns)
        val team1over: TextView = itemView.findViewById(R.id.teamOneOvers)
        val team2run: TextView = itemView.findViewById(R.id.teamTwoRuns)
        val team2over: TextView = itemView.findViewById(R.id.teamTwoOvers)
        val leag_name: TextView = itemView.findViewById(R.id.leag)
        val card: RelativeLayout = itemView.findViewById(R.id.liveCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveMatchesViewHolder {
        val viewType =
            LayoutInflater.from(parent.context).inflate(R.layout.single_live_match, parent, false)
        return LiveMatchesViewHolder(viewType)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LiveMatchesViewHolder, position: Int) {
        val data = list.get(position)

        holder.card.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("MatchId",data.match_id)
            bundle.putString("CompId",data.competition_id)
            bundle.putString("action","1")
            Log.e("Mainactivity","live match clicked !!!")

            val liveMatchFragment = Fragment_Match_Live()
            liveMatchFragment.setArguments(bundle)

            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.MainScreeFrameLayout, liveMatchFragment)
                .addToBackStack("LiveMatchFragment")
                .commit()
        }


        holder.leag_name.text = data.type
        holder.team1name.text = data.teama
        holder.team2name.text = data.teamb


        if (data.teama_scores_full.equals("") || data.teamb_scores_full.equals("")) {
            holder.team1run.text = " -- "
            holder.team1over.text = " -- "
            holder.team2run.text = " -- "
            holder.team2over.text = " -- "
        } else {
            val input1 = data.teama_scores_full
            val parts1 = input1.split(" (")
            val input2 = data.teamb_scores_full
            val parts2 = input2.split(" (")

            holder.team1run.text = parts1[0]
            holder.team1over.text = "(${parts1[1].removeSuffix(")")})"
            holder.team2run.text = parts2[0]
            holder.team2over.text = "(${parts2[1].removeSuffix(")")})"
        }
        Picasso.get().load(data.teama_logo).placeholder(R.drawable.img_1).into(holder.team1flag)
        Picasso.get().load(data.teamb_logo).placeholder(R.drawable.img_1).into(holder.team2flag)

    }
}