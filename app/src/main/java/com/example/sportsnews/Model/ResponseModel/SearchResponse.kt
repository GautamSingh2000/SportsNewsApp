package com.mindgeeks.sportsnews.Model.ResponseModel

data class SearchResponse(
    val answer: Answer,
    val message: String,
    val status: Int
)

data class Answer(
    val ans: List<String>
)