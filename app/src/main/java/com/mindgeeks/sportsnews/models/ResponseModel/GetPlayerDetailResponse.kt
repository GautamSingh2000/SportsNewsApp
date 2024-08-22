package com.mindgeeks.sportsnews.models.ResponseModel

import android.os.Parcel
import android.os.Parcelable

data class GetPlayerDetailResponse(
    val message: String,
    val playerInfo: List<PlayerInfo>,
    val status: Int
)

data class PlayerInfo(
    val batting: List<Batting>,
    val bowling: List<Bowling>,
    val playerInfo: PlayerInfoX
)

data class PlayerInfoX(
    val alt_name: String,
    val playerImage: String,
    val countryFlag: String,
    val batting_style: String,
    val birthdate: String,
    val birthplace: String,
    val bowling_style: String,
    val country: String,
    val debut_data: String,
    val facebook_profile: String,
    val fantasy_player_rating: Double,
    val fielding_position: String,
    val first_name: String,
    val instagram_profile: String,
    val last_name: String,
    val logo_url: String,
    val middle_name: String,
    val nationality: String,
    val pid: Int,
    val playing_role: String,
    val primary_team: List<Any>,
    val recent_appearance: Int,
    val recent_match: Int,
    val short_name: String,
    val thumb_url: String,
    val title: String,
    val twitter_profile: String
)


data class Batting(
    val balls: String?,
    val catches: String?,
    val innings: String?,
    val matches: String?,
    val notout: String?,
    val runs: String?,
    val stumpings: String?,
    val title: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(balls)
        parcel.writeString(catches)
        parcel.writeString(innings)
        parcel.writeString(matches)
        parcel.writeString(notout)
        parcel.writeString(runs)
        parcel.writeString(stumpings)
        parcel.writeString(title)
    }

    companion object CREATOR : Parcelable.Creator<Batting> {
        override fun createFromParcel(parcel: Parcel): Batting {
            return Batting(parcel)
        }

        override fun newArray(size: Int): Array<Batting?> {
            return arrayOfNulls(size)
        }
    }
}

data class Bowling(
    val average: String?,
    val balls: String?,
    val best_innings: String?,
    val best_match: String?,
    val econ: String?,
    val innings: String?,
    val matches: String?,
    val overs: String?,
    val strike: String?,
    val runs: String?,
    val title: String?,
    val wickets: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(average)
        parcel.writeString(balls)
        parcel.writeString(best_innings)
        parcel.writeString(best_match)
        parcel.writeString(econ)
        parcel.writeString(innings)
        parcel.writeString(matches)
        parcel.writeString(overs)
        parcel.writeString(strike)
        parcel.writeString(runs)
        parcel.writeString(title)
        parcel.writeString(wickets)
    }

    companion object CREATOR : Parcelable.Creator<Bowling> {
        override fun createFromParcel(parcel: Parcel): Bowling {
            return Bowling(parcel)
        }

        override fun newArray(size: Int): Array<Bowling?> {
            return arrayOfNulls(size)
        }
    }
}