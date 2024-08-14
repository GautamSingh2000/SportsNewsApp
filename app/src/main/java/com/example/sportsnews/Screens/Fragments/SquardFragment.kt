package com.mindgeeks.sportsnews.Screens.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindgeeks.sportsnews.Adapter.SquardTabLayoutAdapter
import com.mindgeeks.sportsnews.Model.OtherModel.ScoreCard
import com.mindgeeks.sportsnews.Model.ResponseModel.TeamPlayer
import com.mindgeeks.sportsnews.databinding.FragmentSquardBinding
import com.google.android.material.tabs.TabLayoutMediator

class SquardFragment : Fragment() {

    private lateinit var binding : FragmentSquardBinding
    private  var TabTitle : ArrayList<String> = ArrayList()
    private  var listA : ArrayList<TeamPlayer> = ArrayList()
    private  var listB : ArrayList<TeamPlayer> = ArrayList()
    private lateinit var scoreList: ScoreCard

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSquardBinding.inflate(layoutInflater)
        setTablayout()
        return binding.root
    }

    fun sendData(listA: ArrayList<TeamPlayer>, listB: ArrayList<TeamPlayer>, TeamNameList : ArrayList<String>,
                 scoreList : ScoreCard
    )
    {
        this.listA = listA
        this.listB = listB
        TabTitle = TeamNameList
        this.scoreList = scoreList
    }

    fun setTablayout()
    {
        val adapter = SquardTabLayoutAdapter(this)
        adapter.sendData(
            listA = listA,
            listB = listB
        )
        binding.squardvp.adapter = adapter
        TabLayoutMediator(binding.squardtabll,binding.squardvp){
                tab,position ->
            tab.text = TabTitle[position]
        }.attach()

    }
}