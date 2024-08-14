package com.mindgeeks.sportsnews.Model.ResponseModel

data class LoginWithEmailResponse(
    val message: String,
    val securityToken: String,
    val status: Int,
    val userId: String
)