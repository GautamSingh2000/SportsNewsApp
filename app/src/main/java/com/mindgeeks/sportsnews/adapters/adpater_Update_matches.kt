package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.content.Intent
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.models.ResponseModel.Matche
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.screens.Activity.MatchDetailActivity
import com.squareup.picasso.Picasso
import android.widget.ImageView
import android.widget.TextView

class adpater_Update_matches (val context: Context, val list: ArrayList<Matche>, val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<adpater_Update_matches.Update_matchesViewHolder>() {

    val slide_down_anim = AnimationUtils.loadAnimation(context, R.anim.slide_down_anim)
    val slide_up_anim = AnimationUtils.loadAnimation(context, R.anim.slide_up_anim)

    class Update_matchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var togglebutton: Boolean = false

        val imageRL: CardView = itemView.findViewById(R.id.imagecardView)
        val teamAflag: ImageView = itemView.findViewById(R.id.teamAFlag)
        val teamBflag: ImageView = itemView.findViewById(R.id.teamBFlag)
        val teamACapton: ImageView = itemView.findViewById(R.id.teamACapton)
        val teamBCapton: ImageView = itemView.findViewById(R.id.teamBCapton)
        val TeamNameCV: CardView = itemView.findViewById(R.id.cardView8)
        val more: CardView = itemView.findViewById(R.id.more)
        val TeamACircularFlag: ImageView = itemView.findViewById(R.id.TeamACircularFlag)
        val TeamBCircularFlag: ImageView = itemView.findViewById(R.id.TeamBCircularFlag)
        val Updated_date: TextView = itemView.findViewById(R.id.date)
        val teamAname: TextView = itemView.findViewById(R.id.TeamAName)
        val teamBname: TextView = itemView.findViewById(R.id.TeamBName)
        val TeamPlayerNameCV: CardView = itemView.findViewById(R.id.team_rc_ll)
        val teamARc: RecyclerView = itemView.findViewById(R.id.teamAUpdateMatchRc)
        val teamBRc: RecyclerView = itemView.findViewById(R.id.teamBUpdateMatchRc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Update_matchesViewHolder {
        val viewType = LayoutInflater.from(parent.context).inflate(
            R.layout.single_update_match,
            parent, false
        )
        return Update_matchesViewHolder(viewType)
    }

    override fun onBindViewHolder(holder: Update_matchesViewHolder, position: Int) {
        val data = list[position]

        holder.Updated_date.text = data.match_time
        holder.teamAname.text = data.teama
        Picasso.get().load(data.teama_logo).into(holder.TeamACircularFlag)
        holder.teamBname.text = data.teamb
        Picasso.get().load(data.teamb_logo).into(holder.TeamBCircularFlag)
        Picasso.get().load(data.teama_logo).into(holder.teamAflag)
        Picasso.get().load(data.teamb_logo).into(holder.teamBflag)
        Picasso.get().load(R.drawable.captain_one).into(holder.teamACapton)
        Picasso.get().load(R.drawable.caption_two).into(holder.teamBCapton)

        holder.teamARc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.teamARc.adapter = adapter_TeamPlayerName(context, data.teama_players)
        holder.teamBRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.teamBRc.adapter = adapter_TeamPlayerName(context, data.teamb_players)

        holder.more.setOnClickListener {
            Log.e("Update_matchesAdapter", "Adding HomeFragment to MainScreeFrameLayout")
            val intent = Intent(context, MatchDetailActivity::class.java)
            intent.putExtra("MatchId", data.match_id)
            intent.putExtra("CompId", data.competition_id)
            context.startActivity(intent)
        }

        holder.TeamNameCV.setOnClickListener {
            Log.e("adapter", "value of toggle button is " + holder.togglebutton)
            if (holder.togglebutton) {
                holder.more.visibility = View.INVISIBLE
                holder.imageRL.visibility = View.GONE
                holder.TeamPlayerNameCV.visibility = View.GONE
                holder.togglebutton = false
            } else {
                holder.togglebutton = true
                holder.more.visibility = View.VISIBLE
                holder.imageRL.apply {
                    visibility = View.VISIBLE
                    TransitionManager.beginDelayedTransition(this)
                }
                holder.TeamPlayerNameCV.apply {
                    visibility = View.VISIBLE
                    TransitionManager.beginDelayedTransition(this)
                }
                // Scroll to the position of the clicked item
                (holder.itemView.parent as? RecyclerView)?.apply {
                    post {
                        smoothScrollToPosition(holder.adapterPosition)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
