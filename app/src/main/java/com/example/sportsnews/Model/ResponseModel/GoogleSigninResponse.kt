package com.mindgeeks.sportsnews.Model.ResponseModel

data class GoogleSigninResponse(
    val status : Int,
    val message  : String,
    val userId  : String,
    val security_token  : String,
)
