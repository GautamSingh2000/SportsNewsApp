package com.mindgeeks.sportsnews.Screens.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.mindgeeks.sportsnews.Adapter.PlayerBattingDetailAdapter
import com.mindgeeks.sportsnews.Model.ResponseModel.Batting
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.databinding.FragmentBattingSatsBinding

class BattingSatsFragment : Fragment() {
    private lateinit var binding: FragmentBattingSatsBinding
    private val adapter = PlayerBattingDetailAdapter()
    private var battingData: ArrayList<Batting> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBattingSatsBinding.inflate(layoutInflater)
        binding.stateRv.adapter = adapter

        val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                binding.scrollDownIv.visibility = View.GONE
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })

        binding.nestedScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            val lastChild = binding.nestedScrollView.getChildAt(binding.nestedScrollView.childCount - 1)
            val lastVisibleItem = lastChild.bottom - (binding.nestedScrollView.height + binding.nestedScrollView.scrollY)
            if (lastVisibleItem <= 0) {
                binding.scrollDownIv.startAnimation(fadeOut)
            } else {
                binding.scrollDownIv.clearAnimation()
                binding.scrollDownIv.visibility = View.VISIBLE
            }
        }

        arguments?.let {
            battingData = it.getParcelableArrayList(ARG_BATTING_DATA) ?: ArrayList()
            adapter.setData(battingData)
        }

        return binding.root
    }

    companion object {
        private const val ARG_BATTING_DATA = "batting_data"

        fun newInstance(battingData: ArrayList<Batting>): BattingSatsFragment {
            val fragment = BattingSatsFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_BATTING_DATA, battingData)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
