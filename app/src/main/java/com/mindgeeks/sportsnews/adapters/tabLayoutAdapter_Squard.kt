package com.mindgeeks.sportsnews.adapters

import Fragment_TeamPlayeraName
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.models.ResponseModel.TeamPlayer

class tabLayoutAdapter_Squard(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var listA: ArrayList<TeamPlayer> = arrayListOf()
    private var listB: ArrayList<TeamPlayer> = arrayListOf()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = Fragment_TeamPlayeraName()
        val bundle = Bundle()
        when (position) {
            0 -> bundle.putParcelableArrayList("playerNames", listA)
            1 -> bundle.putParcelableArrayList("playerNames", listB)
        }
        fragment.arguments = bundle
        return fragment
    }

    fun sendData(listA: ArrayList<TeamPlayer>, listB: ArrayList<TeamPlayer>) {
        this.listA = listA
        this.listB = listB
    }
}
