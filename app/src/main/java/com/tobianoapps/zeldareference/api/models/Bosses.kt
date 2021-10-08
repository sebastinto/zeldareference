package com.tobianoapps.zeldareference.api.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Keep
data class Bosses(
    @field:Json(name = "count")
    val count: Int,
    @field:Json(name = "data")
    val data: List<Data>,
    @field:Json(name = "success")
    val success: Boolean
) {
    @Keep
    @Parcelize
    data class Data(
        @field:Json(name = "appearances")
        val appearances: List<String>, // actually a single url
        @field:Json(name = "description")
        val description: String,
        @field:Json(name = "dungeons")
        val dungeons: List<String>,
        @field:Json(name = "_id")
        val id: String,
        @field:Json(name = "name")
        val name: String,
        @field:Json(name = "__v")
        val v: Int
    ) : Parcelable {

        @Keep
        @Parcelize
        data class Dungeon(
            @field:Json(name = "_id")
            val id: String,
            @field:Json(name = "name")
            val name: String
        ) : Parcelable
    }
}

@Keep
data class BossAppearances(
    @SerializedName("data")
    val data: Data,
    @SerializedName("success")
    val success: Boolean
) {
    @Keep
    data class Data(
        @SerializedName("description")
        val description: String,
        @SerializedName("developer")
        val developer: String,
        @SerializedName("_id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("publisher")
        val publisher: String,
        @SerializedName("released_date")
        val releasedDate: String,
        @SerializedName("__v")
        val v: Int
    )
}

@Keep
@Parcelize
data class BossDungeons(
    val list: List<Bosses.Data.Dungeon>
) : Parcelable
