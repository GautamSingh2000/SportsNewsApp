package com.mindgeeks.sportsnews.Aurthentication.googleAuthentication

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.mindgeeks.sportsnews.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

// class is to get the user information

class googleAuthenticationUiClient(
    private val context : Context,
    private val onTapClient : SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIning() : IntentSender? {
        var result = try {
            onTapClient.beginSignIn(
                buildSiginrequest()
            ).await()
        }catch (e : Exception){
           e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender // this last code send the information of signin back into the intent
        // to decode this we use pre_created function by Firebase
    }

    suspend fun getSigninWithIntent(intetn: Intent): SignInResult {
     val credential = onTapClient.getSignInCredentialFromIntent(intetn)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken,null)
        return try
        {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run{
                    UserData(
                        socialId = user.uid,
                        userName = user.displayName,
                        userEmail = user.email.toString(),
                        profileUrl = photoUrl?.toString(),
                        socialToken = credential.googleIdToken.toString()
                    )
                },
                errorMessage = null
            )

        }catch (e : Exception)
        {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )

        }
    }

    fun getSignInUSer() : UserData? = auth.currentUser?.run {
        UserData(
            socialId = uid,
            userName = displayName,
            profileUrl = photoUrl.toString(),
            userEmail = email.toString(),
            socialToken = tenantId.toString()
        )
    }

    private fun buildSiginrequest() : BeginSignInRequest{
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.Builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.default_web_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}