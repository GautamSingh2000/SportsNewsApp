package com.mindgeeks.sportsnews.models.ResponseModel

data class SearchResponse(
    val answer: Answer,
    val message: String,
    val status: Int
)

data class Answer(
    val ans: List<String>
)