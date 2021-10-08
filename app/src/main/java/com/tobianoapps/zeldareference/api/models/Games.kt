package com.tobianoapps.zeldareference.api.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Games(
    @field:Json(name = "count")
    val count: Int,
    @field:Json(name = "data")
    val data: List<Data>,
    @field:Json(name = "success")
    val success: Boolean
) : Parcelable {
    @Keep
    @Parcelize
    data class Data(
        @field:Json(name = "description")
        val description: String,
        @field:Json(name = "developer")
        val developer: String,
        @field:Json(name = "_id")
        val id: String,
        @field:Json(name = "name")
        val name: String,
        @field:Json(name = "publisher")
        val publisher: String,
        @field:Json(name = "released_date")
        val releasedDate: String,
        @field:Json(name = "__v")
        val v: Int
    ) : Parcelable
}
