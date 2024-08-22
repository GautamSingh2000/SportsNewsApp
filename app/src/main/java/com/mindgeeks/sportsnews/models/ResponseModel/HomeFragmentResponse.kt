package com.mindgeeks.sportsnews.models.ResponseModel

import android.os.Parcel
import android.os.Parcelable
import com.mindgeeks.sportsnews.R

data class HomeFragmentResponse(
    val leagues: List<League>,
    val liveMatches: List<LiveMatche>,
    val message: String,
    val status: Int,
    val trendingNews: List<TrendingNew>,
    val upcomingMatches: List<UpcomingMatche>
)

data class League(
    val tournamentId: String,
    val tournamentName: String,
    val image: String? = R.drawable.img_1.toString()
)

data class LiveMatche(
    val competition_id: String,
    val match_id: String,
    val match_time: String,
    val short_title: String,
    val teama: String,
    val teama_logo: String,
    val teama_overs: String,
    val teama_scores: String,
    val teama_scores_full: String,
    val teamb: String,
    val teamb_logo: String,
    val teamb_overs: String,
    val teamb_scores: String,
    val teamb_scores_full: String,
    val title: String,
    val type: String,
    val venue: Venue
)
data class TrendingNew(
    val author: String,
    val content: String,
    val desc : String?,
    val image: String,
    val name: String,
    val publishedAt: String,
    val title: String,
    val url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(content)
        parcel.writeString(desc)
        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(publishedAt)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrendingNew> {
        override fun createFromParcel(parcel: Parcel): TrendingNew {
            return TrendingNew(parcel)
        }

        override fun newArray(size: Int): Array<TrendingNew?> {
            return arrayOfNulls(size)
        }
    }
}

data class UpcomingMatche(
    val competition_id: String,
    val match_id: String,
    val match_time: String,
    val short_title: String,
    val teama: String,
    val teama_logo: String,
    val teamb: String,
    val teamb_logo: String,
    val title: String,
    val type: String,
    val venue: Venue
)
