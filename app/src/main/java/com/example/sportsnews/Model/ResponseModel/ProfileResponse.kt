package com.mindgeeks.sportsnews.Model.ResponseModel

data class ProfileResponse(
    val email: String,
    val message: String,
    val name: String,
    val profilePic: String,
    val status: Int
)