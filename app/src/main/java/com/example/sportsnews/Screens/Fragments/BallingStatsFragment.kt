package com.mindgeeks.sportsnews.Screens.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.mindgeeks.sportsnews.Adapter.PlayerBawlingDetailAdapter
import com.mindgeeks.sportsnews.Model.ResponseModel.Bowling
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.databinding.FragmentBawlingStatsBinding

class BallingStatsFragment : Fragment() {
    private lateinit var binding: FragmentBawlingStatsBinding
    private val adapter = PlayerBawlingDetailAdapter()
    private var bawlingData: ArrayList<Bowling> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentBawlingStatsBinding.inflate(layoutInflater)
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
            bawlingData = it.getParcelableArrayList(ARG_BAWLING_DATA) ?: ArrayList()
            adapter.setData(bawlingData)
        }

        return binding.root
    }

    companion object {
        private const val ARG_BAWLING_DATA = "bawling_data"

        fun newInstance(bawlingData: ArrayList<Bowling>): BallingStatsFragment {
            val fragment = BallingStatsFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_BAWLING_DATA, bawlingData)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
