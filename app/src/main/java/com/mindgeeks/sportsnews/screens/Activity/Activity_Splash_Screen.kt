package com.mindgeeks.sportsnews.screens.Activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.databinding.ActivitySplashScreenBinding
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Activity_Splash_Screen : AppCompatActivity() {

    lateinit var context: Context
    private val SPLASH_TIME_OUT = 2000
    private var handler: Handler? = null
    private lateinit var adInfoData: String
    private lateinit var viewModelMain: ViewModel_Main

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var sessionManager: SessionManager
    lateinit var slide_up : Any
    lateinit var slide_in : Any
    lateinit var right_in : Any
    lateinit var right_out : Any
    lateinit var left_in : Any
    lateinit var left_out : Any

    private val tag = "Splashscreen"

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        supportActionBar?.apply {
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        this@Activity_Splash_Screen,
                        R.color.white
                    )
                )
            )
        }
        val view = binding.root
        setContentView(view)
        context = this
            viewModelMain = ViewModel_Main(this)
            sessionManager = SessionManager(this@Activity_Splash_Screen)

            val tokenValue = sessionManager.GetValue(Constants.SECURITY_TOKEN)

 
 

        slide_up  = AnimationUtils.loadAnimation(this,R.anim.mike_slideup_anim)
        slide_in = AnimationUtils.loadAnimation(this,R.anim.mike_slidedown_anim)
        right_in = AnimationUtils.loadAnimation(this,R.anim.right_slide_in_anim)
        right_out = AnimationUtils.loadAnimation(this,R.anim.right_slide_out_anim)
        left_in = AnimationUtils.loadAnimation(this,R.anim.left_slide_in_anim)
        left_out = AnimationUtils.loadAnimation(this,R.anim.left_slide_out_anim)

        binding.bottomMice.visibility = View.VISIBLE
        binding.rightMice.visibility = View.VISIBLE
        binding.leftMice.visibility = View.VISIBLE

        binding.bottomMice.startAnimation(slide_up as Animation?)
        binding.leftMice.startAnimation(left_in as Animation?)
        binding.rightMice.startAnimation(right_in as Animation?)


  
        coroutineScope.launch {
            try {
                val adInfo = async { getAdvertisingIdInfo() }
                val packageInfo = async { getPackageInfo() }

                adInfoData = adInfo.await()?.id ?: ""
                val versionCode = packageInfo.await().versionCode
                val versionName = packageInfo.await().versionName
                val deviceId =
                    Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
                sessionManager.InitializeValue(Constants.DEVICE_ID, deviceId)
                val deviceType = Build.MODEL
                val deviceManufacturer = Build.MANUFACTURER
                val deviceName = "$deviceManufacturer $deviceType"
                Log.e(
                    "SplashScreen",
                    "$deviceId, $deviceType, $deviceName, $adInfoData, $versionName, $versionCode"
                )
                sessionManager.InitializeValue(Constants.VERSION_CODE, versionCode.toString())
                sessionManager.InitializeValue(Constants.VERSION_NAME, versionName)
                sessionManager.InitializeValue(Constants.DEVICE_NAME, deviceName)
                sessionManager.InitializeValue(Constants.DEVICE_TYPE, deviceType)
                sessionManager.InitializeValue(Constants.ADVERTISING_ID, adInfoData)

            } catch (e: Exception) {
                Toast.makeText(this@Activity_Splash_Screen, "Restart Your App !!", Toast.LENGTH_LONG).show()
            }
        }

        if (tokenValue != null && !tokenValue.equals("null") &&
            !sessionManager.GetValue(Constants.USER_ID).equals("null")
        ) {
            viewModelMain.OpenAppAPI(
                securityToken = sessionManager.GetValue(Constants.SECURITY_TOKEN).toString(),
                userId = sessionManager.GetValue(Constants.USER_ID).toString(),
                versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
                versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString()
            ).observe(this@Activity_Splash_Screen) {
                if (it.status == 200) {
                    handler = Handler(Looper.getMainLooper())
                    handler!!.postDelayed(
                        Runnable {
                            sessionManager.InitializeValue(Constants.APP_URL,it.appUrl)
                            exitAnimation("mainScreen")
                        },
                        SPLASH_TIME_OUT.toLong()
                    )
                } else {
                    Toast.makeText(context, "Relaunch the app !", Toast.LENGTH_LONG).show()
                    Log.e("SplashScreen", "Failed to send device details")
                }
            }
        } else {
            handler = Handler(Looper.getMainLooper())
            handler!!.postDelayed(
                Runnable {
                    exitAnimation("Signin")
                },
                SPLASH_TIME_OUT.toLong()
            )
        }

    }

    private fun exitAnimation( screen : String)
    {
        binding.leftMice.startAnimation(left_out as Animation?)
        (left_out as Animation).setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {

                binding.leftMice.visibility = View.GONE
                binding.rightMice.startAnimation(right_out as Animation?)
                binding.bottomMice.startAnimation(slide_in as Animation?)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        (slide_in as Animation).setAnimationListener(object : AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.rightMice.visibility = View.GONE
                binding.bottomMice.visibility = View.GONE
                switchMainScreen(screen)
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }


    private fun switchMainScreen(screen : String) {
        var intent = Intent()
        if(screen.equals("mainScreen")) intent = Intent(context, Activity_Main::class.java)
        else intent = Intent(context, SignInActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.right_slide_in_anim, R.anim.left_slide_out_anim)
        finish()
        }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel("in onDestroyed scope is cancel ") // Cancel all coroutines when the activity is destroyed
    }

    private suspend fun getPackageInfo(): PackageInfo {
        return withContext(Dispatchers.IO) {
            try {
                packageManager.getPackageInfo(packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                PackageInfo()
            }
        }
    }

    private suspend fun getAdvertisingIdInfo(): AdvertisingIdClient.Info? {
        return withContext(Dispatchers.IO) {
            try {
                AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}