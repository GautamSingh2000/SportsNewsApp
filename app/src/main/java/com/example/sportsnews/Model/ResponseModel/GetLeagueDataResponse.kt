package com.mindgeeks.sportsnews.Model.ResponseModel

data class GetLeagueDataResponse(
    val message: String,
    val status: Int,
    val teams: List<Team>
)

data class LeagTeam(
    val id: Int,
    val logo: String,
    val name: String,
    val overs: String,
    val scores: String,
    val short_name: String
)

data class Team(
    val matchDate: String,
    val teama: LeagTeam,
    val teamb: LeagTeam,
    val winningTeam: String,
    val compId: String,
    val matchId: String
)