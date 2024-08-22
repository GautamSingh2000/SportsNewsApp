package com.mindgeeks.sportsnews.models.RequestModels

data class GetLiveMatchDataRequest(val userId: String,
                                   val securityToken: String,
                                   val versionName: String,
                                   val versionCode: String,
                                   val matchId: String,
                                   val compId: String)
