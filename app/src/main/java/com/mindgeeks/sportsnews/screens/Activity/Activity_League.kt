package com.mindgeeks.sportsnews.screens.Activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mindgeeks.sportsnews.adapters.adapter4LeagueMatches
import com.mindgeeks.sportsnews.models.RequestModels.GetLeagDataRequest
import com.mindgeeks.sportsnews.models.ResponseModel.Team
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.ActivityLeagueBinding
import com.squareup.picasso.Picasso


class Activity_League : AppCompatActivity() {
   private  lateinit var binding: ActivityLeagueBinding
   private  lateinit var viewmodelMain : ViewModel_Main
    private lateinit var sessionManager: SessionManager
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeagueBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewmodelMain = ViewModel_Main(this)
        sessionManager = SessionManager(this)
        fragmentManager = supportFragmentManager


        binding.shimmer.startShimmer()
 
 
            val image = intent.getStringExtra("LeagueImage")
            val name = intent.getStringExtra("LeagueName")
            Picasso.get().load(image).into(binding.leagIcon)
            binding.appbar.titile.text  = name.toString()
        val leagueId = intent.getStringExtra("LeagueId")?: -1
        if(leagueId != -1)
        {
            val request = GetLeagDataRequest(
                userId = sessionManager.GetValue(Constants.USER_ID).toString(),
                securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
                versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
                versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
                leagueId = leagueId.toString()
            )
            viewmodelMain.GetLeagueData(request).observe(this@Activity_League){
                if(it.status == 200)
                {
                    binding.leagMatchesRV.adapter = adapter4LeagueMatches(this,fragmentManager,it.teams as ArrayList<Team>)
                    binding.mainll.visibility = View.VISIBLE
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE

                }else{
                    binding.shimmer.stopShimmer()
                    binding.shimmer.visibility = View.GONE
                    binding.DNFLL.visibility =View.VISIBLE
                }
            }
        }else{
            Toast.makeText(this,"Something Went Wrong Try Again Later !!",Toast.LENGTH_LONG).show()
        }
        binding.appbar.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}