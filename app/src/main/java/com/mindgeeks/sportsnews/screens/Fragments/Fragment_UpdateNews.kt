package com.mindgeeks.sportsnews.screens.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindgeeks.sportsnews.adapters.adapter_UpdateNews
import com.mindgeeks.sportsnews.models.ResponseModel.New
import com.mindgeeks.sportsnews.databinding.FragmentUpdateNewsBinding

class Fragment_UpdateNews : Fragment() {

    private lateinit var binding: FragmentUpdateNewsBinding
    private var dateWiseNewsUpdate: ArrayList<New> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateNewsBinding.inflate(layoutInflater)
            dateWiseNewsUpdate.clear()
            dateWiseNewsUpdate = arguments?.getParcelableArrayList("ARG_NEWS_DATA") ?: ArrayList()
            Log.e("UpdateNewsFragment", "dateWiseNewsUpdate size in UpdateNewsFragment is ${dateWiseNewsUpdate.size}")
            if (dateWiseNewsUpdate.isNotEmpty()) {
                binding.updateNewsRv.adapter = adapter_UpdateNews(requireContext(), dateWiseNewsUpdate)
                binding.updateNewsRv.adapter?.notifyDataSetChanged()
                binding.updateNewsRv.visibility = View.VISIBLE
            } else {
                binding.nodatafound.visibility = View.VISIBLE
            }

        return binding.root
    }
}
