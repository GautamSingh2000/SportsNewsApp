package com.mindgeeks.sportsnews.Screens.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mindgeeks.sportsnews.Model.RequestModels.ProfileREquest
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Fragments.HomeFragment
import com.mindgeeks.sportsnews.Screens.Fragments.PlayerFragment
import com.mindgeeks.sportsnews.Screens.Fragments.SearchFragment
import com.mindgeeks.sportsnews.Screens.Fragments.ShotsFragment
import com.mindgeeks.sportsnews.Screens.Fragments.UpdateFragment
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.ViewModels.MainViewModel
import com.mindgeeks.sportsnews.databinding.CordinateMainActivityBinding
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sessionManager: SessionManager

    var lottiState = false
    lateinit var fragmentManager: FragmentManager
    private lateinit var backStackChangedListener: FragmentManager.OnBackStackChangedListener

    lateinit var binding: CordinateMainActivityBinding
    lateinit var context: Context
    private var previousMenuItem: MenuItem? = null
    var BackStackCount = 0
    var current_iteam = 0
    var previous_iteam= 0
    var screen_action = ""
    lateinit var fratmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CordinateMainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        context = this
        mainViewModel = MainViewModel(context)
        sessionManager = SessionManager(context)

        backStackChangedListener = FragmentManager.OnBackStackChangedListener {
            val backStackCount = fragmentManager.backStackEntryCount
            BackStackCount(backStackCount)
            BackStackCount = backStackCount
        }

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.droorbackBtn.setOnClickListener {
            SideMenu()
        }


        binding.rate.setOnClickListener {
            val packageName = packageName
            val packageManager = packageManager
            val installerPackageName = packageManager.getInstallerPackageName(packageName)

            if (installerPackageName != null && installerPackageName == "com.android.vending") {
                try {
                    val manager = ReviewManagerFactory.create(this)
                    val request = manager.requestReviewFlow()
                    request.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // We got the ReviewInfo object
                            val reviewInfo = task.result
                            // Launch the in-app review flow
                            manager.launchReviewFlow(this, reviewInfo)
                                .addOnCompleteListener { _ ->
                                    Toast.makeText(this, "Review Complete !!", Toast.LENGTH_SHORT)
                                        .show()
                                    finish()
                                    // The review flow has finished
                                }
                        } else {
                            Toast.makeText(
                                this,
                                "${task.exception as ReviewException}",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                            @ReviewErrorCode val reviewErrorCode =
                                (task.exception as ReviewException).errorCode
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "Something Went Wrong !!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "App not installed from Google Play Store", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.share.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, sessionManager.GetValue(Constants.APP_URL))
                type = "text/plain"
            }
            val chooser = Intent.createChooser(shareIntent, "Share link via")
            if (shareIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(chooser)
            }
        }
        GetProfileData()

        binding.switchMaterial.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                false -> {
                    binding.notification.setImageDrawable(context.getDrawable(R.drawable.bellof))
                    Toast.makeText(applicationContext, "Notification off", Toast.LENGTH_SHORT)
                        .show()
                }

                true -> {
                    Toast.makeText(applicationContext, "Notification on", Toast.LENGTH_SHORT).show()
                    binding.notification.setImageDrawable(context.getDrawable(R.drawable.bellon))
                }
            }
        }

        binding.name.setOnClickListener {
            val i = Intent(context, ProfileActivity::class.java)
            startActivity(i)
        }
        binding.terms.setOnClickListener {
            val i = Intent(context, Empty_webLinktab::class.java)
            i.putExtra("Link", "https://cricnews.app/terms_n_conditions.html")
            i.putExtra("Heading", "Terms and Condition")
            context.startActivity(i)
        }
        binding.about.setOnClickListener {
            val i = Intent(context, Empty_webLinktab::class.java)
            i.putExtra("Link", "https://cricnews.app/about.html")
            i.putExtra("Heading", "About")
            context.startActivity(i)
        }
        binding.help.setOnClickListener {
            val i = Intent(context, Empty_webLinktab::class.java)
            i.putExtra("Link", "https://cricnews.app/help.html")
            i.putExtra("Heading", "Help")
            context.startActivity(i)
        }

        binding.lottieView.setOnClickListener {
            SideMenu()
        }



        binding.mainlayout.bottomNav.setOnItemSelectedListener { menuItem ->
            previousMenuItem?.let {
                when (it.itemId) {
                    R.id.home -> it.setIcon(R.drawable.unselectedhome) // Set the default icon
                    R.id.Search -> it.setIcon(R.drawable.holosearch) // Set the default icon
                    R.id.update -> it.setIcon(R.drawable.holo_update) // Set the default icon
                    R.id.shots -> it.setIcon(R.drawable.holoshot)
                    R.id.players -> it.setIcon(R.drawable.unselectedplayer)
                    else -> {}
                }
            }

            current_iteam = getItemSequenceNumber(menuItem.title.toString())
            sessionManager.Initialize_Current_no(current_iteam)
            if(current_iteam > previous_iteam) screen_action = "current_iteam"
            else screen_action = "previous_iteam"

            when (menuItem.itemId) {
                R.id.home -> {
                    menuItem.setIcon(R.drawable.selectedhome)
                    binding.mainlayout.title.text = "Welcome"
                    Replace(HomeFragment(), action = screen_action)
                }

                R.id.Search -> {
                    menuItem.setIcon(R.drawable.filledsearch) // If you have a selected icon for search
                    Replace(SearchFragment(), action = screen_action)
                }


                R.id.update -> {
                    menuItem.setIcon(R.drawable.filled_update) // If you have a selected icon for update
                    Replace(UpdateFragment(), action = screen_action)
                }

                R.id.shots -> {
                    menuItem.setIcon(R.drawable.filled_shot) // If you have a selected icon for the default case
                    Replace(ShotsFragment(), action = screen_action)
                }

                R.id.players -> {
                    menuItem.setIcon(R.drawable.selectedplayer) // If you have a selected icon for the default case
                    Replace(PlayerFragment(), action = screen_action)
                }

                else -> {}
            }

            // Store the currently selected menu item as the previous item
            previousMenuItem = menuItem

            return@setOnItemSelectedListener true
        }

        if (savedInstanceState == null) {
            binding.mainlayout.bottomNav.selectedItemId = R.id.home
        }

    }

    private fun getItemSequenceNumber(itemId: String): Int {
        // Get the menu and find the MenuItem
        for (i in 0 until binding.mainlayout.bottomNav.menu.size()) {
            Log.e("value", "Item $i is ${binding.mainlayout.bottomNav.menu.get(i).title}")
            if (binding.mainlayout.bottomNav.menu.get(i).title?.equals(itemId) == true) {
                Log.e("value", "Item selected in navigation bar at $i")
                return i // Return the index of the MenuItem
            }
        }
        return -1 // Return -1 if the MenuItem is not found (error case)
    }

    private fun GetProfileData() {
        val request = ProfileREquest(
            userId = sessionManager.GetValue(Constants.USER_ID).toString(),
            securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
        )
        mainViewModel.ProfileDetail(request).observe(this@MainActivity) {
            if (it.status == 200) {
                Picasso.get().load(it.profilePic).into(binding.profilepic)
                binding.name.text = it.name
                binding.email.text = it.email

            } else {
                Toast.makeText(this@MainActivity, "Something Went Wrong !!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun BackStackCount(backStackCount: Int) {
        Log.e("MainActivity", "backStackCount is $backStackCount")
        if (backStackCount == 0) {
            binding.backBtn.visibility = View.GONE
            binding.mainlayout.title.text = "Welcome"
        } else {
            binding.backBtn.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove the listener when the activity is destroyed
        fragmentManager.removeOnBackStackChangedListener(backStackChangedListener)
    }

    override fun onBackPressed() {
        if (BackStackCount == 0 && lottiState) {
            SideMenu()
        } else {
            binding.backBtn.visibility = View.GONE
            super.onBackPressed()
        }

    }

    fun Replace(fragment: Fragment, action: String) {
        binding.backBtn.visibility = View.GONE
        fragmentManager = supportFragmentManager
        fragmentManager.popBackStack()

        if (action.equals("current_iteam")) {
            fratmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.acti_right_in, R.anim.acti_left_out)
        } else {
            fratmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.acti_left_in,R.anim.acti_right_out)
        }
        fragmentManager.addOnBackStackChangedListener(backStackChangedListener)
        fratmentTransaction.replace(R.id.MainScreeFrameLayout, fragment)
            .commit()

        sessionManager.Initialize_Last_no(previous_iteam)
        previous_iteam = current_iteam
    }

    fun SideMenu() {
        Log.e("Mainactivity", "lottiState is " + lottiState)
        if (lottiState) {
            lottiState = false
            binding.lottieView.apply {
                playAnimation()
            }
            val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.right_slide_out)
            val animation2: Animation =
                AnimationUtils.loadAnimation(context, R.anim.left_slide_out)

            binding.blurViewcard.apply {
                startAnimation(animation)
                isClickable = false
                isFocusableInTouchMode = false
                visibility = View.GONE
            }

            binding.mainlayout.blurView.apply {
                startAnimation(animation2)
                isClickable = false
                isFocusableInTouchMode = false
                visibility = View.GONE
            }


            Log.e("Mainactivity", "now lottiState is " + lottiState)
        } else {

            binding.lottieView.playAnimation()
            lottiState = true
            val animation1: Animation =
                AnimationUtils.loadAnimation(context, R.anim.right_slide_in)
            val animation2: Animation =
                AnimationUtils.loadAnimation(context, R.anim.left_slide_in)

            binding.mainlayout.blurView.apply {
                setBlur(context, this, 100)
                isClickable = true
                isFocusableInTouchMode = true
                visibility = View.VISIBLE
                startAnimation(animation2)
            }


            binding.blurViewcard.apply {
                isFocusableInTouchMode = true
                isClickable = true
                visibility = View.VISIBLE
                startAnimation(animation1)
            }


            Log.e("Mainactivity", "now lottiState is " + lottiState)
        }
    }
}