package com.mindgeeks.sportsnews.models.RequestModels

class ProfileREquest(val userId: String,
                     val securityToken: String,
                     val versionName: String,
                     val versionCode: String,) {
}