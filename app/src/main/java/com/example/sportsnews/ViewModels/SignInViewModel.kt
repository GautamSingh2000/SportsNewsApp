package com.mindgeeks.sportsnews.ViewModels

import androidx.lifecycle.ViewModel
import com.mindgeeks.sportsnews.Aurthentication.googleAuthentication.SignInResult
import com.mindgeeks.sportsnews.Screens.Activity.states.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel():ViewModel() {
  private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.update {
            SignInState(
                isSignInSuccecfull = result.data != null,
                SignInError = result.errorMessage
            )
        }
    }

}