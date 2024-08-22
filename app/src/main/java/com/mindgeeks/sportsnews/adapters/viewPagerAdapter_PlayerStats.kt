package com.mindgeeks.sportsnews.adapters

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.models.ResponseModel.Batting
import com.mindgeeks.sportsnews.models.ResponseModel.Bowling
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_BallingStats
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_BattingSats

class viewPagerAdapter_PlayerStats(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    var BawlingData: ArrayList<Bowling> = ArrayList()
    var BattingData: ArrayList<Batting> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Fragment_BattingSats.newInstance(BattingData)
            else -> Fragment_BallingStats.newInstance(BawlingData)
        }
    }

    fun setData(bowlingList: ArrayList<Bowling>, battingList: ArrayList<Batting>, context: Context) {
        try {
            BawlingData = bowlingList
        } catch (e: Exception) {
            Log.e("PlayerStatsViewPagerAdapter", "PlayerStatsViewPagerAdapter error ${e.message}")
            Toast.makeText(context, "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }
        try {
            BattingData = battingList
        } catch (e: Exception) {
            Log.e("PlayerStatsViewPagerAdapter", "PlayerStatsViewPagerAdapter error ${e.message}")
            Toast.makeText(context, "Error ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
