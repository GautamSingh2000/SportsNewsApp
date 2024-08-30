package com.mindgeeks.sportsnews.screens.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mindgeeks.sportsnews.mainScreen
import com.mindgeeks.sportsnews.Aurthentication.authentication_google.SignInResult
import com.mindgeeks.sportsnews.Aurthentication.authentication_google.googleAuthenticationUiClient
import com.mindgeeks.sportsnews.models.RequestModels.GoogleSigninRequest
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Utils.Constants
import com.mindgeeks.sportsnews.Utils.SessionManager
import com.mindgeeks.sportsnews.screens.Activity.States.SignInState
import com.mindgeeks.sportsnews.screens.Activity.Ui.theme.BatBallTheme
import com.mindgeeks.sportsnews.screens.Activity.Ui.theme.gray
import com.mindgeeks.sportsnews.screens.Activity.Ui.theme.red_black
import com.mindgeeks.sportsnews.screens.Activity.Ui.theme.red_with_white
import com.mindgeeks.sportsnews.view_models.ViewModel_Main
import com.mindgeeks.sportsnews.view_models.ViewModel_SignIn
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
            var viewModelMain = ViewModel_Main(this)
            val sessionManager = SessionManager(this)
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "sign_in") {
                composable("sign_in") {
                    val viewmodel: ViewModel_SignIn by viewModels()
                    val state by viewmodel.state.collectAsState()

                    LaunchedEffect(key1 = Unit) {
                        if (googleAuthUiClient.getSignInUSer() != null) {
                            val intent = Intent(this@SignInActivity, mainScreen::class.java)
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

                            viewModelMain.GoogleSignin(request = request)
                                .observe(this@SignInActivity) {
                                    if (it.status == 200) {

                                        sessionManager.InitializeValue(Constants.USER_ID, it.userId)
                                        sessionManager.InitializeValue(Constants.SECURITY_TOKEN, it.security_token)

                                        viewModelMain.OpenAppAPI(
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
                                                    Intent(this@SignInActivity, mainScreen::class.java)
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
                    color = gray,
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
                colors = CardDefaults.cardColors(red_black),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.white_google_logo),
                        contentDescription = "google_icon",
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        modifier = Modifier
                            .padding(vertical = 10.dp),
                        text = "Login with Google",
                        style = TextStyle(
                            color = Color.White,
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
                        val i = Intent(context, Activity_Login_With_Email::class.java)
                        context.startActivity(i) // Cast context to Activity and call finish()
                    },
                text = "Login with Email",
                style = TextStyle(
                    color = red_with_white,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W300)
                )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                text = "By clicking on the above button you are agree to our",
                style = TextStyle(
                    color =  Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W400
                )
                )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                    verticalAlignment = Alignment.Bottom,

                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier.clickable {
                        openTab(url = "https://cricnews.app/privacy.html", context)
                    },
                    text = "Privacy Policy",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.W500
                    )
                )

                Text(
                    modifier = Modifier,
                    text = "and",
                    style = TextStyle(
                        color =  Color.Black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W400
                    )
                )
                Text(
                    modifier = Modifier.clickable {
                        openTab(url = "https://cricnews.app/terms_n_conditions.html",context)
                    },
                    text = "Terms & Conditions",
                    style = TextStyle(
                        color =  Color.Black,
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
