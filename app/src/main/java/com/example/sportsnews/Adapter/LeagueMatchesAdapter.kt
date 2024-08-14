package com.mindgeeks.sportsnews.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mindgeeks.sportsnews.Model.ResponseModel.Team
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Fragments.LiveMatchFragment
import com.squareup.picasso.Picasso

class LeagueMatchesAdapter(
    private val context : Context,
    private val fragmentManager: FragmentManager,
    private val list: ArrayList<Team>,
) : RecyclerView.Adapter<LeagueMatchesAdapter.LeagueMatchesViewHolder>() {
    class LeagueMatchesViewHolder(itemView: View) : ViewHolder(itemView) {
        val card: com.google.android.material.card.MaterialCardView =
            itemView.findViewById(R.id.card)
        val date = itemView.findViewById<TextView>(R.id.date)
        val TeamA = itemView.findViewById<TextView>(R.id.teamA)
        val TeamB = itemView.findViewById<TextView>(R.id.teamB)
        val runTeamA = itemView.findViewById<TextView>(R.id.runTeamA)
        val runTeamB = itemView.findViewById<TextView>(R.id.runTeamB)
        val teamAOver = itemView.findViewById<TextView>(R.id.teamAOver)
        val teamBOver = itemView.findViewById<TextView>(R.id.teamBOver)
        val winLogoA = itemView.findViewById<ImageView>(R.id.winIconA)
        val winLogoB = itemView.findViewById<ImageView>(R.id.winIconB)
        val teamALogo = itemView.findViewById<ImageView>(R.id.teamALogo)
        val teamBLogo = itemView.findViewById<ImageView>(R.id.teamBLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueMatchesViewHolder {
        val viewType = LayoutInflater.from(parent.context).inflate(
            R.layout.single_metch_4_league,
            parent, false
        )
        return LeagueMatchesViewHolder(viewType)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LeagueMatchesViewHolder, position: Int) {
        val data = list.get(position)
        var next = false

        when (data.winningTeam.trim()) {
            "Yet To Happen" -> {
                holder.date.text =  "${data.matchDate} ${data.winningTeam}"
                next = false
            }

            else -> {
                holder.date.text =   "${data.matchDate} ${data.winningTeam} Win"
                next = true
            }
        }
        holder.TeamA.text = data.teama.name
        holder.TeamB.text = data.teamb.name
        holder.runTeamA.text = "${data.teama.scores} "
        holder.runTeamB.text = "${data.teamb.scores} "
        holder.runTeamA.text = data.teama.scores
        holder.runTeamB.text = data.teamb.scores
        holder.runTeamA.text = data.teama.scores
        holder.runTeamB.text = data.teamb.scores
        holder.teamAOver.text = "( ${data.teama.overs} )"
        holder.teamBOver.text = "( ${data.teamb.overs} )"
        Picasso.get().load(data.teama.logo).into(holder.teamALogo)
        Picasso.get().load(data.teamb.logo).into(holder.teamBLogo)
        if (data.winningTeam.equals("Team A")) holder.winLogoA.visibility = View.VISIBLE
        else holder.winLogoB.visibility = View.VISIBLE

        holder.card.setOnClickListener {
            if(next) {
                val destinationFragment = LiveMatchFragment()

                val bundle = Bundle()
                bundle.putString("action", "2")
                bundle.putString("MatchId", data.matchId)
                bundle.putString("CompId", data.compId)
                destinationFragment.arguments = bundle

                fragmentManager.beginTransaction()
                    .add(R.id.LeagueActivityContainer, destinationFragment)
                    .addToBackStack("LiveMatchFragmentfromLeague")
                    .commit()
            }else{
                Toast.makeText(context, "This Match Is Yet To Happen !!", Toast.LENGTH_LONG).show()
            }
        }
    }
}