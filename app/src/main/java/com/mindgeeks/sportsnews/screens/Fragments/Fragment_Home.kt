package com.mindgeeks.sportsnews.screens.Fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.mindgeeks.sportsnews.adapters.adapter_LiveMatches
import com.mindgeeks.sportsnews.adapters.adapter_TopLeagur
import com.mindgeeks.sportsnews.adapters.adapter_TrendingNews
import com.mindgeeks.sportsnews.adapters.adapter_UpcomingMatch
import com.mindgeeks.sportsnews.models.ResponseModel.League
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.FragmentHomeBinding
import kotlin.math.abs

class Fragment_Home : Fragment() {


    lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelMain: ViewModel_Main
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModelMain = ViewModel_Main(requireContext())
        sessionManager = SessionManager(requireContext())

        binding.loaderHomeFragment.rc.rc.startShimmer()
        binding.loaderHomeFragment.liveCardView.liveCardView.startShimmer()
        binding.loaderHomeFragment.text.text.startShimmer()
        binding.loaderHomeFragment.text0.text.startShimmer()
        binding.loaderHomeFragment.text2.text.startShimmer()
        binding.loaderHomeFragment.topLeagueRv1.cardviewLeague.startShimmer()
        binding.loaderHomeFragment.topLeagueRv2.cardviewLeague.startShimmer()
        binding.loaderHomeFragment.topLeagueRv3.cardviewLeague.startShimmer()

        GetData()

        binding.refresh.setOnRefreshListener{
            binding.loading.visibility = View.VISIBLE
            binding.loading.addAnimatorListener(object : AnimatorListener{
                override fun onAnimationStart(animation: Animator) {
                    
                }

                override fun onAnimationEnd(animation: Animator) {
                    binding.loading.playAnimation()
                }

                override fun onAnimationCancel(animation: Animator) {
                    
                }

                override fun onAnimationRepeat(animation: Animator) {
                    
                }

            })
            binding.loaderHomeFragment.rc.rc.startShimmer()
            binding.loaderHomeFragment.liveCardView.liveCardView.startShimmer()
            binding.loaderHomeFragment.text.text.startShimmer()
            binding.loaderHomeFragment.text0.text.startShimmer()
            binding.loaderHomeFragment.text2.text.startShimmer()
            binding.loaderHomeFragment.topLeagueRv1.cardviewLeague.startShimmer()
            binding.loaderHomeFragment.topLeagueRv2.cardviewLeague.startShimmer()
            binding.loaderHomeFragment.topLeagueRv3.cardviewLeague.startShimmer()
            binding.loaderHomeFragment.shimmerMainll.visibility = View.VISIBLE
            binding.homeFragmentMainLl.visibility = View.GONE
            GetData()
        }
        return binding.root
    }

    private fun GetData() {

        Log.e(tag,"Fetching data")

        viewModelMain.getHomeFragmentData(
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString()
        ).observe(viewLifecycleOwner) {
            binding.refresh.isRefreshing = false
            binding.loading.visibility = View.GONE
            if (it.status != 200) {
                Toast.makeText(requireContext(), "Please try again later!", Toast.LENGTH_SHORT)
                    .show()
            } else {

                binding.topLeagueRv.adapter = adapter_TopLeagur(requireContext(),it.leagues as ArrayList<League>)
                //for upcoming matches
                binding.rc.adapter = adapter_UpcomingMatch(it.upcomingMatches)
                binding.rc.clipToPadding = false
                binding.rc.setClipChildren(false)
                binding.rc.setOffscreenPageLimit(4)
                binding.rc.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)

                val compositePageTransformer = CompositePageTransformer()
                compositePageTransformer.addTransformer(MarginPageTransformer(20))
                compositePageTransformer.addTransformer { page, position ->
                    val r = (1 - abs(position.toDouble())).toFloat()
                    page.scaleY = 0.85f + r * 0.15f
                }

                binding.rc.setPageTransformer(compositePageTransformer)


                binding.trendingVp.adapter = adapter_TrendingNews(requireContext(), it.trendingNews)
                binding.trendingVp.clipToPadding = false
                binding.trendingVp.setClipChildren(false)
                binding.trendingVp.setOffscreenPageLimit(4)
                binding.trendingVp.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_ALWAYS)

                binding.trendingVp.setPageTransformer(compositePageTransformer)


                binding.liveCardView.adapter =
                    adapter_LiveMatches(it.liveMatches, parentFragmentManager)
                binding.liveCardView.clipToPadding = false
                binding.liveCardView.setClipChildren(false)
                binding.liveCardView.setOffscreenPageLimit(4)
                binding.liveCardView.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)
                binding.liveCardView.setPageTransformer(compositePageTransformer)


                binding.loaderHomeFragment.rc.rc.stopShimmer()
                binding.loaderHomeFragment.liveCardView.liveCardView.stopShimmer()
                binding.loaderHomeFragment.text.text.stopShimmer()
                binding.loaderHomeFragment.text0.text.stopShimmer()
                binding.loaderHomeFragment.text2.text.stopShimmer()
                binding.loaderHomeFragment.topLeagueRv1.cardviewLeague.stopShimmer()
                binding.loaderHomeFragment.topLeagueRv2.cardviewLeague.stopShimmer()
                binding.loaderHomeFragment.topLeagueRv3.cardviewLeague.stopShimmer()
                binding.loaderHomeFragment.shimmerMainll.visibility = View.GONE
                binding.homeFragmentMainLl.visibility = View.VISIBLE
            }
        }
    }


}