package com.mindgeeks.sportsnews.Model.RequestModels

class ProfileREquest(val userId: String,
                     val securityToken: String,
                     val versionName: String,
                     val versionCode: String,) {
}