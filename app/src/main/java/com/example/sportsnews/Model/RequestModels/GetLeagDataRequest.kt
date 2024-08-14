package com.mindgeeks.sportsnews.Model.RequestModels

class GetLeagDataRequest(
    val securityToken: String,
    val versionName: String,
    val versionCode: String,
    val userId: String ,
    val leagueId: String,
)