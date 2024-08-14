package com.mindgeeks.sportsnews.Model.ResponseModel

data class PlayerSearchResponse(
    val status: Int,
    val message: Boolean,
    val allPlayers: List<PlayerData>,
)

class PlayerData(
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val pId: String,
    val nationality: String,
    val image: String,
)
