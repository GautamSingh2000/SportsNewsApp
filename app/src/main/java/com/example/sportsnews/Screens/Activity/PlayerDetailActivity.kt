package com.mindgeeks.sportsnews.Screens.Activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mindgeeks.sportsnews.Adapter.PlayerStatsViewPagerAdapter
import com.mindgeeks.sportsnews.Adapter.SimmilarPlayerViewPagerAdapter
import com.mindgeeks.sportsnews.Model.RequestModels.GetPlayerDetailRequest
import com.mindgeeks.sportsnews.Model.RequestModels.PlayerSearchRequest
import com.mindgeeks.sportsnews.Model.ResponseModel.Batting
import com.mindgeeks.sportsnews.Model.ResponseModel.Bowling
import com.mindgeeks.sportsnews.Model.ResponseModel.PlayerData
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.CountryISO
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.ViewModels.MainViewModel
import com.mindgeeks.sportsnews.databinding.ActivityPlayerDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import java.time.LocalDate
import java.time.Period
import kotlin.math.abs

class PlayerDetailActivity : AppCompatActivity() {
    private var SimmilarPlayerList: ArrayList<PlayerData> = ArrayList()
    private var rotate = false
    private lateinit var binding: ActivityPlayerDetailBinding
    lateinit var context: Context
    lateinit var viewModel: MainViewModel
    lateinit var sessionManager: SessionManager
    lateinit var adapter: PlayerStatsViewPagerAdapter

    var BawlingData: ArrayList<Bowling> = ArrayList()
    var BattingData: ArrayList<Batting> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this
        sessionManager = SessionManager(this)
        adapter = PlayerStatsViewPagerAdapter(this)
        binding.playerdetialShimmer.startShimmer()
        binding.appbar.titile.text = "Player Detail"
        binding.appbar.backBtn.setOnClickListener {
            onBackPressed()
        }

        viewModel = MainViewModel(this)
        binding.viewpager.adapter = adapter // Set the adapter here

        val playerid = intent.getStringExtra("playerId") ?: return
        Log.e("PLayerDetailActivity", playerid.toString())
        if (playerid != null) {
            val request = GetPlayerDetailRequest(
                userId = sessionManager.GetValue(Constants.USER_ID).toString(),
                securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
                versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
                versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
                playerId = playerid
            )

            viewModel.GetPlayerDetail(request = request).observe(this@PlayerDetailActivity) {
                when {
                    it.status == 200 -> {
                        binding.playerName.text =
                            "${it.playerInfo.get(0).playerInfo.first_name} \n ${it.playerInfo.get(0).playerInfo.last_name}"

                        binding.age.text = when {
                            it.playerInfo.get(0).playerInfo.birthdate.isNotEmpty() &&
                                    !it.playerInfo.get(0).playerInfo.birthdate.equals("--") -> {
                                "${calculateAge(it.playerInfo.get(0).playerInfo.birthdate)} YEARS"
                            }

                            else -> {
                                " -- "
                            }
                        }
                        binding.nationality.text = when {
                            it.playerInfo.get(0).playerInfo.nationality.isNotEmpty() &&
                                    !it.playerInfo.get(0).playerInfo.nationality.equals("--") -> {
                                it.playerInfo.get(0).playerInfo.nationality
                            }

                            else -> {
                                " -- "
                            }
                        }
                        Picasso.get().load(it.playerInfo.get(0).playerInfo.countryFlag).into(binding.flag)
                        binding.battingStyle.text = it.playerInfo.get(0).playerInfo.batting_style
                        binding.bawlingStyle.text = it.playerInfo.get(0).playerInfo.bowling_style

                        BattingData = it.playerInfo.get(0).batting as ArrayList<Batting>
                        BawlingData = it.playerInfo.get(0).bowling as ArrayList<Bowling>
                        adapter.setData(
                            battingList = BattingData,
                            bowlingList = BawlingData,
                            context = this
                        )
                        try {
                            binding.playerName.text =
                                "${it.playerInfo.get(0).playerInfo.first_name}"

                            binding.age.text = when {
                                        !it.playerInfo.get(0).playerInfo.birthdate.equals("--") -> {
                                    "${calculateAge(it.playerInfo.get(0).playerInfo.birthdate)} YEARS"
                                }
                                else -> {
                                    " -- "
                                }
                            }
                            binding.nationality.text = when {
                                it.playerInfo.get(0).playerInfo.nationality.isNotEmpty() &&
                                        !it.playerInfo.get(0).playerInfo.nationality.equals("--") -> {
                                    it.playerInfo.get(0).playerInfo.nationality
                                }

                                else -> {
                                    " -- "
                                }
                            }
                            Picasso.get().load(it.playerInfo.get(0).playerInfo.countryFlag)
                                .into(binding.flag)
                            binding.battingStyle.text =
                                it.playerInfo.get(0).playerInfo.batting_style
                            binding.bawlingStyle.text =
                                it.playerInfo.get(0).playerInfo.bowling_style

                            BattingData = it.playerInfo.get(0).batting as ArrayList<Batting>
                            BawlingData = it.playerInfo.get(0).bowling as ArrayList<Bowling>
                            adapter.setData(
                                battingList = BattingData,
                                bowlingList = BawlingData,
                                context = this
                            )
                            try {
                                GetSimmilarPlayer(binding.nationality.text.toString())
                            } catch (e: Exception) {
                                Log.e(
                                    "PLayerdetail",
                                    "exception found while simmilarplayers ${e.message}"
                                )
                            }
                        }catch (e : Exception)
                        {
                            Log.e("PLayerdetail","error in trycatch ${e.message}")
                            Toast.makeText(this, "error in trycatch ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this, "Try again later !!!", Toast.LENGTH_SHORT).show()
        }

        val tabLayout: TabLayout = binding.tabll
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        TabLayoutMediator(tabLayout, binding.viewpager) { tab, position ->
            tab.text = when (position) {
                0 -> "Batting Stats"
                1 -> "Bowling Stats"
                else -> null
            }
        }.attach()


        setCameraDistance(binding.playerImage)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

                val anim = if (rotate) {
                    Picasso.get().load(R.drawable.batsman).into( binding.playerImage)
                    AnimatorInflater.loadAnimator(context, R.animator.flip_right_y_axis) as Animator
                } else {
                    Picasso.get().load(R.drawable.bawler).into( binding.playerImage)
                    AnimatorInflater.loadAnimator(context, R.animator.flip_left_y_axis) as Animator
                }
                rotate = !rotate

                val animation = AlphaAnimation(0.0f, 1.0f)
                animation.duration = 300 // duration of the animation
                binding.viewpager.startAnimation(animation)

                anim.setTarget(binding.playerImage)
                anim.start()

                anim.addListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {
                        binding.playerImage.visibility = View.VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        binding.playerImage.visibility = View.VISIBLE
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        binding.playerImage.visibility = View.VISIBLE
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                    }
                })
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Optional: Add animation for unselected tab
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Optional: Add animation for reselected tab
            }
        })
    }

    private fun GetSimmilarPlayer(nationality: String) {
        Log.e("PlayerDetail","nationality is ${nationality}")
        val isoCode = getIsoCodeFromNationality(nationality)
        Log.e("PlayerDetail","iso code is $isoCode")

        val request = PlayerSearchRequest(
            page = "1",
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            nationality = isoCode
        )

        Log.e("PlayerDetail","nationality is ${request.nationality}")

        viewModel.PlayerSearch(request).observe(this) {
            if (it.status == 200) {
                try {
                    SimmilarPlayerList = it.allPlayers as ArrayList<PlayerData>
                    SimmilarPlayerList.shuffle()
                    val compositePageTransformer = CompositePageTransformer()
                    compositePageTransformer.addTransformer(MarginPageTransformer(20))
                    compositePageTransformer.addTransformer { page, position ->
                        val r = (1 - abs(position.toDouble())).toFloat()
                        page.scaleY = 0.85f + r * 0.15f
                    }

                    binding.simmilarplayers.adapter =
                        SimmilarPlayerViewPagerAdapter(this, SimmilarPlayerList)
                    binding.simmilarplayers.clipToPadding = false
                    binding.simmilarplayers.setClipChildren(false)
                    binding.simmilarplayers.setOffscreenPageLimit(4)
                    if (binding.simmilarplayers.childCount > 1) {
                        binding.simmilarplayers.getChildAt(1).overScrollMode =
                            RecyclerView.OVER_SCROLL_ALWAYS
                    }
                    binding.simmilarplayers.setPageTransformer(compositePageTransformer)
                    binding.playerdetialShimmer.stopShimmer()
                    binding.playerdetialShimmer.visibility = View.GONE
                    binding.mainll.visibility = View.VISIBLE

                }catch (e :Exception)
                {
                    Log.e("PlayerDetail","Error while fetching fata in PlayerDetail activity !!")
                    Toast.makeText(context,
                        "Problem To Load Data !!",
                        Toast.LENGTH_SHORT).show()
                }
            }else {
                binding.simmilarplayers.visibility = View.GONE
                binding.simmilar.visibility = View.GONE
                binding.materialDivider3.visibility = View.GONE
            }
        }
    }



    private fun setCameraDistance(view: View) {
        val scale = resources.displayMetrics.density
        view.cameraDistance = 8000 * scale
    }

    fun calculateAge(dob: String): Int {
        val birthDate = LocalDate.parse(dob)
        val currentDate = LocalDate.now()
        val agePeriod = Period.between(birthDate, currentDate)
        return agePeriod.years
    }

    private fun getIsoCodeFromNationality(nationality: String): String {
        val nationalityUpper = nationality.uppercase()
        for (country in CountryISO::class.java.enumConstants) {
            if (country.name.equals(nationalityUpper, ignoreCase = true)) {
                return country.code
            }
        }
        return ""
    }
}

