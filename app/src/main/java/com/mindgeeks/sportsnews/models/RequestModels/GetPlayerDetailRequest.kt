package com.mindgeeks.sportsnews.models.RequestModels

data class GetPlayerDetailRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String,
    val playerId: String,
)
