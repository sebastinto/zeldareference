package com.tobianoapps.zeldareference.repository

import android.content.Context
import com.google.gson.JsonElement
import com.tobianoapps.zeldareference.util.networking.Resource

interface ZeldaRepository {

    suspend fun getZeldaData(
        context: Context,
        path: String
    ): Resource<JsonElement>

    suspend fun getZeldaDetailData(
        context: Context,
        url: String
    ): Resource<JsonElement>
}
