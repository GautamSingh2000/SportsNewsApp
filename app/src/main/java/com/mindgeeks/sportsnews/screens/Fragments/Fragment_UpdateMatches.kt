package com.mindgeeks.sportsnews.screens.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindgeeks.sportsnews.adapters.adpater_Update_matches
import com.mindgeeks.sportsnews.models.ResponseModel.Matche
import com.mindgeeks.sportsnews.databinding.FragmentUpdateMatchesBinding

class Fragment_UpdateMatches : Fragment() {
    private lateinit var binding: FragmentUpdateMatchesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateMatchesBinding.inflate(inflater, container, false)
        var dateWiseMatchUpdate: ArrayList<Matche> = ArrayList()
            dateWiseMatchUpdate.clear()
            dateWiseMatchUpdate = arguments?.getParcelableArrayList("ARG_MATCH_DATA") ?: ArrayList()
        Log.e("UpdateMatchesFragment", "dateWiseMatchUpdate size in UpdateMatchesFragment is ${dateWiseMatchUpdate.size}")
        updateMatchData(dateWiseMatchUpdate)

        return binding.root
    }

    private fun updateMatchData(newlist : ArrayList<Matche>) {
        if (newlist.isNotEmpty() && newlist.size > 0) {
            binding.updateMatchFragRc.adapter =
                adpater_Update_matches(requireContext(), newlist, parentFragmentManager)
            binding.updateMatchFragRc.adapter?.notifyDataSetChanged()
            binding.updateMatchFragRc.visibility = View.VISIBLE
        } else {
            binding.nodatafound.visibility = View.VISIBLE
        }
    }


}
