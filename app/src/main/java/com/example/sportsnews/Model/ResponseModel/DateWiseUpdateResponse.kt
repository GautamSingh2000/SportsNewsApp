package com.mindgeeks.sportsnews.Model.ResponseModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class DateWiseUpdateResponse(
    val matches: List<Matche>,
    val message: String,
    val news: List<New>,
    val status: Int
)

@Parcelize
data class Matche(
    val competition_id: String,
    val match_id: String,
    val match_time: String,
    val short_title: String,
    val teama: String,
    val teama_logo: String,
    val teama_overs: String,
    val teama_players: List<TeamPlayer>,
    val teama_scores: String,
    val teama_scores_full: String,
    val teamb: String,
    val teamb_logo: String,
    val teamb_overs: String,
    val teamb_players: List<TeamPlayer>,
    val teamb_scores: String,
    val teamb_scores_full: String,
    val title: String,
    val type: String,
    val venue: Venue
) : Parcelable

@Parcelize
data class New(
    val author: String,
    val content: String?,
    val desc: String,
    val image: String,
    val name: String,
    val publishedAt: String,
    val title: String,
    val url: String
) : Parcelable


@Parcelize
data class Venue(
    val country: String,
    val location: String,
    val name: String
): Parcelable