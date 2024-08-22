package com.mindgeeks.sportsnews.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.mindgeeks.sportsnews.Aurthentication.authentication_google.SignInResult
import com.mindgeeks.sportsnews.screens.Activity.States.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModel_SignIn():ViewModel() {
  private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            Log.e("Singinviewmodel","result of signIn is ${result.data}")
            SignInState(
                isSignInSuccecfull = result.data != null,
                SignInError = result.errorMessage
            )
        }
    }

}