package com.mindgeeks.sportsnews.Screens.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindgeeks.sportsnews.Adapter.ScoreCardAdapterBalling
import com.mindgeeks.sportsnews.Adapter.ScoreCardAdapterBatting
import com.mindgeeks.sportsnews.Model.OtherModel.ScoreCard
import com.mindgeeks.sportsnews.databinding.FragmentScoreCardBinding

class ScoreCardFragment : Fragment() {
    lateinit var data : ScoreCard
    lateinit var binding : FragmentScoreCardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentScoreCardBinding.inflate(layoutInflater)
        val view = binding.root

        val bundle = arguments

        try {
            if (bundle != null) {
                val tempData = bundle.getParcelable<ScoreCard>("scoreData")
                if (tempData is ScoreCard) {
                    data = tempData
                    binding.teamAName.text = data.matchData.teama_short_name
                    binding.teamBName.text = data.matchData.teamb_short_name

                    binding.teamAScore.text = data.matchData.teama_scores
                    binding.teamBScore.text = data.matchData.teamb_scores

                    binding.teamABatting.Totalh2.text = data.teamABattingTotalScore.total_runs.toString()
                    binding.teamABatting.Totalh3.text = data.teamABattingTotalScore.total_balls.toString()
                    binding.teamABatting.Totalh4.text = data.teamABattingTotalScore.total_fours.toString()
                    binding.teamABatting.Totalh5.text = data.teamABattingTotalScore.total_sixes.toString()
                    binding.teamABatting.Totalh6.text = formatStrickRate(data.teamABattingTotalScore.average_strike_rate)

                    binding.teamABalling.Totalh2.text = data.teamABowlingTotalScore.total_runs.toString()
                    binding.teamABalling.Totalh3.text = data.teamABowlingTotalScore.total_balls.toString()
                    binding.teamABalling.Totalh4.text = data.teamABowlingTotalScore.total_fours.toString()
                    binding.teamABalling.Totalh5.text = data.teamABowlingTotalScore.total_sixes.toString()
                    binding.teamABalling.Totalh6.text = formatStrickRate(data.teamABowlingTotalScore.average_strike_rate)

                    binding.teamBBatting.Totalh2.text = data.teamBBattingTotalScore.total_runs.toString()
                    binding.teamBBatting.Totalh3.text = data.teamBBattingTotalScore.total_balls.toString()
                    binding.teamBBatting.Totalh4.text = data.teamBBattingTotalScore.total_fours.toString()
                    binding.teamBBatting.Totalh5.text = data.teamBBattingTotalScore.total_sixes.toString()
                    binding.teamBBatting.Totalh6.text = formatStrickRate(data.teamBBattingTotalScore.average_strike_rate ).toString()

                    binding.teamBBalling.Totalh2.text = data.teamBBowlingTotalScore.total_runs.toString()
                    binding.teamBBalling.Totalh3.text = data.teamBBowlingTotalScore.total_balls.toString()
                    binding.teamBBalling.Totalh4.text = data.teamBBowlingTotalScore.total_fours.toString()
                    binding.teamBBalling.Totalh5.text = data.teamBBowlingTotalScore.total_sixes.toString()
                    binding.teamBBalling.Totalh6.text = formatStrickRate(data.teamBBowlingTotalScore.average_strike_rate ).toString()

                    setHeading()
                    binding.teamABalling.rv.adapter = ScoreCardAdapterBalling(data.teamsScoreCard.teama.bowlers)
                    binding.teamBBalling.rv.adapter = ScoreCardAdapterBalling(data.teamsScoreCard.teamb.bowlers)
                    binding.teamABatting.rv.adapter = ScoreCardAdapterBatting(data.teamsScoreCard.teama.batsmen)
                    binding.teamBBatting.rv.adapter = ScoreCardAdapterBatting(data.teamsScoreCard.teamb.batsmen)

                } else {
                    throw IllegalArgumentException("Invalid data type passed to fragment")
                }
            } else {
                throw IllegalArgumentException("No data passed to fragment")
            }
        }catch (e : Exception)
        {
            Log.e("ScoreCard","exception found ${e.message}")
        }

        return view
    }

    private fun setHeading() {

        binding.teamABalling.h1.text = "BALLING"
        binding.teamABalling.h2.text = "O"
        binding.teamABalling.h3.text = "M"
        binding.teamABalling.h4.text = "R"
        binding.teamABalling.h5.text = "W"
        binding.teamABalling.h6.text = "Econ"

        binding.teamBBalling.h1.text = "BALLING"
        binding.teamBBalling.h2.text = "O"
        binding.teamBBalling.h3.text = "M"
        binding.teamBBalling.h4.text = "R"
        binding.teamBBalling.h5.text = "W"
        binding.teamBBalling.h6.text = "Econ"
    }

    fun formatStrickRate(data: Double):String{
        val averageStrikeRate = data
        val integerPart = averageStrikeRate.toInt()
        val integerPartAsString = integerPart.toString()

// Take the first three characters
        val firstThreeChars = if (integerPartAsString.length > 3) {
            integerPartAsString.take(3)
        } else {
            integerPartAsString
        }
        return "$firstThreeChars.00"
    }
}