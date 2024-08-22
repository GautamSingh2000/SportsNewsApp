package com.mindgeeks.sportsnews.models.ResponseModel

data class ProfileResponse(
    val email: String,
    val message: String,
    val name: String,
    val profilePic: String,
    val status: Int
)