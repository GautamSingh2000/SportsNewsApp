package com.mindgeeks.sportsnews.models.RequestModels

data class ShortsRequestModel(    val userId: String,
                                  val securityToken: String,
                                  val versionName: String,
                                  val versionCode: String,
                                  val deviceId: String,
                                  var page : String,
) {
    init {
        require(userId.isNotBlank()) { "userId cannot be blank" }
        require(securityToken.isNotBlank()) { "securityToken cannot be blank" }
        require(versionName.isNotBlank()) { "versionName cannot be blank" }
        require(versionCode.isNotBlank()) { "versionCode cannot be blank" }
        require(page.isNotBlank()) { "page cannot be blank" }
        require(deviceId.isNotBlank()) { "deviceId cannot be blank" }
    }
}

