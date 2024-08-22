package com.mindgeeks.sportsnews.models.ResponseModel

data class OpenAppResponse(
    val message: String,
    val status: Int,
    val appUrl: String,
    val forceUpdate: Boolean,
    val socialEmail: String,
    val socialImgUrl: String,
    val socialName: String,
)