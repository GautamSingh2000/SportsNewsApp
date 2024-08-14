package com.mindgeeks.sportsnews.Screens.Activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.mindgeeks.sportsnews.Adapter.LeagueMatchesAdapter
import com.mindgeeks.sportsnews.Model.RequestModels.GetLeagDataRequest
import com.mindgeeks.sportsnews.Model.ResponseModel.Team
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.ViewModels.MainViewModel
import com.mindgeeks.sportsnews.databinding.ActivityLeagueBinding
import com.squareup.picasso.Picasso


class LeagueActivity : AppCompatActivity() {
   private  lateinit var binding: ActivityLeagueBinding
   private  lateinit var mainViewmodel : MainViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLeagueBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainViewmodel = MainViewModel(this)
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
            mainViewmodel.GetLeagueData(request).observe(this@LeagueActivity){
                if(it.status == 200)
                {
                    binding.leagMatchesRV.adapter = LeagueMatchesAdapter(this,fragmentManager,it.teams as ArrayList<Team>)
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