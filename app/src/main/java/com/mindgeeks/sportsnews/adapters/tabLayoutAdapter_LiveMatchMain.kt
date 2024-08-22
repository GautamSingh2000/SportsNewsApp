package com.mindgeeks.sportsnews.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.models.OtherModel.score_Card
import com.mindgeeks.sportsnews.models.ResponseModel.TeamPlayer
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_ScoreCard
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Squard

class   tabLayoutAdapter_LiveMatchMain(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    private  var TabTitle: ArrayList<String> =ArrayList()
    private  var listA : ArrayList<TeamPlayer> =ArrayList()
    private  var listB : ArrayList<TeamPlayer> =ArrayList()
    private lateinit var scoreList : score_Card

    fun sendData(listA: ArrayList<TeamPlayer>, listB: ArrayList<TeamPlayer>, teamsName : ArrayList<String>
    ,scoreList :score_Card)
    {
        this.listA = listA
        this.listB = listB
        TabTitle = teamsName
        this.scoreList = scoreList
    }

    override fun createFragment(position: Int): Fragment {
        val squardFragment = Fragment_Squard()
        val scoreCardFragment = Fragment_ScoreCard()

        squardFragment.sendData(
            listA = listA,
            listB = listB,
            TeamNameList = TabTitle ,
            scoreList = scoreList
        )

        val bundle = Bundle()
        bundle.putParcelable("scoreData",scoreList)
        scoreCardFragment.arguments =  bundle

        return when (position) {
            0 -> squardFragment
            else -> scoreCardFragment
        }
    }
}