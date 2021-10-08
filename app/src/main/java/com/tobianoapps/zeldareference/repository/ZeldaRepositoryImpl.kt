package com.tobianoapps.zeldareference.repository

import android.content.Context
import com.google.gson.JsonElement
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.ZeldaApi
import com.tobianoapps.zeldareference.di.CoroutineModule.IoDispatcher
import com.tobianoapps.zeldareference.util.networking.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class ZeldaRepositoryImpl @Inject constructor(
    private val zeldaApi: ZeldaApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ZeldaRepository {

    /**
     * Call main endpoint
     */
    override suspend fun getZeldaData(
        context: Context,
        path: String
    ): Resource<JsonElement> = withContext(ioDispatcher) {
        return@withContext try {
            // Make REST call
            val response: Response<JsonElement> = zeldaApi.getZeldaData(path)

            // Parse response
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error(
                context.getString(R.string.error_message_empty_response_body),
                null, null
            )
        } catch (e: IOException) {
            Resource.Error("$e")
        }
    }


    override suspend fun getZeldaDetailData(
        context: Context,
        url: String
    ): Resource<JsonElement> = withContext(ioDispatcher) {

        val path = url.substringBefore('/')
        val id = url.substringAfter('/')

        return@withContext try {
            // Make REST call
            val response: Response<JsonElement> = zeldaApi.getZeldaDetailData(path, id)

            // Parse response
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error(
                context.getString(R.string.error_message_empty_response_body),
                null, null
            )
        } catch (e: IOException) {
            Resource.Error("$e")
        }
    }
}
