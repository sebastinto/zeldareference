package com.tobianoapps.zeldareference.api

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ZeldaApi {

    @GET("{path}")
    suspend fun getZeldaData(@Path("path") path: String?): Response<JsonElement>

    @GET("{path}/{id}")
    suspend fun getZeldaDetailData(
        @Path("path") path: String,
        @Path("id") id: String
    ): Response<JsonElement>
}
