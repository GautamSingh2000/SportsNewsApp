package com.mindgeeks.sportsnews.models.OtherModel

import android.os.Parcel
import android.os.Parcelable
import com.mindgeeks.sportsnews.models.ResponseModel.MatchData
import com.mindgeeks.sportsnews.models.ResponseModel.Scores
import com.mindgeeks.sportsnews.models.ResponseModel.total


data class score_Card(
    val matchData: MatchData,
    val teamsScoreCard: Scores,
    val teamABattingTotalScore: total,
    val teamABowlingTotalScore: total,
    val teamBBattingTotalScore: total,
    val teamBBowlingTotalScore: total
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(MatchData::class.java.classLoader)!!,
        parcel.readParcelable(Scores::class.java.classLoader)!!,
        parcel.readParcelable(total::class.java.classLoader)!!,
        parcel.readParcelable(total::class.java.classLoader)!!,
        parcel.readParcelable(total::class.java.classLoader)!!,
        parcel.readParcelable(total::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(matchData, flags)
        parcel.writeParcelable(teamsScoreCard, flags)
        parcel.writeParcelable(teamABattingTotalScore, flags)
        parcel.writeParcelable(teamABowlingTotalScore, flags)
        parcel.writeParcelable(teamBBattingTotalScore, flags)
        parcel.writeParcelable(teamBBowlingTotalScore, flags)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<score_Card> {
        override fun createFromParcel(parcel: Parcel): score_Card {
            return score_Card(parcel)
        }

        override fun newArray(size: Int): Array<score_Card?> {
            return arrayOfNulls(size)
        }
    }
}
