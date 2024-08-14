package com.mindgeeks.sportsnews.Model.RequestModels

data class HomeFragmentRequest(
    val userId: String,
    val securityToken: String,
    val versionName: String,
    val versionCode: String,
) {
    init {
        require(userId.isNotBlank()) { "userId cannot be blank" }
        require(securityToken.isNotBlank()) { "securityToken cannot be blank" }
        require(versionName.isNotBlank()) { "versionName cannot be blank" }
        require(versionCode.isNotBlank()) { "versionCode cannot be blank" }
    }
}
