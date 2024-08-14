package com.mindgeeks.sportsnews.Aurthentication.googleAuthentication


data class SignInResult(
    val data : UserData?,
    val errorMessage : String?
)

data class UserData(
    val userName :String?,
    val userEmail :String,
    val profileUrl : String?,
    val socialToken : String,
    val socialId: String,
)