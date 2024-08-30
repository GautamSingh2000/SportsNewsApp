package com.mindgeeks.sportsnews.models.ResponseModel

data class ShortsResponseModel(
    val message: String,
    val shorts: List<Short>,
    val status: Int
) {
    data class Short(
        val reelId: Int,
        val reelUrl: String
    )
}