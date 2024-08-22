package com.mindgeeks.sportsnews.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mindgeeks.sportsnews.models.ResponseModel.Matche
import com.mindgeeks.sportsnews.models.ResponseModel.New
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_UpdateMatches
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_UpdateNews

class tabLayoutAdapter_Upate(fragment: Fragment) : FragmentStateAdapter(fragment) {
private val TAG = "UpateTabLayoutAdapter"
    private var dateWiseMatchUpdate: ArrayList<Matche> = ArrayList()
    private var dateWiseNewsUpdate: ArrayList<New> = ArrayList()
    private var matchF:Fragment_UpdateMatches = Fragment_UpdateMatches()
    private var newsF: Fragment_UpdateNews = Fragment_UpdateNews()

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
