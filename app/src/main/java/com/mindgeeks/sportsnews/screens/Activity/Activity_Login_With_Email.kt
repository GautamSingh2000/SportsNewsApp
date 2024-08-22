package com.mindgeeks.sportsnews.screens.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mindgeeks.sportsnews.models.RequestModels.LoginWithEmailrequest
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.databinding.ActivityLoginWithEmailBinding

class Activity_Login_With_Email : AppCompatActivity() {
    private lateinit var binding: ActivityLoginWithEmailBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModelMain: ViewModel_Main
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
                    Login(binding.email.text.toString().trim(),binding.password.text.toString().trim())
                }else{
                    Toast.makeText(this,"Enter Enter Password!!",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Enter Valid Email!!",Toast.LENGTH_SHORT).show()
            }
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
            if(it.status == 200){
                Toast.makeText(this,"Login Successful !!",Toast.LENGTH_SHORT).show()
                sessionManager.InitializeValue(Constants.USER_ID,it.userId)
                sessionManager.InitializeValue(Constants.SECURITY_TOKEN,it.securityToken)
                val intent =
                    Intent(this@Activity_Login_With_Email, Activity_Main::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"${it.message}",Toast.LENGTH_LONG).show()
            }
        }
    }
}