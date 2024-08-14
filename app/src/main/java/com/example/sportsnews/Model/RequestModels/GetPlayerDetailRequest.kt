package com.mindgeeks.sportsnews.Model.RequestModels

data class GetPlayerDetailRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String,
    val playerId: String,
)
