package com.mindgeeks.sportsnews.Adapter

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.Model.ResponseModel.Batting
import com.mindgeeks.sportsnews.Model.ResponseModel.Bowling
import com.mindgeeks.sportsnews.Screens.Fragments.BallingStatsFragment
import com.mindgeeks.sportsnews.Screens.Fragments.BattingSatsFragment

class PlayerStatsViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    var BawlingData: ArrayList<Bowling> = ArrayList()
    var BattingData: ArrayList<Batting> = ArrayList()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BattingSatsFragment.newInstance(BattingData)
            else -> BallingStatsFragment.newInstance(BawlingData)
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
