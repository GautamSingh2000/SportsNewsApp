package com.mindgeeks.sportsnews.screens.Activity

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.mainScreen
import com.mindgeeks.sportsnews.models.RequestModels.LoginWithEmailrequest
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.ActivityLoginWithEmailBinding

class Activity_Login_With_Email : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWithEmailBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModelMain: ViewModel_Main
    private var password_show = false
    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginWithEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.animation.playAnimation()

        sessionManager = SessionManager(this)
        viewModelMain = ViewModel_Main(this)

        binding.appba.titile.text = "Login Page"
        binding.appba.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.animation.playAnimation()
        binding.animation.addAnimatorListener(object : AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
                
            }

            override fun onAnimationEnd(animation: Animator) {
                binding.animation.playAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {
                
            }

            override fun onAnimationRepeat(animation: Animator) {
                
            }

        })

        binding.password.addTextChangedListener(object : TextWatcher{

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
             if(count>0)
             {
                 binding.lottieView.visibility = View.VISIBLE
                 binding.lottieView.playAnimation()

             }else{
                 binding.lottieView.visibility = View.GONE
             }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.liginBtn.setOnClickListener{
            if(!binding.email.text.isNullOrEmpty())
            {
                if(!binding.password.text.isNullOrEmpty())
                {
                    binding.email.clearFocus()
                    binding.password.clearFocus()
                    Login(binding.email.text.toString().trim(),binding.password.text.toString().trim())
                }else{
                    Toast.makeText(this,"Enter Enter Password!!",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Enter Valid Email!!",Toast.LENGTH_SHORT).show()
            }
        }
        binding.password.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = 2 // Index of the drawable at the end (right side)

                if (event.rawX >= (binding.password.right - binding.password.compoundDrawables[drawableEnd].bounds.width())) {
                    password_show = !password_show

                    if (password_show) {
                        // Show Password
                        binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.custom_lock, 0, R.drawable.eye_crossed, 0
                        )
                    } else {
                        // Hide Password
                        binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                        binding.password.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.custom_lock, 0, R.drawable.open_eye, 0
                        )
                    }

                    // Move cursor to the end of the text
                    binding.password.text?.let { binding.password.setSelection(it.length) }
                    return@setOnTouchListener true
                }
            }
            false
        }



        binding.pp.setOnClickListener {
            openTab(url = "https://cricnews.app/privacy.html",this)
        }

        binding.tc.setOnClickListener {
            openTab(url = "https://cricnews.app/terms_n_conditions.html",this)
        }

    }

    private fun Login(email: String,password : String)
    {
        binding.loading.root.visibility = View.VISIBLE
        val request = LoginWithEmailrequest(
            versionCode = sessionManager.GetValue(Constants.VERSION_CODE).toString(),
            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString(),
            email = email,
            password =  password
        )

        viewModelMain.LoginWithEmail(request).observe(this@Activity_Login_With_Email){
            binding.loading.root.visibility = View.GONE
            if(it.status == 200){
                Toast.makeText(this,"Login Successful !!",Toast.LENGTH_SHORT).show()
                sessionManager.InitializeValue(Constants.USER_ID,it.userId)
                sessionManager.InitializeValue(Constants.SECURITY_TOKEN,it.securityToken)
                val intent =
                    Intent(this@Activity_Login_With_Email, mainScreen::class.java)
                startActivity(intent)
                finish()
            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    binding.email.focusable = View.FOCUSABLE
                    binding.password.focusable = View.FOCUSABLE
                }
                Toast.makeText(this,"${it.message}",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun openTab(url: String, context : Context) {
        try {
            val builder = CustomTabsIntent.Builder()

            // to set the toolbar color use CustomTabColorSchemeParams
            // since CustomTabsIntent.Builder().setToolBarColor() is deprecated

            val params = CustomTabColorSchemeParams.Builder()
            params.setToolbarColor(ContextCompat.getColor(context, R.color.gradient_red))
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
            customBuilder.launchUrl(context, Uri.parse(url))
        }
        catch (e:Exception)
        {
            Toast.makeText(context,e.message, Toast.LENGTH_SHORT).show()
        }
    }
}

