package com.mindgeeks.sportsnews.models.RequestModels

data class GoogleSigninRequest(
    val versionName: String,
    val versionCode: String,
    val advertisingId: String,
    val deviceId: String,
    val deviceType: String ,
    val deviceName: String,
    val socialType: String = "Google",
    val socialId: String,
    val socialToken: String,
    val socialEmail: String,
    val socialName: String,
    val socialImgUrl: String?,
    val utmSource: String = " ",
    val utmMedium: String = " ",
    val utmTerm: String = " ",
    val utmContent: String = " ",
    val utmCampaign: String = " ",
    val referrerUrl: String = " ",
)
