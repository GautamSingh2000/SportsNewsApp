package com.mindgeeks.sportsnews.Adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.Model.ResponseModel.Matche
import com.mindgeeks.sportsnews.Model.ResponseModel.New
import com.mindgeeks.sportsnews.Screens.Fragments.UpdateMatchesFragment
import com.mindgeeks.sportsnews.Screens.Fragments.UpdateNewsFragment

class UpateTabLayoutAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
private val TAG = "UpateTabLayoutAdapter"
    private var dateWiseMatchUpdate: ArrayList<Matche> = ArrayList()
    private var dateWiseNewsUpdate: ArrayList<New> = ArrayList()
    private var matchF:UpdateMatchesFragment = UpdateMatchesFragment()
    private var newsF: UpdateNewsFragment = UpdateNewsFragment()

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val matchBundle = Bundle()
                matchBundle.putParcelableArrayList("ARG_MATCH_DATA", dateWiseMatchUpdate)
                matchF.arguments = matchBundle
                return matchF
            }
            else -> {
                val newsBundle = Bundle()
                newsBundle.putParcelableArrayList("ARG_NEWS_DATA", dateWiseNewsUpdate)
                newsF.arguments = newsBundle
                return newsF
            }
        }
    }


    fun setData(matchList: ArrayList<Matche>, news: ArrayList<New>) {
        dateWiseMatchUpdate.clear()
        dateWiseNewsUpdate.clear()
        dateWiseMatchUpdate = matchList
        dateWiseNewsUpdate = news
        notifyDataSetChanged()
    }
}
