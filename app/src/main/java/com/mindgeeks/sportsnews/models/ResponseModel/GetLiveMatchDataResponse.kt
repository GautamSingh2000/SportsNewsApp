package com.mindgeeks.sportsnews.models.ResponseModel

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class GetLiveMatchDataResponse(
    val matchData: MatchData,
    val message: String,
    val status: Int,
    val teama__batsman_total : total,
    val teamb__batsman_total : total,
    val teama__bowlers_total : total,
    val teamb__bowlers_total : total
)

@Parcelize
data class Batsmen(
    val balls_faced: String,
    val batsman_id: String,
    val batting: String,
    val bowler_id: String,
    val dismissal: String,
    val first_fielder_id: String,
    val fours: String,
    val how_out: String,
    val name: String,
    val position: String,
    val role: String,
    val role_str: String,
    val run0: String,
    val run1: String,
    val run2: String,
    val run3: String,
    val run5: String,
    val runs: String,
    val second_fielder_id: String,
    val sixes: String,
    val strike_rate: String,
    val third_fielder_id: String
): Parcelable

@Parcelize
data class Bowler(
    val bowledcount: String,
    val bowler_id: String,
    val bowling: String,
    val econ: String,
    val lbwcount: String,
    val maidens: String,
    val name: String,
    val noballs: String,
    val overs: String,
    val position: String,
    val run0: String,
    val runs_conceded: String,
    val wickets: String,
    val wides: String
): Parcelable

@Parcelize
data class DidNotBat(
    val name: String,
    val player_id: String
): Parcelable

@Parcelize
data class Equations(
    val bowlers_used: Int,
    val overs: String,
    val runrate: String,
    val runs: Int,
    val wickets: Int
): Parcelable

@Parcelize
data class ExtraRuns(
    val byes: Int,
    val legbyes: Int,
    val noballs: Int,
    val penalty: String,
    val total: Int,
    val wides: Int
): Parcelable

@Parcelize
data class Review(
    val batting: Batting,
    val bowling: Bowling
):Parcelable


@Parcelize
data class Scores(
    val teama: TeamScore,
    val teamb: TeamScore
): Parcelable

@Parcelize
data class TeamScore(
    val batsmen: List<Batsmen>,
    val batting_team_id: Int,
    val bowlers: List<Bowler>,
    val did_not_bat: List<DidNotBat>,
    val equations: Equations,
    val extra_runs: ExtraRuns,
    val iid: Int,
    val name: String,
    val number: Int,
    val result: Int,
    val review: Review,
    val scores: String,
    val scores_full: String,
    val short_name: String,
    val status: Int,
): Parcelable

@Parcelize
data class MatchData(
    val match_type: String,
    val scores: Scores,
    val teama_logo: String,
    val teama_name: String,
    val teama_players: List<TeamPlayer>,
    val teama_scores: String,
    val teama_short_name: String,
    val teamb_logo: String,
    val teamb_name: String,
    val teamb_players: List<TeamPlayer>,
    val teamb_scores: String,
    val teamb_short_name: String
) : Parcelable


data class TeamPlayer(
    val alt_name: String?,
    val batting_style: String?,
    val birthdate: String?,
    val birthplace: String?,
    val bowling_style: String?,
    val country: String?,
    val debut_data: String?,
    val first_name: String,
    val last_name: String?,
    val logo_url: String?,
    val middle_name: String?,
    val nationality: String?,
    val pid: Int,
    val short_name: String?,
    val thumb_url: String?,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(alt_name)
        parcel.writeString(batting_style)
        parcel.writeString(birthdate)
        parcel.writeString(birthplace)
        parcel.writeString(bowling_style)
        parcel.writeString(country)
        parcel.writeString(debut_data)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeString(logo_url)
        parcel.writeString(middle_name)
        parcel.writeString(nationality)
        parcel.writeInt(pid)
        parcel.writeString(short_name)
        parcel.writeString(thumb_url)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<TeamPlayer> {
        override fun createFromParcel(parcel: Parcel): TeamPlayer {
            return TeamPlayer(parcel)
        }

        override fun newArray(size: Int): Array<TeamPlayer?> {
            return arrayOfNulls(size)
        }
    }
}

@Parcelize
data class total(
    val average_strike_rate: Double,
    val total_balls: Int,
    val total_fours: Int,
    val total_runs: Int,
    val total_sixes: Int
):Parcelable