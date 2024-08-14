package com.mindgeeks.sportsnews.Screens.Fragments

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieDrawable
import com.mindgeeks.sportsnews.Adapter.LiveMatchMainTabLayoutAdapter
import com.mindgeeks.sportsnews.Model.OtherModel.ScoreCard
import com.mindgeeks.sportsnews.Model.ResponseModel.MatchData
import com.mindgeeks.sportsnews.Model.ResponseModel.TeamPlayer
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.ViewModels.MainViewModel
import com.mindgeeks.sportsnews.databinding.FragmentLiveMatchBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class LiveMatchFragment : Fragment() {


    lateinit var mainViewModel: MainViewModel
    lateinit var binding: FragmentLiveMatchBinding
    private lateinit var sessionManager: SessionManager
    var matchid: String = "null"
    var compid: String = "null"

    val TabTitle = listOf("Squad","Score Card")
    private lateinit var listA : ArrayList<TeamPlayer>
    private lateinit var listB : ArrayList<TeamPlayer>
    private lateinit var TeamNames : ArrayList<String>
    private lateinit var scoreList :MatchData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        activity?.findViewById<TextView>(R.id.title)?.text = "Live Match"
        binding = FragmentLiveMatchBinding.inflate(layoutInflater)
        mainViewModel = MainViewModel(requireContext())
        sessionManager = SessionManager(requireContext())

        binding.shimmerMainll.livematchShimmer.startShimmer()

        TeamNames = ArrayList()  // Initialize the list
        listA = ArrayList()     // Initialize the list
        listB = ArrayList()

        val data = arguments?.getString("action")
        matchid = arguments?.getString("MatchId", "null").toString()
        compid = arguments?.getString("CompId", "null").toString()

        Log.e("LiveMatchFragment", "action is $data and matchid $matchid compid $compid")

        if (data.equals("1")) LiveIcon()
        else{
            if(data.equals("2"))
            {
                binding.livematchcv.matchState.text = "Match Details"
            }else{
                binding.livematchcv.matchState.text = "Match History"
            }
        }

        when{
            data.equals("1") -> {LiveIcon()}
            data.equals("2") -> {  binding.livematchcv.matchState.text = "Match Details" }
            data.equals("3") -> {binding.livematchcv.matchState.text = "Match History" }
        }
        binding.livematchcv.nextbtn.visibility = View.GONE
        GetData()
        return binding.root
    }

    private fun GetData() {
        mainViewModel.getLiveMatchData(
            deviceId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            matchId = matchid,
            compId = compid
        ).observe(viewLifecycleOwner) {
            when (it.status) {
                200 -> {
                    TeamNames.add(it.matchData.teama_name)
                    TeamNames.add(it.matchData.teamb_name)

                    Picasso.get().load(it.matchData.teama_logo).into(binding.livematchcv.teamOnelogo)
                    Picasso.get().load(it.matchData.teamb_logo).into(binding.livematchcv.teamTwologo)

                    binding.livematchcv.teamOneName.text = it.matchData.teama_name
                    binding.livematchcv.teamTwoName.text = it.matchData.teamb_name
                    try {
                        if (it.matchData.teama_scores.equals("") || it.matchData.teama_scores.equals(
                                ""
                            )
                        ) {
                            binding.livematchcv.teamOneRuns.text = " -- "
                            binding.livematchcv.teamOneOvers.text = " -- "
                            binding.livematchcv.teamTwoRuns.text = " -- "
                            binding.livematchcv.teamTwoOvers.text = " -- "
                        } else {
                            val input1 = it.matchData.teama_scores
                            val parts1 = input1.split(" (")
                            val input2 = it.matchData.teamb_scores
                            val parts2 = input2.split(" (")

                            binding.livematchcv.teamOneRuns.text = parts1[0]
                            binding.livematchcv.teamOneOvers.text =
                                "(${parts1[1].removeSuffix(")")})"
                            binding.livematchcv.teamTwoRuns.text = parts2[0]
                            binding.livematchcv.teamTwoOvers.text =
                                "(${parts2[1].removeSuffix(")")})"
                        }
                    }catch (e :Exception)
                    {
                        Log.e(tag,"exception found ${e.message}")
                        binding.livematchcv.teamOneRuns.text = " -- "
                        binding.livematchcv.teamOneOvers.text = " -- "
                        binding.livematchcv.teamTwoRuns.text = " -- "
                        binding.livematchcv.teamTwoOvers.text = " -- "
                    }

                    binding.livematchcv.leag.text = it.matchData.match_type

                    when (it.matchData.teama_players.size) {
                        0 -> {

                        }

                        else -> {
                            listA = it.matchData.teama_players as ArrayList<TeamPlayer>
                        }
                    }

                    when (it.matchData.teamb_players.size) {
                        0 -> {

                        }

                        else -> {
                            listB = it.matchData.teamb_players as ArrayList<TeamPlayer>
                        }
                    }

                    val scoreCard = ScoreCard(
                        matchData = it.matchData,
                        teamsScoreCard = it.matchData.scores,
                        teamABattingTotalScore = it.teama__batsman_total,
                        teamABowlingTotalScore = it.teama__bowlers_total,
                        teamBBattingTotalScore = it.teamb__batsman_total,
                        teamBBowlingTotalScore = it.teamb__bowlers_total,
                    )
                    setTablayout(
                        listA = listA,
                        listB = listB,
                        TeamNameList = TeamNames,
                        scoreList = scoreCard
                    )

                }

                else -> {
                    Toast.makeText(
                        context,
                        "Something went wrong! Try again later!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    fun setTablayout(listA: ArrayList<TeamPlayer>, listB: ArrayList<TeamPlayer>, TeamNameList : ArrayList<String>
                     ,scoreList : ScoreCard
    ) {
        val adapter = LiveMatchMainTabLayoutAdapter(this)
        adapter.sendData(
            listA = listA,
            listB = listB,
            teamsName = TeamNameList,
            scoreList = scoreList
        )

        binding.viewpager.adapter = adapter
        TabLayoutMediator(binding.tabll, binding.viewpager) { tab, position ->
            tab.text = TabTitle[position]
        }.attach()

        for (i in 0..1) {
            val text =
                LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null) as TextView
            binding.tabll.getTabAt(i)?.customView = text
        }

        binding.shimmerMainll.livematchShimmer.stopShimmer()
        binding.shimmerMainll.livematchShimmer.visibility = View.GONE
        binding.mainll.visibility = View.VISIBLE
    }


    fun LiveIcon() {
        binding.livematchcv.liveIcon.apply {
            visibility = View.VISIBLE
            repeatMode = LottieDrawable.RESTART
            playAnimation()
            addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    // Do nothing
                }

                override fun onAnimationEnd(animation: Animator) {
                    playAnimation() // Restart the animation
                }

                override fun onAnimationCancel(animation: Animator) {
                    // Do nothing
                }

                override fun onAnimationRepeat(animation: Animator) {
                    // Do nothing
                }
            })
        }
    }
}