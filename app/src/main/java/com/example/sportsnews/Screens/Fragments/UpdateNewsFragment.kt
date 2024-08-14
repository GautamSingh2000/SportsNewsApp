package com.mindgeeks.sportsnews.Screens.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mindgeeks.sportsnews.Adapter.UpdateNewsAdapter
import com.mindgeeks.sportsnews.Model.ResponseModel.New
import com.mindgeeks.sportsnews.databinding.FragmentUpdateNewsBinding

class UpdateNewsFragment : Fragment() {

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
                binding.updateNewsRv.adapter = UpdateNewsAdapter(requireContext(), dateWiseNewsUpdate)
                binding.updateNewsRv.adapter?.notifyDataSetChanged()
                binding.updateNewsRv.visibility = View.VISIBLE
            } else {
                binding.nodatafound.visibility = View.VISIBLE
            }

        return binding.root
    }
}
