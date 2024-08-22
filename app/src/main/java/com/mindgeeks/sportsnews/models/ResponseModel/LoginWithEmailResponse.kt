package com.mindgeeks.sportsnews.models.ResponseModel

data class LoginWithEmailResponse(
    val message: String,
    val securityToken: String,
    val status: Int,
    val userId: String
)