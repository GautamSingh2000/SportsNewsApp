package com.mindgeeks.sportsnews.Screens.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mindgeeks.sportsnews.Aurthentication.googleAuthentication.SignInResult
import com.mindgeeks.sportsnews.Aurthentication.googleAuthentication.googleAuthenticationUiClient
import com.mindgeeks.sportsnews.Model.RequestModels.GoogleSigninRequest
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Screens.Activity.states.SignInState
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.BatBallTheme
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.red
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.red_black
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.red_with_white
import com.mindgeeks.sportsnews.Screens.Activity.ui.theme.splashscreen_red
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.ViewModels.MainViewModel
import com.mindgeeks.sportsnews.ViewModels.SignInViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInActivity : ComponentActivity() {
    private val googleAuthUiClient by lazy {
        googleAuthenticationUiClient(
            context = applicationContext,
            onTapClient = com.google.android.gms.auth.api.identity.Identity.getSignInClient(
                applicationContext
            )
        )
    }
    lateinit var userData: SignInResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var mainViewModel = MainViewModel(this)
            val sessionManager = SessionManager(this)
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_in") {
                composable("sign_in") {
                    val viewmodel: SignInViewModel by viewModels()
                    val state by viewmodel.state.collectAsState()

                    LaunchedEffect(key1 = Unit) {
                        if (googleAuthUiClient.getSignInUSer() != null) {
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartIntentSenderForResult(),
                        onResult = { result ->
                            if (result.resultCode == RESULT_OK) {
                                lifecycleScope.launch {
                                    val signInResult = googleAuthUiClient.getSigninWithIntent(
                                        intetn = result.data ?: return@launch
                                    )
                                    userData = signInResult
                                    viewmodel.onSignInResult(signInResult)
                                }
                            }
                        })

                    MainComponent(
                        state = state,
                        onSignInClick = {
                            lifecycleScope.launch {
                                val signInIntent = googleAuthUiClient.signIning()
                                launcher.launch(
                                    IntentSenderRequest.Builder(
                                        signInIntent ?: return@launch
                                    ).build()
                                )
                            }
                        }
                    )

                    // Observe sign-in success and navigate to MainActivity
                    LaunchedEffect(key1 = state.isSignInSuccecfull) {
                        if (state.isSignInSuccecfull) {

                            val request = GoogleSigninRequest(
                                deviceId = sessionManager.GetValue(Constants.DEVICE_ID).toString(),
                                deviceType = sessionManager.GetValue(Constants.DEVICE_TYPE)
                                    .toString(),
                                deviceName = sessionManager.GetValue(Constants.DEVICE_NAME)
                                    .toString(),
                                versionName = sessionManager.GetValue(Constants.VERSION_NAME)
                                    .toString(),
                                versionCode = sessionManager.GetValue(Constants.VERSION_CODE)
                                    .toString(),
                                socialId = userData.data?.socialId.toString(),
                                socialEmail = userData.data?.userEmail.toString(),
                                socialName = userData.data?.userName.toString(),
                                socialImgUrl = userData.data?.profileUrl.toString(),
                                socialToken = userData.data?.socialToken.toString(),
                                advertisingId = sessionManager.GetValue(Constants.ADVERTISING_ID)
                                    .toString()
                            )

                            mainViewModel.GoogleSignin(request = request)
                                .observe(this@SignInActivity) {
                                    if (it.status == 200) {

                                        sessionManager.InitializeValue(Constants.USER_ID, it.userId)
                                        sessionManager.InitializeValue(Constants.SECURITY_TOKEN, it.security_token)

                                        mainViewModel.OpenAppAPI(
                                            securityToken = it.security_token,
                                            userId = it.userId,
                                            versionCode =sessionManager.GetValue(Constants.VERSION_CODE).toString(),
                                            versionName = sessionManager.GetValue(Constants.VERSION_NAME).toString()
                                        ).observe(this@SignInActivity){
                                            if (it.status == 200) {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Signing Successful !!",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                                val intent =
                                                    Intent(this@SignInActivity, MainActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                Toast.makeText(this@SignInActivity,"Relaunch the app !",Toast.LENGTH_LONG).show()
                                                Log.e("SplashScreen", "Failed to send device details")
                                            }
                                        }
                                    } else {
                                        Toast.makeText(
                                            applicationContext,
                                            "Logining Fail !!",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainComponent(
    state: SignInState,
    onSignInClick: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.SignInError) {
        state.SignInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            val images = listOf(
                R.drawable.batesmanone,
                R.drawable.batsman_one,
                R.drawable.batsman_two,
                R.drawable.three,
                R.drawable.four,
            )
            AutomaticImageViewFlipper(images)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = "Unleash the Power of the Pitch: Live Cricket News at Your Fingertips!",
                style = TextStyle(
                    color = Color.Gray,
                    fontWeight = FontWeight.W500,
                    fontSize = 29.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(Font(R.font.fjalla_one))
                )
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                modifier = Modifier.padding(horizontal = 25.dp),
                text = "Catch every thrilling moment with live scores, expert analysis, and exclusive interviews—all on our cricket news app!",
                style = TextStyle(
                    color = red_black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start
                )
            )

            Spacer(modifier = Modifier.height(22.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .clickable(
                        onClick = {
                            onSignInClick()
                        },
                    ),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(red_with_white),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_google_48),
                        contentDescription = "google_icon",
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        text = "Login with Google",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(Font(R.font.rem))
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable {
                        val i = Intent(context, LoginWithEmailActivity::class.java)
                        context.startActivity(i) // Cast context to Activity and call finish()
                    },
                text = "Login with Email",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W300)
                )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
 
 
                    verticalAlignment = Alignment.Bottom,
  
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.clickable {
                        val i = Intent(context, Empty_webLinktab::class.java)
                        i.putExtra("Link", "https://cricnews.app/privacy.html")
                        i.putExtra("Heading", "Private Policy")

                        context.startActivity(i)

                    },
                    text = "Private Policy",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                )
                Text(
                    modifier = Modifier.clickable {
                        val i = Intent(context, Empty_webLinktab::class.java)
                        i.putExtra("Link", "https://cricnews.app/terms_n_conditions.html")
                        i.putExtra("Heading", "Terms and Condition")
                        context.startActivity(i)

                    },
                    text = "Terms & Condition",
                    style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                )
            }
        }
    }
}

@Composable
fun AutomaticImageViewFlipper(imageIds: List<Int>) {
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2500)
            currentIndex = (currentIndex + 1) % imageIds.size
        }
    }

    Box(modifier = Modifier) {
        imageIds.forEachIndexed { index, imageId ->
            AnimatedVisibility(
                visible = index == currentIndex,
                enter = fadeIn(animationSpec = tween(1500)),
                exit = fadeOut(animationSpec = tween(1500))
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(330.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainComponentPreview() {
    val sampleState = SignInState(
        isSignInSuccecfull = false,
        SignInError = null
        // Add other fields with sample values as needed
    )

    BatBallTheme {
        MainComponent(
            state = sampleState,
            onSignInClick = {

            }
        )

    }
}
