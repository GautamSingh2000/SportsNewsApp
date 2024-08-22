package com.mindgeeks.sportsnews.models.RequestModels

data class PlayerSearchRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String,
    val playerName: String ="",
    val page : String = "1",
    val nationality: String = "",
    val playingRole: String = "",
)
