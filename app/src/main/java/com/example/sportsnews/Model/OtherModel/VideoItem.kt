package com.mindgeeks.sportsnews.Model.OtherModel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoItem(
    val videoUrl: String
):Parcelable