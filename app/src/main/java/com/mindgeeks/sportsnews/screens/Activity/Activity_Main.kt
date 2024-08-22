package com.mindgeeks.sportsnews.screens.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mindgeeks.sportsnews.models.RequestModels.ProfileREquest
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.CordinateMainActivityBinding
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Home
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Player
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Search
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Shots
import com.mindgeeks.sportsnews.screens.Fragments.Fragment_Update
import com.squareup.picasso.Picasso


class Activity_Main : AppCompatActivity() {
    private lateinit var viewModelMain: ViewModel_Main
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
        viewModelMain = ViewModel_Main(context)
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

        binding.terms.setOnClickListener {
            openTab("https://cricnews.app/terms_n_conditions.html")
        }
        binding.about.setOnClickListener {
            openTab("https://cricnews.app/about.html")
        }
        binding.help.setOnClickListener {
            openTab("https://cricnews.app/help.html")
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
                    Replace(Fragment_Home(), action = screen_action)
                }

                R.id.Search -> {
                    menuItem.setIcon(R.drawable.filledsearch) // If you have a selected icon for search
                    Replace(Fragment_Search(), action = screen_action)
                }


                R.id.update -> {
                    menuItem.setIcon(R.drawable.filled_update) // If you have a selected icon for update
                    Replace(Fragment_Update(), action = screen_action)
                }

                R.id.shots -> {
                    menuItem.setIcon(R.drawable.filled_shot) // If you have a selected icon for the default case
                    Replace(Fragment_Shots(), action = screen_action)
                }

                R.id.players -> {
                    menuItem.setIcon(R.drawable.selectedplayer) // If you have a selected icon for the default case
                    Replace(Fragment_Player(), action = screen_action)
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
        viewModelMain.ProfileDetail(request).observe(this@Activity_Main) {
            if (it.status == 200) {
                Picasso.get().load(it.profilePic).into(binding.profilepic)
                binding.name.text = it.name
                binding.email.text = it.email

            } else {
                Toast.makeText(this@Activity_Main, "Something Went Wrong !!", Toast.LENGTH_SHORT)
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
                .setCustomAnimations(R.anim.screen_right_in, R.anim.screen_left_out)
        } else {
            fratmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.screeen_left_in,R.anim.screen_right_out)
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
                AnimationUtils.loadAnimation(context, R.anim.right_slide_out_anim)
            val animation2: Animation =
                AnimationUtils.loadAnimation(context, R.anim.left_slide_out_anim)

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
                AnimationUtils.loadAnimation(context, R.anim.right_slide_in_anim)
            val animation2: Animation =
                AnimationUtils.loadAnimation(context, R.anim.left_slide_in_anim)

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


    fun openTab(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(this, R.color.gradient_red))
            builder.setDefaultColorSchemeParams(params.build())
            // shows the title of web-page in toolbar
            builder.setShowTitle(true)

            // setShareState(CustomTabsIntent.SHARE_STATE_ON) will add a menu to share the web-page
            builder.setShareState(CustomTabsIntent.SHARE_STATE_ON)

            // To modify the close button, use
            // builder.setCloseButtonIcon(bitmap)

            // to set weather instant apps is enabled for the custom tab or not, use
            builder.setInstantAppsEnabled(true)

            //  To use animations use -
            //  builder.setStartAnimations(this, android.R.anim.start_in_anim, android.R.anim.start_out_anim)
            //  builder.setExitAnimations(this, android.R.anim.exit_in_anim, android.R.anim.exit_out_anim)
            val customBuilder = builder.build()
            customBuilder.intent.setPackage("com.android.chrome")
            customBuilder.launchUrl(this, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }
    }


}