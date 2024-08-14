package com.mindgeeks.sportsnews.Screens.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Fragments.LiveMatchFragment
import com.mindgeeks.sportsnews.databinding.ActivityMatchDetailBinding

class MatchrDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityMatchDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.include2.titile.text = "Match Details"

        binding.include2.backBtn.setOnClickListener {
            onBackPressed()
        }
        val bundle = Bundle()
        try {
            val match_id = intent.getStringExtra("MatchId")
            val competition_id = intent.getStringExtra("CompId")
            bundle.putString("MatchId",match_id)
            bundle.putString("CompId",competition_id)
            bundle.putSerializable("action","3")
        }catch (e :Exception)
        {
            Toast.makeText(this,"exception ${e.message}",Toast.LENGTH_SHORT).show()
        }
        val liveMatchFragment = LiveMatchFragment()
        liveMatchFragment.setArguments(bundle)

        val fragmentManager = supportFragmentManager
        val fratmentTransaction = fragmentManager.beginTransaction()

        fratmentTransaction.replace(R.id.matchDetail,liveMatchFragment).commit()
    }
}