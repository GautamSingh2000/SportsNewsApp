package com.mindgeeks.sportsnews.Adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.Model.OtherModel.ScoreCard
import com.mindgeeks.sportsnews.Model.ResponseModel.TeamPlayer
import com.mindgeeks.sportsnews.Screens.Fragments.ScoreCardFragment
import com.mindgeeks.sportsnews.Screens.Fragments.SquardFragment

class   LiveMatchMainTabLayoutAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    private  var TabTitle: ArrayList<String> =ArrayList()
    private  var listA : ArrayList<TeamPlayer> =ArrayList()
    private  var listB : ArrayList<TeamPlayer> =ArrayList()
    private lateinit var scoreList : ScoreCard

    fun sendData(listA: ArrayList<TeamPlayer>, listB: ArrayList<TeamPlayer>, teamsName : ArrayList<String>
    ,scoreList :ScoreCard)
    {
        this.listA = listA
        this.listB = listB
        TabTitle = teamsName
        this.scoreList = scoreList
    }

    override fun createFragment(position: Int): Fragment {
        val squardFragment = SquardFragment()
        val scoreCardFragment = ScoreCardFragment()

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